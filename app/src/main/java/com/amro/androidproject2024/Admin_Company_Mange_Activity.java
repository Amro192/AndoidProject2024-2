package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_Company_Mange_Activity extends AppCompatActivity {

   private ListView Company_Mange_Activity_admin_mit;

   private Button btn_delete_company_mit_admin;

   private Button button_back_admin_in_Company_mange;

   // private ArrayList<Company_admin_class> companyList;

   // private ArrayAdapter<Company_admin_class> adapter;
    private Company selectedCompany;

    ArrayList<Company> companyList;
    ArrayAdapter<Company> adapter;
    private Company selectedcompany;

    private int userId;
    private String userName;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_company_mange);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Admin_Company_Mange_Activity_mit_super), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("user_id", -1);
            userName = intent.getStringExtra("user_name");
            userRole = intent.getIntExtra("admin_id", -1);
            setupview();
            fetchCompanyData();
        }

        Company_Mange_Activity_admin_mit.setOnItemClickListener((parent, view, position, id) -> {
            selectedcompany = companyList.get(position);
            Toast.makeText(Admin_Company_Mange_Activity.this, "Selected: " + selectedcompany.getCompanyName(), Toast.LENGTH_SHORT).show();
        });

        btn_delete_company_mit_admin.setOnClickListener(v -> {
            if (selectedcompany != null) {
                deleteCompany(selectedcompany);
            } else {
                Toast.makeText(Admin_Company_Mange_Activity.this, "No customer selected", Toast.LENGTH_SHORT).show();
            }
        });
        button_back_admin_in_Company_mange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Company_Mange_Activity.this, AdminButtons.class);
                intent.putExtra("user_name", userName);
                startActivity(intent);
            }
        });
    }
    public void setupview(){
        Company_Mange_Activity_admin_mit = findViewById(R.id.Company_Mange_Activity_admin_mit);
        btn_delete_company_mit_admin = findViewById(R.id.btn_delete_company_mit_admin);
        button_back_admin_in_Company_mange = findViewById(R.id.button_back_admin_in_Company_mange);
    }

    private void fetchCompanyData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/ReturnCompany.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray companies = response.getJSONArray("companies");
                             companyList = new ArrayList<>();
                            for (int i = 0; i < companies.length(); i++) {
                                JSONObject jsonObject = companies.getJSONObject(i);
                                int companyID = jsonObject.getInt("CompanyID");
                                int userID = jsonObject.getInt("UserID");
                                String companyName = jsonObject.getString("CompanyName");
                                String email = jsonObject.getString("Email");
                                String phone = jsonObject.getString("Phone");
                                String address = jsonObject.getString("Address");
                                Log.e("Tag", "RESPONSE: " + companyID + " " + userID + " " + companyName + " " + email + " " + phone + " " + address);
                                // Create an instance of Company_admin_class and add it to companyList
                                Company company = new Company(userID, companyName, email, phone, address);
                                companyList.add(company);
                            }
                             adapter = new ArrayAdapter<>(
                                    Admin_Company_Mange_Activity.this,
                                    android.R.layout.simple_list_item_1,
                                    companyList);
                            Company_Mange_Activity_admin_mit.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.e("AdminActivity", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AdminActivity", "Volley error: " + error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private void deleteCompany(Company company) {
        // Remove the customer from the list and notify the adapter
        companyList.remove(company);
        adapter.notifyDataSetChanged();
        selectedcompany = null;//DeleteCustomer.php
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/delete_company.php";

        String userId = String.valueOf(company.getUserID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // Check if there's an error
                            if (jsonResponse.has("error")) {
                                String errorMessage = jsonResponse.getString("error");
                                // Do something with the error message
                                Toast.makeText(Admin_Company_Mange_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();

                                // Handle error
                            } else {
                                // Handle success
                                String message = jsonResponse.getString("message");
                                // Do something with the success message
                                Toast.makeText(Admin_Company_Mange_Activity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle Volley error
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", userId); // Add UserID to POST parameters
                return params;
            }
        };

// Add the request to the RequestQueue
        queue.add(stringRequest);


    }

}