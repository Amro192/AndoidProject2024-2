package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        setupviews();

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("user_id", -1);
            userName = intent.getStringExtra("name");
            userRole = intent.getIntExtra("company_id", -1);
            text_view_admin_show_name.setText("Welcome: " + userName);

        }
        showAllCarsButton.setOnClickListener(v -> {
            Intent intent12 = new Intent(CustomerButtons.this, CarList.class);
            intent12.putExtra("user_id", userId);
            intent12.putExtra("user_name", userName);
            intent12.putExtra("admin_id", userRole);
            startActivity(intent12);
        });

        showAllReservationsButton.setOnClickListener(v -> {
            Intent intentReservations = new Intent(CustomerButtons.this, CarList.class);
            startActivity(intentReservations);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intentLogout = new Intent(CustomerButtons.this, MainActivity.class);
            startActivity(intentLogout);
        });
    }

    public void setupviews() {
        showAllCarsButton = findViewById(R.id.showAllCars);
        showAllReservationsButton = findViewById(R.id.showReservations);
        text_view_admin_show_name = findViewById(R.id.welcomeCustomer);
        logoutButton = findViewById(R.id.logOut);
    }

}