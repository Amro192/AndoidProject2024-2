package com.amro.androidproject2024.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static Company fromJson(JSONObject jsonObject) throws JSONException {
        return new Company(
                jsonObject.getInt("UserID"),
                jsonObject.getString("CompanyName"),
                jsonObject.getString("Email"),
                jsonObject.getString("Phone"),
                jsonObject.getString("Address")
        );
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
    @NonNull
    @Override
    public String toString() {
        return ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address;
    }
}
