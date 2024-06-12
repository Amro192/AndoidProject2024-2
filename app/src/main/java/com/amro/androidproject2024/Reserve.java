package com.amro.androidproject2024;

public class Reserve {
    private String make;
    private String model;
    private String year;
    private String price;
    private String companyName;
    private String image;
    private String startDateTime;
    private String endDateTime;

    public Reserve(String make, String model, String year, String price, String image, String companyName, String startDateTime, String endDateTime) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.image = image;
        this.companyName = companyName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

    }

    public String getCompanyName() {
        return companyName;
    }

    public String getImage() {
        return image;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }
    public String getStartDateTime() {
        return startDateTime;
    }
    public String getEndDateTime() {
        return endDateTime;
    }


}
