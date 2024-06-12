package com.amro.androidproject2024;

public class CustomerClass {

    private int userID;
    private int rentalID;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public CustomerClass(int userID, int rentalID, String name, String email, String phone) {
        this.userID = userID;
        this.rentalID = rentalID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "Customer Name " + name + " + email " +email + " + phone " + phone ;
    }
}
