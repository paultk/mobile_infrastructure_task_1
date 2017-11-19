package com.example.paulthomaskorsvold.sensorapplicatinthird;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paulthomaskorsvold on 11/18/17.
 */

public class SensorData {
    public String mX,mY, mZ, mTimestamp;

    public SensorData() {
    }

    public SensorData(String mX, String mY, String mZ) {
        this.mX = mX;
        this.mY = mY;
        this.mZ = mZ;
        this.mTimestamp = "" + System.currentTimeMillis();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mX", mX);
        result.put("mY", mY);
        result.put("mZ", mZ);
        result.put("mTimestamp", mTimestamp);
        return result;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "mX='" + mX + '\'' +
                ", mY='" + mY + '\'' +
                ", mZ='" + mZ + '\'' +
                ", mTimestamp='" + mTimestamp + '\'' +
                '}';
    }

    public String getmX() {
        return mX;
    }

    public void setmX(String mX) {
        this.mX = mX;
    }

    public String getmY() {
        return mY;
    }

    public void setmY(String mY) {
        this.mY = mY;
    }

    public String getmZ() {
        return mZ;
    }

    public void setmZ(String mZ) {
        this.mZ = mZ;
    }

    public String getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }
}
/*
* myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);
        Log.d(TAG, "Value is: " + value);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w(TAG, "Failed to read value.", error.toException());
    }
});*/