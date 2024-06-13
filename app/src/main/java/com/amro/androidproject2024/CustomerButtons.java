package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerButtons extends AppCompatActivity {
    private Button showAllCarsButton;

    private Button showAllReservationsButton;

    private int userId;
    private String userName;
    private int userRole;
    private TextView text_view_admin_show_name;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_buttons);
        setUpViews();

        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("name");
            text_view_admin_show_name.setText(String.format("%s%s", getString(R.string.welcome_back), userName));
        }

        showAllCarsButton.setOnClickListener(v -> {
            Intent customerIntent = new Intent(CustomerButtons.this, CarListForCustomer.class);
            customerIntent.putExtra("userName", userName);
            customerIntent.putExtra("customerID", userRole);
            startActivity(customerIntent);
        });

        showAllReservationsButton.setOnClickListener(v -> {
            Intent intentReservations = new Intent(CustomerButtons.this, CarListForCompany.class);
            startActivity(intentReservations);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intentLogout = new Intent(CustomerButtons.this, MainActivity.class);
            startActivity(intentLogout);
        });
    }

    public void setUpViews() {
        showAllCarsButton = findViewById(R.id.showAllCars);
        showAllReservationsButton = findViewById(R.id.showReservations);
        text_view_admin_show_name = findViewById(R.id.welcomeCustomer);
        logoutButton = findViewById(R.id.logOut);
    }

}