package com.amro.androidproject2024.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amro.androidproject2024.MainActivity;
import com.amro.androidproject2024.R;

public class AdminHomeScreen extends AppCompatActivity {
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String ADMIN_ID = "admin_id";

    private TextView showAdminNameTextView;

    private String userName;
    private int userId;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bootunssss);
        setUpViews();

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra(USER_ID, -1);
            userName = intent.getStringExtra(USER_NAME);
            userRole = intent.getIntExtra(ADMIN_ID, -1);
            showAdminNameTextView.setText("Welcome, " + userName);
        }

    }

    private void setUpViews() {
        showAdminNameTextView = findViewById(R.id.showAdminNameTextView);
    }

    public void onClickShowCustomersButton(View ignored) {
        Intent adminCustomerIntent = new Intent(this, AdminCustomerManageActivity.class);
        adminCustomerIntent.putExtra(USER_ID, userId);
        adminCustomerIntent.putExtra(USER_NAME, userName);
        adminCustomerIntent.putExtra(ADMIN_ID, userRole);
        startActivity(adminCustomerIntent);
    }

    public void onClickShowCompanyButton(View ignored) {
        Intent adminCompanyIntent = new Intent(this, AdminCompanyManageActivity.class);
        adminCompanyIntent.putExtra(USER_ID, userId);
        adminCompanyIntent.putExtra(USER_NAME, userName);
        adminCompanyIntent.putExtra(ADMIN_ID, userRole);
        startActivity(adminCompanyIntent);
    }

    public void onClickAddAdminButton(View ignored) {
        Intent addAdminIntent = new Intent(this, AddAdminActivity.class);
        addAdminIntent.putExtra(USER_NAME, userName);
        startActivity(addAdminIntent);
    }

    public void onClickLogOutButton(View ignored) {
        Intent logOutToMainIntent = new Intent(this, MainActivity.class);
        startActivity(logOutToMainIntent);
    }
}