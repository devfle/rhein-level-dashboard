package com.github.devfle.rheinleveldashboard;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class RheinLevelFilterController implements Serializable {
    private short filterValue = 100;
    private byte minValue = 100;
    private short maxValue = 400;
    private byte steps = 20;

    public short getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(short filterValue) {
        this.filterValue = filterValue;
    }

    public byte getMinValue() {
        return minValue;
    }

    public void setMinValue(byte minValue) {
        this.minValue = minValue;
    }

    public short getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(short maxValue) {
        this.maxValue = maxValue;
    }

    public byte getSteps() {
        return steps;
    }

    public void setSteps(byte steps) {
        this.steps = steps;
    }
}
