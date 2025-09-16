import joblib
import pandas as pd
from fastapi import FastAPI

app = FastAPI(title="Water Quality Prediction API")

# Load your trained pipeline
pipeline = joblib.load("final_clean_pipeline.joblib")

@app.get("/")
def root():
    return {"message": "🚀 Water Quality Prediction API is running"}

@app.post("/predict_water_quality/")
def predict_water_quality(data: dict):
    """
    Endpoint to predict water quality risk for a given region based on IoT data.
    Expects JSON input with keys:
    - State
    - Village
    - Water_pH
    - Turbidity_NTU
    - Chlorine_mg_L
    - EColi_MPN
    - Rainfall_mm
    - AvgTemperature_C
    - BacterialPresence
    """
    # Convert input to DataFrame for the pipeline
    df = pd.DataFrame([data])

    # Predict class and probabilities
    pred_class = pipeline.predict(df)[0]
    pred_proba = pipeline.predict_proba(df)[0]
    class_labels = pipeline.classes_

    # Confidence calculation
    pred_idx = list(class_labels).index(pred_class)
    confidence = pred_proba[pred_idx] * 100

    return {
        "state": data.get("State", ""),
        "village": data.get("Village", ""),
        "prediction": str(pred_class),
        "confidence": round(confidence, 2)
    }
