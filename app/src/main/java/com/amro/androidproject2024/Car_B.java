package com.amro.androidproject2024;

public class Car_B {
    private String make;
    private String model;
    private String year;
    private String price;
    private String companyName;
    private String image;

    public Car_B(String make, String model, String year, String price, String companyName, String image) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.companyName = companyName;

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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
