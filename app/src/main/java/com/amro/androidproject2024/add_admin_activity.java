package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class add_admin_activity extends AppCompatActivity {
       private EditText fullNameForAdmin_add_Admin_mit;
       private EditText emailForAdmin_add_Admin_mit;
       private EditText passwordForAdmin_add_Admin_mit;

       private EditText confirmPassword_for_admin_add_mit;

       private Button Add_admin_button_mit;

       private Button buttom_back_admin_in_add_admin_mit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_admin_activty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_admin_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpValues();

        Add_admin_button_mit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    add_Admin();
                    Toast.makeText(add_admin_activity.this, "Admin Added Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttom_back_admin_in_add_admin_mit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_admin_activity.this, test_bootunssss.class);
                startActivity(intent);

            }
        });
    }

    public void add_Admin() {
        String url = "http://10.0.2.2:80/androidPr/AddAdmin.php";
        RequestQueue queue = Volley.newRequestQueue(add_admin_activity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Tag", "RESPONSE: " + response);  // Log the raw response

                if (response == null || response.trim().isEmpty()) {
                    Log.e("Tag", "Empty response from the server");
                    Toast.makeText(add_admin_activity.this, "Server returned an empty response.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String message = jsonResponse.getString("message");
                    // Handle the response message
                    Toast.makeText(add_admin_activity.this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON parsing error
                    Toast.makeText(add_admin_activity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    Log.e("Tag", "Invalid JSON response: " + response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(add_admin_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailForAdmin_add_Admin_mit.getText().toString().trim());
                params.put("password", passwordForAdmin_add_Admin_mit.getText().toString().trim());
                params.put("name", fullNameForAdmin_add_Admin_mit.getText().toString().trim());
                return params;
            }
        };

        queue.add(request);
    }



    public void setUpValues(){
        fullNameForAdmin_add_Admin_mit = findViewById(R.id.fullNameForAdmin_add_Admin_mit);
        emailForAdmin_add_Admin_mit = findViewById(R.id.emailForAdmin_add_Admin_mit);
        passwordForAdmin_add_Admin_mit = findViewById(R.id.passwordForAdmin_add_Admin_mit);
        confirmPassword_for_admin_add_mit = findViewById(R.id.confirmPassword_for_admin_add_mit);
        Add_admin_button_mit = findViewById(R.id.Add_admin_button_mit);
        buttom_back_admin_in_add_admin_mit = findViewById(R.id.buttom_back_admin_in_add_admin_mit);
    }

    private boolean isFullNameValid() {
        String fullNameAdmin = fullNameForAdmin_add_Admin_mit.getText().toString().trim();
        if (fullNameAdmin.isEmpty()) {
            fullNameForAdmin_add_Admin_mit.setError("Full Name is required");
            return false;
        }else if (Character.isDigit(fullNameAdmin.charAt(0))) {  // check if the full name start with number
            fullNameForAdmin_add_Admin_mit.setError("Company name cannot start with a number");
            fullNameForAdmin_add_Admin_mit.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isEmailValid() {
        String email = emailForAdmin_add_Admin_mit.getText().toString();
        if (email.isEmpty()) {
            emailForAdmin_add_Admin_mit.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailForAdmin_add_Admin_mit.setError("Invalid Email Address");
            return false;
        }
        return true;
    }
    private boolean isPasswordValid() {
        String password = passwordForAdmin_add_Admin_mit.getText().toString();
        if ((password.isEmpty())) {
            passwordForAdmin_add_Admin_mit.setError("Password is required");
            return false;
        }
        if(password.length() < 6){
            passwordForAdmin_add_Admin_mit.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean isConfirmPasswordValid() {
        String ConfirmPassword = confirmPassword_for_admin_add_mit.getText().toString();
        if (ConfirmPassword.isEmpty()) {
            confirmPassword_for_admin_add_mit.setError("Confirm Password is required");
            return false;
        }if(ConfirmPassword.length() < 6){
            confirmPassword_for_admin_add_mit.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean doPasswordsMatch() {
        String password = passwordForAdmin_add_Admin_mit.getText().toString();
        String confirmPassword = confirmPassword_for_admin_add_mit.getText().toString();
        if (!password.equals(confirmPassword)) {
            confirmPassword_for_admin_add_mit.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private boolean validateInput() {
        if (!isEmailValid()) return false;
        if (!isPasswordValid()) return false;
        if (!isConfirmPasswordValid()) return false;
        if (!doPasswordsMatch()) return false;
        if (!isFullNameValid()) return false;
        return true;
    }
}