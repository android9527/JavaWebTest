package com.servlet.json.entity;

import java.util.ArrayList;

/**
 * Created by chenfeiyue on 16/9/2.
 */
public class AvgData {
    private ArrayList<String> labels;

    private ArrayList<String> pm;

    private ArrayList<String> temperature;

    private ArrayList<String> humidity;

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<String> getPm() {
        return pm;
    }

    public void setPm(ArrayList<String> pm) {
        this.pm = pm;
    }

    public ArrayList<String> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<String> temperature) {
        this.temperature = temperature;
    }

    public ArrayList<String> getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList<String> humidity) {
        this.humidity = humidity;
    }
}
