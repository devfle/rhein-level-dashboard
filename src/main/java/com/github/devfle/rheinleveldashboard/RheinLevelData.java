package com.github.devfle.rheinleveldashboard;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class RheinLevelData implements Serializable {
    private float value;
    private String timestamp;

    public float getValue() {
        return value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}