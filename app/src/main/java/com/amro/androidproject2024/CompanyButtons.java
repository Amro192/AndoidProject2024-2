package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CompanyButtons extends AppCompatActivity {
    private String companyId;
    private TextView welcomeCompanys;

    private String companyName;
    private Button showAllCars_Company;
    private Button showMyCompanyCars;
    private Button logOut_Company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_buttons);
        setUpViews();
        Intent intent = getIntent();

        if (intent != null) {
            companyName = intent.getStringExtra("name");
            companyId = intent.getStringExtra("id");
            welcomeCompanys.setText(String.format("%s%s", getString(R.string.welcome_back), companyName));

        showAllCars_Company.setOnClickListener(v -> {
            Intent companyIntent = new Intent(CompanyButtons.this, CarListForCompany.class);
            companyIntent.putExtra("customerID", companyId);
            companyIntent.putExtra("flage", "NO");

            startActivity(companyIntent);
        });
        showMyCompanyCars.setOnClickListener(v -> {
            Intent companyMyIntent = new Intent(CompanyButtons.this, CarListForCompany.class);
            companyMyIntent.putExtra("customerID", companyId);
            companyMyIntent.putExtra("flage", "YES");

            startActivity(companyMyIntent);
        });
        logOut_Company.setOnClickListener(v -> {
            Intent intentLogout = new Intent(CompanyButtons.this, MainActivity.class);
            startActivity(intentLogout);
        });
        }
    }
    public void setUpViews() {
        showAllCars_Company = findViewById(R.id.showAllCars_Company);
        showMyCompanyCars = findViewById(R.id.showMyCompanyCars);
        logOut_Company = findViewById(R.id.logOut_Company);
        welcomeCompanys = findViewById(R.id.welcomeCompanys);
    }
}