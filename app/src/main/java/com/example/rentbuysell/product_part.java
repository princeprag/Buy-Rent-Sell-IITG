package com.example.rentbuysell;

public class product_part {
    String MODE;
    String IMAGEURL;
    String MOBILENO;
    String CATEGORY;
    String NAME;
    String PRICE;
    String DESCRIPTION;
    String UID;

    public product_part() {

    }

    public product_part(String MODE, String IMAGEURL, String MOBILENO, String CATAGORY, String NAME, String PRICE, String DESCRIPTION,String ID) {
        this.MODE = MODE;
        this.IMAGEURL = IMAGEURL;
        this.MOBILENO = MOBILENO;
        this.CATEGORY = CATAGORY;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.DESCRIPTION = DESCRIPTION;
        this.UID = ID;
    }

    public String getMODE() {
        return MODE;
    }

    public String getIMAGEURL() {
        return IMAGEURL;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public String getCATAGORY() {
        return CATEGORY;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getUID() {
        return UID;
    }

    public void setMODE(String MODE) {
        this.MODE = MODE;
    }

    public void setIMAGEURL(String IMAGEURL) {
        this.IMAGEURL = IMAGEURL;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
