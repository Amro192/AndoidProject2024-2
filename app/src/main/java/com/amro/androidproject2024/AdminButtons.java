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

public class AdminButtons extends AppCompatActivity {
    private Button button_admin_show_customer;

    private Button button_admin_show_company;

    private Button button_add_admin_mitri;
    private Button botton_edit_profile_mitri_admin;

    private int userId;
    private String userName;
    private int userRole;
    private TextView text_view_admin_show_name;

    private Button botton_logout_mitri_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_bootunssss);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupviews();
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("user_id", -1);
           userName = intent.getStringExtra("user_name");
           userRole = intent.getIntExtra("admin_id", -1);
            text_view_admin_show_name.setText("Wlecome "+userName);

        }
        button_admin_show_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AdminButtons.this, Admin_customer_mange_activity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("user_name", userName);
            intent.putExtra("admin_id", userRole);
            startActivity(intent);
            }
        });

        button_admin_show_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(AdminButtons.this, Admin_Company_Mange_Activity.class);
           startActivity(intent);
            }
        });

        button_add_admin_mitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(AdminButtons.this, add_admin_activity.class);
           startActivity(intent);
            }
        });

        botton_edit_profile_mitri_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        botton_logout_mitri_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminButtons.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setupviews() {
        button_admin_show_customer = findViewById(R.id.button_admin_show_customer);
        button_admin_show_company = findViewById(R.id.button_admin_show_company);
        button_add_admin_mitri = findViewById(R.id.button_add_admin_mitri);
        botton_edit_profile_mitri_admin = findViewById(R.id.botton_edit_profile_mitri_admin);
        text_view_admin_show_name = findViewById(R.id.text_view_admin_show_name);
        botton_logout_mitri_admin = findViewById(R.id.botton_logout_mitri_admin);

    }
}