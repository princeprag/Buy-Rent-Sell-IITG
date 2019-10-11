package com.example.rentbuysell;
public class users {

    String NAME,HOSTEL,ROLL_NUMBER,EMAIL,MOILE_NUMBER,IMAGE_URL;

    public users() {

    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public users(String name, String hostel, String RollNumber, String Email, String mobileno, String ImageUrl){
        this.NAME=name;
        this.EMAIL=Email;
        this.HOSTEL=hostel;
        this.ROLL_NUMBER=RollNumber;
        this.MOILE_NUMBER=mobileno;
        this.IMAGE_URL=ImageUrl;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getHOSTEL() {
        return HOSTEL;
    }

    public void setHOSTEL(String HOSTEL) {
        this.HOSTEL = HOSTEL;
    }

    public String getROLL_NUMBER() {
        return ROLL_NUMBER;
    }

    public void setROLL_NUMBER(String ROLL_NUMBER) {
        this.ROLL_NUMBER = ROLL_NUMBER;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMOILE_NUMBER() {
        return MOILE_NUMBER;
    }

    public void setMOILE_NUMBER(String MOILE_NUMBER) {
        this.MOILE_NUMBER = MOILE_NUMBER;
    }


}
