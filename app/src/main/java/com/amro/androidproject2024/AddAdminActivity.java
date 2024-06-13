package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAdminActivity extends AppCompatActivity {

    private static final String TAG = AddAdminActivity.class.getName();

    private EditText fullnameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin_activty);
        setUpValues();

        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("user_name");
        }

    }

    public void addAdmin() {
        String url = "http://10.0.2.2:80/androidPr/AddAdmin.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(newRequestHandler(url));
    }

    @NonNull
    private StringRequest newRequestHandler(String url) {
        return new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.e(TAG, "RESPONSE: " + response);  // Log the raw response

                    if (response == null || response.trim().isEmpty()) {
                        Log.e(TAG, "Empty response from the server");
                        Toast.makeText(this, "Server returned an empty response.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    handleResponseMessage(response);
                }, error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailEditText.getText().toString().trim());
                params.put("password", passwordEditText.getText().toString().trim());
                params.put("name", fullnameEditText.getText().toString().trim());
                return params;
            }
        };
    }

    private void handleResponseMessage(String response) {
        try {
            // TODO:Handle the response message
            String message = new JSONObject(response).getString("message");
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            // TODO: Handle JSON parsing error
            Log.e(TAG, "Invalid JSON response: " + response);
            Toast.makeText(this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpValues() {
        fullnameEditText = findViewById(R.id.fullNameForAdmin_add_Admin_mit);
        emailEditText = findViewById(R.id.emailForAdmin_add_Admin_mit);
        passwordEditText = findViewById(R.id.passwordForAdmin_add_Admin_mit);
        confirmPasswordEditText = findViewById(R.id.confirmPassword_for_admin_add_mit);
    }

    private boolean isFullNameValid() {
        String fullNameAdmin = fullnameEditText.getText().toString().trim();
        if (fullNameAdmin.isEmpty()) {
            fullnameEditText.setError("Full Name is required");
            return false;
        } else if (Character.isDigit(fullNameAdmin.charAt(0))) {  // check if the full name start with number
            fullnameEditText.setError("Company name cannot start with a number");
            fullnameEditText.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isEmailValid() {
        String email = emailEditText.getText().toString();
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid Email Address");
            return false;
        }
        return true;
    }

    private boolean isPasswordValid() {
        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean isConfirmPasswordValid() {
        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Confirm Password is required");
            return false;
        }
        if (confirmPassword.length() < 6) {
            confirmPasswordEditText.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean doPasswordsMatch() {
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private boolean validateInput() {
        if (!isEmailValid()) return false;
        if (!isPasswordValid()) return false;
        if (!isConfirmPasswordValid()) return false;
        if (!doPasswordsMatch()) return false;
        return isFullNameValid();
    }

    public void onClickAddAdminButton(View ignored) {
        if (validateInput()) {
            addAdmin();
            Toast.makeText(this, "Admin added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickBackButton(View ignored) {
        startActivity(
                new Intent(this, AdminButtons.class)
                        .putExtra("user_name", userName)
        );
    }
}