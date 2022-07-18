package com.mirror.oasis.home;

class HomeData {
    private String key;
    private String location;
    private String name;
    private String detail;
    private String date;
    private String photo;

    public HomeData(){}

    public HomeData(String key, String location, String name, String detail, String date, String photo) {
        this.key = key;
        this.location = location;
        this.name = name;
        this.detail = detail;
        this.date = date;
        this.photo = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
