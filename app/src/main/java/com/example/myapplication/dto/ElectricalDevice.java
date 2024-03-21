package com.example.myapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
public class ElectricalDevice {
    private String months;
    private Led led;
    private Incandescent incandescent;
    private Fluorescent fluorescent;
    private Halogen halogen;
    private String nameDev;

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Led getLed() {
        return led;
    }

    public void setLed(Led led) {
        this.led = led;
    }

    public Incandescent getIncandescent() {
        return incandescent;
    }

    public void setIncandescent(Incandescent incandescent) {
        this.incandescent = incandescent;
    }

    public Fluorescent getFluorescent() {
        return fluorescent;
    }

    public void setFluorescent(Fluorescent fluorescent) {
        this.fluorescent = fluorescent;
    }

    public Halogen getHalogen() {
        return halogen;
    }

    public void setHalogen(Halogen halogen) {
        this.halogen = halogen;
    }

    public String getNameDev() {
        return nameDev;
    }

    public void setNameDev(String nameDev) {
        this.nameDev = nameDev;
    }
}
