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

public class SignUpAsRentalActivity extends AppCompatActivity {

    private EditText fullNameAsRental_sign_up;
    private EditText email_As_Rental_Sign_up;

    private EditText password_As_Rental_Sign_up;

    private EditText confirm_password_As_Rental_Sign_up_editText;

    private EditText phone_number_As_Rental_Sign_up;

    private Button Sign_up_as_rental_button;

    private Button loginbtn_login_as_rental;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_as_rental);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Sign_up_as_rental_activity__), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetupViews();
        loginbtn_login_as_rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAsRentalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Sign_up_as_rental_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    // Proceed with the sign-up logic here
                    SignUpAsRental();
                    Toast.makeText(SignUpAsRentalActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    // redirect to activity as rental

                }
            }
        });
    }

    private void SignUpAsRental() {
        String url = "http://10.0.2.2:80/androidPr/Sing_up_As_Rental.php";
        RequestQueue queue = Volley.newRequestQueue(SignUpAsRentalActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.startsWith("<") && response.endsWith(">")) {
                    // The response contains HTML tags, handle it accordingly
                    Toast.makeText(SignUpAsRentalActivity.this, "Server error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // Parse the response as JSON
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.getString("message");
                        int userID = jsonResponse.getInt("userID");

                        // Handle the response message
                        Toast.makeText(SignUpAsRentalActivity.this, message, Toast.LENGTH_SHORT).show();

                        // Redirect or perform any action based on the response
//                        Intent intent = new Intent(SignUpAsRentalActivity.this, AnotherActivity.class);
//                        intent.putExtra("userID", userID);
//                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                        Toast.makeText(SignUpAsRentalActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpAsRentalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email_As_Rental_Sign_up.getText().toString().trim());
                params.put("password", password_As_Rental_Sign_up.getText().toString().trim());
                params.put("name", fullNameAsRental_sign_up.getText().toString().trim());
                params.put("phone", phone_number_As_Rental_Sign_up.getText().toString().trim());
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(request);
    }




    public void SetupViews(){
        fullNameAsRental_sign_up = findViewById(R.id.fullNameAsRental_sign_up);
        email_As_Rental_Sign_up= findViewById(R.id.email_As_Rental_Sign_up);
        password_As_Rental_Sign_up = findViewById(R.id.password_As_Rental_Sign_up);
        confirm_password_As_Rental_Sign_up_editText = findViewById(R.id.confirm_password_As_Rental_Sign_up_editText);
        phone_number_As_Rental_Sign_up = findViewById(R.id.phone_number_As_Rental_Sign_up);
        Sign_up_as_rental_button = findViewById(R.id.Sign_up_as_rental_button);
        loginbtn_login_as_rental = findViewById(R.id.loginbtn_login_as_rental);
    }
    //{A simple explanation about the methods}
    // validate the name ,email ,password and confirm password
    // first trim the string to get rid of any spaces
    // and check if it is empty or not , set error method display error massage
    // the request Focus method to focus on the field
    private boolean validateFullName() {
        String fullName = fullNameAsRental_sign_up.getText().toString().trim();
        if (fullName.isEmpty()) {
            fullNameAsRental_sign_up.setError("Full name is required");
            fullNameAsRental_sign_up.requestFocus();
            return false;
        } else if (Character.isDigit(fullName.charAt(0))) {  // check if the number start with number
            fullNameAsRental_sign_up.setError("Full name cannot start with a number");
            fullNameAsRental_sign_up.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateEmail() { // second if check if the email is valid format or not
                                      // patterns example ("user@gmail.com")
        String email = email_As_Rental_Sign_up.getText().toString().trim();
        if (email.isEmpty()) {
            email_As_Rental_Sign_up.setError("Email is required");
            email_As_Rental_Sign_up.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_As_Rental_Sign_up.setError("Please enter a valid email");
            email_As_Rental_Sign_up.requestFocus();
            return false;
        }
        return true;

    }

    private boolean validatePassword() { // second if check if the password must be at least 6 characters
        String password = password_As_Rental_Sign_up.getText().toString().trim();
        if (password.isEmpty()) {
            password_As_Rental_Sign_up.setError("Password is required");
            password_As_Rental_Sign_up.requestFocus();
            return false;
        } else if (password.length() < 6) {
            password_As_Rental_Sign_up.setError("Password must be at least 6 characters long");
            password_As_Rental_Sign_up.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword() { // in this method we check if the confirm password is the same as the password
        // and not empty
        String confirmPassword = confirm_password_As_Rental_Sign_up_editText.getText().toString().trim();
        String password = password_As_Rental_Sign_up.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            confirm_password_As_Rental_Sign_up_editText.setError("Confirm password is required");
            confirm_password_As_Rental_Sign_up_editText.requestFocus();
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirm_password_As_Rental_Sign_up_editText.setError("Passwords do not match");
            confirm_password_As_Rental_Sign_up_editText.requestFocus();
            return false;
        }
        return true;
    }
    // in this method we check if the phone number is valid and not empty
    // and check if it is a valid phone number patterns example("123-456-7890")phone_number_As_Rental_Sign_up
    private boolean validatePhoneNumber() {
        String phoneNumber = phone_number_As_Rental_Sign_up.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            phone_number_As_Rental_Sign_up.setError("Phone number is required");
            phone_number_As_Rental_Sign_up.requestFocus();
            return false;
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            phone_number_As_Rental_Sign_up.setError("Please enter a valid phone number");
            phone_number_As_Rental_Sign_up.requestFocus();
            return false;
        } else if (phoneNumber.replaceAll("[^0-9]", "").length() != 10) { // check if the phone number is 10 digits
            phone_number_As_Rental_Sign_up.setError("Phone number must be exactly 10 digits");
            phone_number_As_Rental_Sign_up.requestFocus();
            return false;
        }
        return true;
    }


    private boolean validateInput() { // in this method we check if all the fields are valid and not empty ,or not
        if (!validateFullName()) return false;
        if (!validateEmail()) return false;
        if (!validatePassword()) return false;
        if (!validateConfirmPassword()) return false;
        if (!validatePhoneNumber()) return false;
        return true;
    }

}