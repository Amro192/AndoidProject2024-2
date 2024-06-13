package com.amro.androidproject2024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amro.androidproject2024.admin_activities.AdminHomeScreen;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String FLAG = "FLAG";
    private Button loginButton;
    private Button signUpButton;
    private EditText email;
    private EditText password;
    private CheckBox checkBox;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setupSharedPrefs();
        checkPrefs();

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpAsActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();

            if (checkBox.isChecked()) {
                editor.putString(EMAIL, email);
                editor.putString(PASSWORD, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }
            loginSetUp(email, password);
        });
    }

    public void loginSetUp(String email, String password) { //127.0.0.1 //10.0.2.2
        String url = "http://10.0.2.2:80/androidPr/Login.php";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            // Handle successful response
            Log.e("Tag", "RESPONSE: " + response);
            try {
                JSONObject jsonResponse = new JSONObject(response);

                if (jsonResponse.has("error")) {
                    // Display the error message
                    Toast.makeText(MainActivity.this, jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } else if (jsonResponse.has("idRole") && jsonResponse.has("role")) {
                    String idRole = jsonResponse.getString("idRole");
                    String role = jsonResponse.getString("role");
                    String user_id = jsonResponse.getString("user_id");
                    String name = jsonResponse.getString("Name");


                    // Handle different roles
                    switch (role) {
                        case "admin":
                            Toast.makeText(MainActivity.this, "Logged in as Admin. Admin ID: " + idRole + " User ID: " + user_id, Toast.LENGTH_SHORT).show();
                            Log.d("Tag", "Admin ID: " + idRole + " User ID: " + user_id + " Name: " + name);
                            Toast.makeText(MainActivity.this, "Logged in as Admin. Admin ID: " + idRole, Toast.LENGTH_SHORT).show();
                            Log.d("Tag", "Admin ID: " + idRole);

                            // Redirect to admin activity
                            // use the user id and admin id to do many things in admin page
                            Intent adminIntent = new Intent(MainActivity.this, AdminHomeScreen.class);
                            adminIntent.putExtra("user_id", user_id);
                            adminIntent.putExtra("user_name", name);
                            adminIntent.putExtra("admin_id", idRole);
                            startActivity(adminIntent);
                            break;

                        case "rental":
                            Toast.makeText(MainActivity.this, "Logged in as Rental. Rental ID: " + idRole, Toast.LENGTH_SHORT).show();
                            Intent rentalIntent = new Intent(MainActivity.this, CustomerButtons.class);
                            rentalIntent.putExtra("name", name);
                            rentalIntent.putExtra("customerID", idRole);
                            startActivity(rentalIntent);
                            break;  //admin@example.com admin

                        case "company":
                            Toast.makeText(MainActivity.this, "Logged in as Company. Company ID: " + idRole, Toast.LENGTH_SHORT).show();
                            Log.d("Tag", "Company ID: " + idRole);
                            Intent intent2 = new Intent(MainActivity.this, CarListForCompany.class);
                            intent2.putExtra("name", name);
                            intent2.putExtra("id", idRole);
                            startActivity(intent2);
                            // Redirect to company activity
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "Logged in with unrecognized role: " + role, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Tag", "JSON Parsing error: " + e.getMessage());
                Toast.makeText(MainActivity.this, "Response parsing error", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            // Handle error
            Log.e("Tag", "Error: " + error.toString());
            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

// Add the request to the RequestQueue
        queue.add(request);
    }

    public void setUpViews() {
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
    }

    private void checkPrefs() {
        boolean flag = prefs.getBoolean(FLAG, false);
        if (flag) {
            String emailStringForPrefs = prefs.getString(EMAIL, "");
            String passwordStringForPrefs = prefs.getString(PASSWORD, "");
            email.setText(emailStringForPrefs);
            password.setText(passwordStringForPrefs);
            checkBox.setChecked(true);
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }
}