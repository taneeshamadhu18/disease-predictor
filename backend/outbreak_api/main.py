# main.py
import pickle
import pandas as pd
import numpy as np
from scipy.sparse import hstack
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
import os
from google.cloud import firestore
from datetime import datetime, timezone, timedelta
from twilio.rest import Client
from typing import Optional

# --- Import config files
import config  #for twilio

# Securely initialize Firestore client
if os.path.exists('credentials.json'):
    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'credentials.json'
try:
    db = firestore.Client()
    print("Successfully connected to Firestore.")
except Exception as e:
    print(f"Failed to connect to Firestore: {e}")
    db = None

# Initialize Twilio client
try:
    twilio_client = Client(config.TWILIO_ACCOUNT_SID, config.TWILIO_AUTH_TOKEN)
    print("Successfully connected to Twilio.")
except Exception as e:
    print(f"Failed to connect to Twilio: {e}")
    twilio_client = None

app = FastAPI(
    title="ASHA Outbreak Prediction API",
    description="Predicts outbreak risk, saves data, and triggers hotspot alerts.",
    version="3.0.0"
)

# Load models
try:
    model = pickle.load(open('best_model.pkl', 'rb'))
    structured_preprocessor = pickle.load(open('structured_preprocessor.pkl', 'rb'))
    text_preprocessor = pickle.load(open('text_preprocessor.pkl', 'rb'))
    label_encoder = pickle.load(open('label_encoder.pkl', 'rb'))
except FileNotFoundError:
    raise RuntimeError("Model or preprocessor files not found.")

class PatientData(BaseModel):
    State: str = Field(..., example="Nagaland")
    Village: str = Field(..., example="Khonoma") # Village is required for hotspot checks
    Age: int = Field(..., example=35)
    Gender: str = Field(..., example="Female")
    WaterSourceType: str = Field(..., example="HandPump")
    SanitationLevels: str = Field(..., example="Poor")
    Fever: bool = Field(..., example=True)
    Vomiting: bool = Field(..., example=False)
    AbdominalPain: bool = Field(..., example=True)
    Diarrhoea: bool = Field(..., example=True)
    YellowEyes: bool = Field(..., example=False)
    SymptomsDuration: int = Field(..., example=5)
    RecentTravelHistory: bool = Field(..., example=False)
    CommunityNotes: str = Field(..., example="Several children have similar symptoms.")
    latitude: Optional[float] = Field(None, example=25.67)
    longitude: Optional[float] = Field(None, example=94.01)

# --- Alerting and Analysis Functions ---
def send_sms_alert(village, state, num_cases, avg_risk):
    if not twilio_client or not config.HEALTH_OFFICIAL_PHONE:
        print("Twilio client not available or official's phone number not set. Skipping SMS alert.")
        return

    message_body = (
        f"Health Alert: Potential outbreak detected in {village}, {state}. "
        f"Details: {num_cases} recent cases with an average risk confidence of {avg_risk:.0%}."
    )
    try:
        message = twilio_client.messages.create(
            body=message_body,
            from_=config.TWILIO_PHONE_NUMBER,
            to=config.HEALTH_OFFICIAL_PHONE
        )
        print(f"Successfully sent alert SMS. SID: {message.sid}")
    except Exception as e:
        print(f"Failed to send SMS via Twilio: {e}")

def check_for_hotspot(db, village: str, state: str):
    seven_days_ago = datetime.now(timezone.utc) - timedelta(days=7)
    records_ref = db.collection('patient_records')
    query = records_ref.where("Village", "==", village).where("State", "==", state).where("timestamp", ">=", seven_days_ago)
    docs = list(query.stream())
    
    if not docs: return

    recent_cases = [doc.to_dict() for doc in docs]
    num_cases = len(recent_cases)
    avg_risk = np.mean([case.get('prediction_confidence', 0) for case in recent_cases])
    high_confidence_count = sum(1 for case in recent_cases if case.get('prediction_confidence', 0) > 0.80)

    CASE_THRESHOLD = 5
    AVG_RISK_THRESHOLD = 0.65
    HIGH_CONFIDENCE_THRESHOLD = 3

    print(f"Analysis for {village}: {num_cases} cases, {avg_risk:.2%} avg risk, {high_confidence_count} high-confidence cases.")

    if (num_cases >= CASE_THRESHOLD and 
        avg_risk >= AVG_RISK_THRESHOLD and 
        high_confidence_count >= HIGH_CONFIDENCE_THRESHOLD):
        
        send_sms_alert(village, state, num_cases, avg_risk)

# --- API Endpoints ---
@app.get("/")
def read_root():
    return {"message": "ASHA Outbreak Prediction API is running."}

@app.post("/predict")
def predict_risk(data: PatientData):
    if db is None:
        raise HTTPException(status_code=500, detail="Database connection is not available.")
    
    model_input_df = pd.DataFrame([data.dict()]).drop(columns=['Village'])
    transformed_structured = structured_preprocessor.transform(model_input_df)
    transformed_text = text_preprocessor.transform(model_input_df['CommunityNotes'])
    transformed_final = hstack([transformed_structured, transformed_text]).tocsr()
    prediction_proba = model.predict_proba(transformed_final)
    
    high_risk_index = list(label_encoder.classes_).index('High_Risk')
    confidence = float(prediction_proba[0][high_risk_index])
    risk_label = "High_Risk" if confidence > 0.5 else "Low_Risk"

    try:
        patient_record = data.dict()
        patient_record['timestamp'] = datetime.now(timezone.utc)
        patient_record['prediction_result'] = risk_label
        patient_record['prediction_confidence'] = confidence
        db.collection('patient_records').add(patient_record)
        database_status = "Successfully saved patient record with prediction."
    except Exception as e:
        print(f" Error saving to Firestore: {e}")
        database_status = "Failed to save patient record."

    check_for_hotspot(db, data.Village, data.State)

    return {
        "prediction": risk_label,
        "confidence_high_risk": round(confidence, 4),
        "database_status": database_status
    }

