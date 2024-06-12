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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_customer_mange_activity extends AppCompatActivity {

    private ListView listView_Customer_admin_mit;
    private Button btn_delete_customers_admin;
    private ArrayAdapter<CustomerClassm> adapter;
    private ArrayList<CustomerClassm> customerList;
    private CustomerClassm selectedCustomer;
    private Button buttom_back_admin_in_customer_mange;

    private int userId;
    private String userName;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_customer_mange);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Admin_customer_mange_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("user_id", -1);
            userName = intent.getStringExtra("user_name");
            userRole = intent.getIntExtra("admin_id", -1);
            setUpViews();
            fetchCustomerData();
        }


        listView_Customer_admin_mit.setOnItemClickListener((parent, view, position, id) -> {
            selectedCustomer = customerList.get(position);
            Toast.makeText(Admin_customer_mange_activity.this, "Selected: " + selectedCustomer.getName(), Toast.LENGTH_SHORT).show();
        });

        btn_delete_customers_admin.setOnClickListener(v -> {
            if (selectedCustomer != null) {
                deleteCustomer(selectedCustomer);
            } else {
                Toast.makeText(Admin_customer_mange_activity.this, "No customer selected", Toast.LENGTH_SHORT).show();
            }
        });
        buttom_back_admin_in_customer_mange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_customer_mange_activity.this, test_bootunssss.class);
                startActivity(intent);
            }
        });
    }

    private void setUpViews() {
        listView_Customer_admin_mit = findViewById(R.id.listView_Customer_admin_mit);
        btn_delete_customers_admin = findViewById(R.id.btn_delete_customers_admin);
        buttom_back_admin_in_customer_mange=findViewById(R.id.buttom_back_admin_in_customer_mange);

    }

    private void fetchCustomerData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/Return_Customers.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        customerList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int userID = jsonObject.getInt("UserID");
                                int rentalID = jsonObject.getInt("RentalID");
                                String name = jsonObject.getString("Name");
                                String email = jsonObject.getString("Email");
                                String phone = jsonObject.getString("Phone");
                                Log.e("Tag", "RESPONSE: " + rentalID + " " + name + " " + email + " " + phone);

                                CustomerClassm customer = new CustomerClassm(userID, rentalID, name, email, phone);
                                customerList.add(customer);
                            }
                            adapter = new ArrayAdapter<>(
                                    Admin_customer_mange_activity.this,
                                    android.R.layout.simple_list_item_1,
                                    customerList);
                            listView_Customer_admin_mit.setAdapter(adapter);
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

        queue.add(jsonArrayRequest);
    }

    private void deleteCustomer(CustomerClassm customer) {
        // Remove the customer from the list and notify the adapter
        customerList.remove(customer);
        adapter.notifyDataSetChanged();
        selectedCustomer = null;//DeleteCustomer.php
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/DeleteCustomer.php";

        String userId = String.valueOf(customer.getUserID());

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
                                Toast.makeText(Admin_customer_mange_activity.this, errorMessage, Toast.LENGTH_SHORT).show();

                                // Handle error
                            } else {
                                // Handle success
                                String message = jsonResponse.getString("message");
                                // Do something with the success message
                                Toast.makeText(Admin_customer_mange_activity.this, message, Toast.LENGTH_SHORT).show();
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
