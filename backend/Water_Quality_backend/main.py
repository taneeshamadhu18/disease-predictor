import joblib
import pandas as pd
from fastapi import FastAPI, HTTPException
import firebase_admin
from firebase_admin import credentials, firestore
import os
from datetime import datetime

# Initialize FastAPI app
app = FastAPI(title="Water Quality Prediction API")

# Load your trained pipeline
pipeline = joblib.load("final_clean_pipeline.joblib")

# Initialize Firestore
# Path to your service account key file
try:
    # Use the service account key from the environment variable in production
    if os.environ.get("GOOGLE_APPLICATION_CREDENTIALS"):
        cred = credentials.Certificate(os.environ.get("GOOGLE_APPLICATION_CREDENTIALS"))
    else: # Use local file for development
        cred = credentials.Certificate("firebase-key.json")
    
    firebase_admin.initialize_app(cred)
    db = firestore.client()
    print("🚀 Firestore database connected successfully!")
except Exception as e:
    print(f"❌ Error initializing Firestore: {e}")
    db = None # Set db to None if connection fails

@app.get("/")
def root():
    return {"message": "🚀 Water Quality Prediction API is running"}

@app.post("/predict_water_quality/")
def predict_water_quality(data: dict):
    """
    Endpoint to predict water quality risk and store the result in Firestore.
    """
    if db is None:
        raise HTTPException(status_code=500, detail="Database connection failed.")

    # Convert input to DataFrame for the pipeline
    try:
        df = pd.DataFrame([data])
    except ValueError as e:
        raise HTTPException(status_code=400, detail=f"Invalid input data: {e}")

    # Predict class and probabilities
    pred_class = pipeline.predict(df)[0]
    pred_proba = pipeline.predict_proba(df)[0]
    class_labels = pipeline.classes_

    # Confidence calculation
    pred_idx = list(class_labels).index(pred_class)
    confidence = round(pred_proba[pred_idx] * 100, 2)

    # Prepare data for Firestore
    record = {
        "state": data.get("State", ""),
        "village": data.get("Village", ""),
        "input_data": data, # Store the full input payload
        "prediction": str(pred_class),
        "confidence": confidence,
        "timestamp": datetime.utcnow()
    }

    # Add a new document to the 'predictions' collection
    try:
        doc_ref = db.collection("predictions").document()
        doc_ref.set(record)
        print(f"✅ Prediction for {data.get('Village', '')} stored in Firestore.")
    except Exception as e:
        print(f"❌ Error storing data in Firestore: {e}")


    return {
        "state": data.get("State", ""),
        "village": data.get("Village", ""),
        "prediction": str(pred_class),
        "confidence": confidence
    }