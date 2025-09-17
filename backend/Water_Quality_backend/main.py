import joblib
import pandas as pd
from fastapi import FastAPI, HTTPException
from datetime import datetime

# Initialize FastAPI app
app = FastAPI(title="Water Quality Prediction API")

# Load your trained pipeline
pipeline = joblib.load("final_clean_pipeline.joblib")

@app.get("/")
def root():
    return {"message": "Water Quality Prediction API is running"}

@app.post("/predict_water_quality/")
def predict_water_quality(data: dict):
    """
    Endpoint to predict water quality risk.
    """
    try:
        df = pd.DataFrame([data])
    except ValueError as e:
        raise HTTPException(status_code=400, detail=f"Invalid input data: {e}")

    try:
        # Predict class and probabilities
        pred_class = pipeline.predict(df)[0]
        pred_proba = pipeline.predict_proba(df)[0]
        class_labels = pipeline.classes_

        # Confidence calculation
        pred_idx = list(class_labels).index(pred_class)
        confidence = round(pred_proba[pred_idx] * 100, 2)

        return {
            "state": data.get("State", ""),
            "village": data.get("Village", ""),
            "prediction": str(pred_class),
            "confidence": confidence,
            "timestamp": datetime.utcnow()
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Prediction failed: {e}")
