package com.servlet.json.entity;

import java.util.ArrayList;

/**
 * Created by chenfeiyue on 16/8/31.
 */
public class PMModel {

    private String draw;
    private int recordsTotal;
    private int recordsFiltered;

    ArrayList<PMEntity> data;

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public ArrayList<PMEntity> getEntities() {
        return data;
    }

    public void setEntities(ArrayList<PMEntity> entities) {
        this.data = entities;
    }
}
