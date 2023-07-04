package com.github.devfle.rheinleveldashboard;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class RheinLevelData {
    private float value;
    private String timestamp;

    private String stationName;

    public float getValue() {
        return value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStationName() {
        return stationName;
    }
}
