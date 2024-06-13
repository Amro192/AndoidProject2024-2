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
import com.amro.androidproject2024.model.Customer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCustomerManageActivity extends AppCompatActivity {

    private static final String TAG = AdminCustomerManageActivity.class.getName();
    private ListView customersList;
    private final List<Customer> customerList = new ArrayList<>();
    private ArrayAdapter<Customer> adapter;
    private Customer selectedCustomer;

    private String userName;

    // TODO make use of these variables or remove them.
    private int userId;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer_mange);
        setUpViews();

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra(AdminHomeScreen.USER_ID, -1);
            userName = intent.getStringExtra(AdminHomeScreen.USER_NAME);
            userRole = intent.getIntExtra(AdminHomeScreen.ADMIN_ID, -1);

            fetchCustomerData();
        }

        customersList.setOnItemClickListener(
                (parent, view, position, id) -> {
                    selectedCustomer = customerList.get(position);
                    Toast.makeText(AdminCustomerManageActivity.this, "Selected: " + selectedCustomer.getName(), Toast.LENGTH_SHORT).show();
                }
        );

    }

    private void setUpViews() {
        customersList = findViewById(R.id.customersList);
    }


    private void fetchCustomerData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/Return_Customers.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                this::getCustomersFromResponse,
                error -> Log.e(TAG, "Volley error: " + error.getMessage())
        );

        queue.add(jsonArrayRequest);
    }

    private void getCustomersFromResponse(JSONArray response) {
        try {

            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                customerList.add(Customer.fromJson(jsonObject));
            }

            adapter = new ArrayAdapter<>(
                    AdminCustomerManageActivity.this,
                    android.R.layout.simple_list_item_1,
                    customerList);

            customersList.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }

    private void deleteCustomer(Customer customer) {
        // Remove the customer from the list and notify the adapter
        customerList.remove(customer);
        adapter.notifyDataSetChanged();
        selectedCustomer = null;//DeleteCustomer.php
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = deleteCustomerRequest(customer);
        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }

    @NonNull
    private StringRequest deleteCustomerRequest(Customer customer) {
        String url = "http://10.0.2.2:80/androidPr/DeleteCustomer.php";
        return new StringRequest(Request.Method.POST, url,
                this::handleResponse,
                error -> Log.e(TAG, "Volley error: " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", String.valueOf(customer.getUserID())); // Add UserID to POST parameters
                return params;
            }
        };
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            // Check if there's an error
            if (jsonResponse.has("error")) {
                String errorMessage = jsonResponse.getString("error");
                // Do something with the error message
                Log.e(TAG, "Error: " + errorMessage);
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

                return;
            }

            //  TODO : Handle success
            String message = jsonResponse.getString("message");
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            // TODO: Handle JSON parsing error
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }

    public void onDeleteCustomerButtonClick(View ignored) {
        if (selectedCustomer == null) {
            Log.e(TAG, "No customer selected");
            Toast.makeText(this, "No customer selected", Toast.LENGTH_SHORT).show();
            return;
        }

        deleteCustomer(selectedCustomer);
    }

    public void onClickBackButton(View ignored) {
        startActivity(
                new Intent(this, AdminHomeScreen.class)
                        .putExtra(AdminHomeScreen.USER_NAME, userName)
        );
    }
}
