package fr.polytech.raspberry.entities;

import javax.json.bind.annotation.JsonbProperty;

public class EnergyReport {

    private String userId;

    private String product;

    private String t1Ptec;

    private int t1Papp;

    private int t1Base;

    private String t2Ptec;

    private int t2Papp;

    private int t2Base;

    private int indexC1;

    private int indexC2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    @JsonbProperty("product")
    public void setProduct(String product) {
        this.product = product;
    }

    public String getT1Ptec() {
        return t1Ptec;
    }

    @JsonbProperty("T1_PTEC")
    public void setT1Ptec(String t1Ptec) {
        this.t1Ptec = t1Ptec;
    }

    public int getT1Papp() {
        return t1Papp;
    }

    @JsonbProperty("T1_PAPP")
    public void setT1Papp(int t1Papp) {
        this.t1Papp = t1Papp;
    }

    public int getT1Base() {
        return t1Base;
    }

    @JsonbProperty("T1_BASE")
    public void setT1Base(int t1Base) {
        this.t1Base = t1Base;
    }

    public String getT2Ptec() {
        return t2Ptec;
    }

    @JsonbProperty("T2_PTEC")
    public void setT2Ptec(String t2Ptec) {
        this.t2Ptec = t2Ptec;
    }

    public int getT2Papp() {
        return t2Papp;
    }

    @JsonbProperty("T2_PAPP")
    public void setT2Papp(int t2Papp) {
        this.t2Papp = t2Papp;
    }

    public int getT2Base() {
        return t2Base;
    }

    @JsonbProperty("T2_BASE")
    public void setT2Base(int t2Base) {
        this.t2Base = t2Base;
    }

    public int getIndexC1() {
        return indexC1;
    }

    @JsonbProperty("INDEX_C1")
    public void setIndexC1(int indexC1) {
        this.indexC1 = indexC1;
    }

    public int getIndexC2() {
        return indexC2;
    }

    @JsonbProperty("INDEX_C2")
    public void setIndexC2(int indexC2) {
        this.indexC2 = indexC2;
    }
}