package com.amro.androidproject2024;

public class Car_B {
    private String make;
    private String model;
    private String year;
    private String price;
    private String companyName;
    private String image;

    public Car_B(String make, String model, String year, String price, String image, String companyID) {
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

}
