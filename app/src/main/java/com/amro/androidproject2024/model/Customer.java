package com.amro.androidproject2024.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {

    private final int userID;
    private final int rentalID;
    private final String name;
    private final String email;
    private final String phone;

    // Constructor
    public Customer(int userID, int rentalID, String name, String email, String phone) {
        this.userID = userID;
        this.rentalID = rentalID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public static Customer fromJson(JSONObject json) throws JSONException {

        return new Customer(
                json.getInt("UserID"),
                json.getInt("RentalID"),
                json.getString("Name"),
                json.getString("Email"),
                json.getString("Phone")
        );

    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @NonNull
    @Override
    public String toString() {
        return "Customer Name " + name + " + email " + email + " + phone " + phone;
    }
}
