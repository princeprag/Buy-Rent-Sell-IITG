package com.example.rentbuysell;

public class product_part {
    String MODE;
    String IMAGEURL;
    String MOBILENO;
    String CATEGORY;
    String NAME;
    String PRICE;
    String DESCRIPTION;
    //String ID;

    public product_part() {

    }

    public product_part(String MODE, String IMAGEURL, String MOBILENO, String CATAGORY, String NAME, String PRICE, String DESCRIPTION){ //String ID) {
        this.MODE = MODE;
        this.IMAGEURL = IMAGEURL;
        this.MOBILENO = MOBILENO;
        this.CATEGORY = CATAGORY;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.DESCRIPTION = DESCRIPTION;
       // this.ID = ID;
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

  /*  public String getID() {
        return ID;
    }*/
}
