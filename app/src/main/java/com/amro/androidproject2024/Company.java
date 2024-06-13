package com.amro.androidproject2024;

public class Company {
    private final int userID;
    private final String companyName;
    private final String email;
    private final String phone;
    private final String address;

    // Constructor
    public Company(int userID, String companyName, String email, String phone, String address) {
        this.userID = userID;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters

    public int getUserID() {
        return userID;
    }


    public String getCompanyName() {
        return companyName;
    }


    public String getEmail() {
        return email;
    }


    // toString method for easy representation
    @Override
    public String toString() {
        return ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address;
    }
}
