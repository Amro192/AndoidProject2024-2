package com.amro.androidproject2024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button loginButton;
    private Button signUpButton;

    public static EditText email;
    public EditText password;

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String FLAG = "FLAG";

    private boolean flag = false;

    private CheckBox checkBox;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Setup_control();
        setupSharedPrefs();
        checkPrefs();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpAsActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailst = email.getText().toString();
                String passwordst = password.getText().toString();

                if (checkBox.isChecked()) {
                    if (!flag) {
                        editor.putString(EMAIL, emailst);
                        editor.putString(PASSWORD, passwordst);
                        editor.putBoolean(FLAG, true);
                        editor.commit();
                    }

                }

                // do request to api and know any user is login to go to the there way
                loginSetUp(emailst, passwordst);
            }
        });
    }

    public void loginSetUp(String email, String password) { //127.0.0.1 //10.0.2.2
        String url = "http://10.0.2.2:80/androidPr/Login.php";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                                        Log.d("Tag", "Admin ID: " + idRole+ " User ID: " + user_id+ " Name: " + name);
                                        Toast.makeText(MainActivity.this, "Logged in as Admin. Admin ID: " + idRole, Toast.LENGTH_SHORT).show();

                                        Log.d("Tag", "Admin ID: " + idRole);
                                        // Redirect to admin activity
                                        // use the user id and admin id to do many things in admin page
                                        Intent intent = new Intent(MainActivity.this, test_bootunssss.class);
                                        intent.putExtra("user_id", user_id);
                                        intent.putExtra("user_name", name);
                                        intent.putExtra("admin_id", idRole);
                                        startActivity(intent);

                                        break;
                                    case "rental":
                                        Toast.makeText(MainActivity.this, "Logged in as Rental. Rental ID: " + idRole, Toast.LENGTH_SHORT).show();
                                        Intent rentalIntent = new Intent(MainActivity.this, CarList.class);
                                        rentalIntent.putExtra("name", name);
                                        rentalIntent.putExtra("id", idRole);
                                        startActivity(rentalIntent);
                                        break;  //admin@example.com admin
                                    case "company":
                                        Toast.makeText(MainActivity.this, "Logged in as Company. Company ID: " + idRole, Toast.LENGTH_SHORT).show();
                                        Log.d("Tag", "Company ID: " + idRole);
                                        Intent intent = new Intent(MainActivity.this, CarList.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("id", idRole);
                                        Log.d( "Tag", "Company ID: " + idRole);
                                        startActivity(intent);
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Tag", "Error: " + error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

// Add the request to the RequestQueue
        queue.add(request);
    }


    public void Setup_control() {
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if (flag) {
            String emaill = prefs.getString(EMAIL, "");
            String passwordd = prefs.getString(PASSWORD, "");
            email.setText(emaill);
            password.setText(passwordd);
            checkBox.setChecked(true);
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }
}