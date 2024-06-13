package com.amro.androidproject2024.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amro.androidproject2024.R;
import com.amro.androidproject2024.model.Company;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCompanyManageActivity extends AppCompatActivity {

    private static final String TAG = AdminCompanyManageActivity.class.getName();
    private static final String JSON_PARSING_ERROR = "JSON parsing error: ";

    private final List<Company> companyList = new ArrayList<>();
    private ListView companiesListView;
    private ArrayAdapter<Company> adapter;

    private Company selectedCompany;
    private String userName;

    // TODO make use of these variables or remove them.
    private int userId;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company_mange);
        setUpViews();

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra(AdminHomeScreen.USER_ID, -1);
            userName = intent.getStringExtra(AdminHomeScreen.USER_NAME);
            userRole = intent.getIntExtra(AdminHomeScreen.ADMIN_ID, -1);

            fetchCompanyData();
        }

        companiesListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    selectedCompany = companyList.get(position);
                    Toast.makeText(this, "Selected: " + selectedCompany.getCompanyName(), Toast.LENGTH_SHORT).show();
                }
        );

    }

    private void setUpViews() {
        companiesListView = findViewById(R.id.companiesListView);
    }

    private void fetchCompanyData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/ReturnCompany.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleJsonResponse,
                error -> Log.e(TAG, "Volley error: " + error.getMessage())
        );

        queue.add(jsonObjectRequest);
    }

    private void handleJsonResponse(JSONObject response) {
        try {
            JSONArray companies = response.getJSONArray("companies");

            for (int i = 0; i < companies.length(); i++) {
                // Create an instance of Company_admin_class and add it to companyList
                companyList.add(Company.fromJson(companies.getJSONObject(i)));
            }

            adapter = new ArrayAdapter<>(
                    AdminCompanyManageActivity.this,
                    android.R.layout.simple_list_item_1,
                    companyList
            );

            companiesListView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e(TAG, JSON_PARSING_ERROR + e.getMessage());
        }
    }

    private void deleteCompany(Company company) {
        // Remove the customer from the list and notify the adapter
        companyList.remove(company);
        adapter.notifyDataSetChanged();
        selectedCompany = null; //DeleteCustomer.php

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(deleteCompanyRequest(company));

    }

    @NonNull
    private StringRequest deleteCompanyRequest(Company company) {
        String url = "http://10.0.2.2:80/androidPr/delete_company.php";
        return new StringRequest(
                Request.Method.POST,
                url,
                this::handleResponse,
                this::handleError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", String.valueOf(company.getUserID())); // Add UserID to POST parameters
                return params;
            }
        };
    }

    private void handleError(VolleyError error) {
        // TODO: Handle the error
        Log.e(TAG, "Volley error: " + error.getMessage());
        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            // Check if there's an error
            if (jsonResponse.has("error")) {
                // TODO Handle the error
                String error = jsonResponse.getString("error");
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + error);
                return;
            }

            // TODO: Handle the success response
            Toast.makeText(this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Log.e(TAG, JSON_PARSING_ERROR + e.getMessage());
            Toast.makeText(this, JSON_PARSING_ERROR + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDeleteButton(View view) {
        if (selectedCompany == null) {
            Toast.makeText(this, "No customer selected", Toast.LENGTH_SHORT).show();
            return;
        }

        deleteCompany(selectedCompany);
    }

    public void onClickBackButton(View view) {
        Intent intent = new Intent(this, AdminHomeScreen.class);
        intent.putExtra(AdminHomeScreen.USER_NAME, userName);
        startActivity(intent);
    }

}