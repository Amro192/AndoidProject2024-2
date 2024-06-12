package com.amro.androidproject2024;

public class Company_admin_class {
    private int companyID;
    private int userID;
    private String companyName;
    private String email;
    private String phone;
    private String address;

    // Constructor
    public Company_admin_class(int companyID, int userID, String companyName, String email, String phone, String address) {
        this.companyID = companyID;
        this.userID = userID;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
