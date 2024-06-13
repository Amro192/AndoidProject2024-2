package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpAsActivity extends AppCompatActivity {
    private Button AsRenterBtn;
    private Button AsCompanyBtn;
    private Button loginbtn_sign_up_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_as_actvity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ActivitySign12), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpViews();

        loginbtn_sign_up_1.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpAsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        AsRenterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpAsActivity.this, SignUpAsRentalActivity.class);
            startActivity(intent);
        });

        AsCompanyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpAsActivity.this, SignUpAsCompanyActivity.class);
            startActivity(intent);
        });

    }

    public void setUpViews() {
        loginbtn_sign_up_1 = findViewById(R.id.loginbtn_sign_up_1);
        AsRenterBtn = findViewById(R.id.AsRenterBtn);
        AsCompanyBtn = findViewById(R.id.AsCompanyBtn);
    }
}