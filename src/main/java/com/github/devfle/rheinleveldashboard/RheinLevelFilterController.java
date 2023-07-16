package com.github.devfle.rheinleveldashboard;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class RheinLevelFilterController implements Serializable {
    private short filterValue = 100;
    private final byte minValue = 100;
    private final short maxValue = 400;
    private final byte steps = 20;

    public short getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(short filterValue) {
        this.filterValue = filterValue;
    }

    public byte getMinValue() {
        return minValue;
    }

    public short getMaxValue() {
        return maxValue;
    }

    public byte getSteps() {
        return steps;
    }
}
