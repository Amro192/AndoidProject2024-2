package com.amro.androidproject2024;

public class Car {
    private final String make;
    private final String model;
    private final String year;
    private final String price;
    private String companyName;
    private final String image;

    public Car(String make, String model, String year, String price, String image, String companyName) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.image = image;
        this.companyName = companyName;

    }

    public Car(String make, String model, String year, String price, String image) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.image = image;
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
