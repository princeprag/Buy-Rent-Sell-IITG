package com.example.rentbuysell.Notification;

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;
    private String prod_name;
    private String prod_id;
    private String prod_des;
    private String prod_price;
    private String mobile;
    private String imageURL;
    private String category;
    private String modd;

    public Data(String user, int icon, String body, String title, String sented, String prod_name, String prod_id, String prod_des, String prod_price, String mobile, String imageURL, String category, String modd) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sented = sented;
        this.prod_name = prod_name;
        this.prod_id = prod_id;
        this.prod_des = prod_des;
        this.prod_price = prod_price;
        this.mobile = mobile;
        this.imageURL = imageURL;
        this.category = category;
        this.modd = modd;
    }

    public Data() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_des() {
        return prod_des;
    }

    public void setProd_des(String prod_des) {
        this.prod_des = prod_des;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModd() {
        return modd;
    }

    public void setModd(String modd) {
        this.modd = modd;
    }

/*
        public Data(String user, int icon, String body, String title, String sended) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sented = sended;
    }
    public Data() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }*/
}
