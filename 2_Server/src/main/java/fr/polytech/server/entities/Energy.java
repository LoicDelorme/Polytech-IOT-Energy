package fr.polytech.server.entities;

public class Energy {

    private String userId;

    private String product;

    private String t1Ptec;

    private String t1Papp;

    private String t1Base;

    private String t2Ptec;

    private String t2Papp;

    private String t2Base;

    private String indexC1;

    private String indexC2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getT1Ptec() {
        return t1Ptec;
    }

    public void setT1Ptec(String t1Ptec) {
        this.t1Ptec = t1Ptec;
    }

    public String getT1Papp() {
        return t1Papp;
    }

    public void setT1Papp(String t1Papp) {
        this.t1Papp = t1Papp;
    }

    public String getT1Base() {
        return t1Base;
    }

    public void setT1Base(String t1Base) {
        this.t1Base = t1Base;
    }

    public String getT2Ptec() {
        return t2Ptec;
    }

    public void setT2Ptec(String t2Ptec) {
        this.t2Ptec = t2Ptec;
    }

    public String getT2Papp() {
        return t2Papp;
    }

    public void setT2Papp(String t2Papp) {
        this.t2Papp = t2Papp;
    }

    public String getT2Base() {
        return t2Base;
    }

    public void setT2Base(String t2Base) {
        this.t2Base = t2Base;
    }

    public String getIndexC1() {
        return indexC1;
    }

    public void setIndexC1(String indexC1) {
        this.indexC1 = indexC1;
    }

    public String getIndexC2() {
        return indexC2;
    }

    public void setIndexC2(String indexC2) {
        this.indexC2 = indexC2;
    }
}