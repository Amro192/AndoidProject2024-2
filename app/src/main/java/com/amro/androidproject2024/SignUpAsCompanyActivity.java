package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class SignUpAsCompanyActivity extends AppCompatActivity {

    EditText companyName_edt_signAsCompany ;
    EditText emailForCompany_signUp ;
    EditText password_for_company_signUp ;
    EditText confirmPassword_for_company_signUp ;
    EditText phoneNumberForCompany;
    Spinner citySpinner;
   EditText companyCountry_edt_signAsCompany;
   Button Sign_up_as_company_button;
   Button loginBtn_login_as_company_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_as_company);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Sign_up_as_Company_activity__), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupViews();
        setupSpinner();

        Sign_up_as_company_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    setupAsCompany();
                    Toast.makeText(SignUpAsCompanyActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn_login_as_company_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAsCompanyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



    public void setupAsCompany() {
        String url = "http://10.0.2.2:80/androidPr/Sign_Up_As_Company.php";
        RequestQueue queue = Volley.newRequestQueue(SignUpAsCompanyActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.startsWith("<") && response.endsWith(">")) {
                    // The response contains HTML tags, handle it accordingly
                    Toast.makeText(SignUpAsCompanyActivity.this, "Server error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // Parse the response as JSON
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.getString("message");
                        int userID = jsonResponse.getInt("userID");

                        // Handle the response message
                        Toast.makeText(SignUpAsCompanyActivity.this, message, Toast.LENGTH_SHORT).show();

                        // Redirect or perform any action based on the response


                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                        Toast.makeText(SignUpAsCompanyActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpAsCompanyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailForCompany_signUp.getText().toString().trim());
                params.put("password", password_for_company_signUp.getText().toString().trim());
                params.put("companyName", companyName_edt_signAsCompany.getText().toString().trim());
                params.put("companyEmail", emailForCompany_signUp.getText().toString().trim());
                params.put("phone", phoneNumberForCompany.getText().toString().trim());
                params.put("address", companyCountry_edt_signAsCompany.getText().toString().trim() + ", " + citySpinner.getSelectedItem().toString());
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(request);
    }

    public void setupViews(){
        companyName_edt_signAsCompany =findViewById(R.id.companyName_edt_signAsCompany);
        emailForCompany_signUp = findViewById(R.id.emailForCompany_signUp);
        password_for_company_signUp = findViewById(R.id.password_for_company_signUp);
        confirmPassword_for_company_signUp = findViewById(R.id.confirmPassword_for_company_signUp);
        phoneNumberForCompany = findViewById(R.id.phoneNumberForCompany);
        citySpinner = findViewById(R.id.citySpinner);
        companyCountry_edt_signAsCompany = findViewById(R.id.companyCountry_edt_signAsCompany);
        Sign_up_as_company_button = findViewById(R.id.Sign_up_as_company_button);
        loginBtn_login_as_company_sign_up = findViewById(R.id.loginBtn_login_as_company_sign_up);
        companyCountry_edt_signAsCompany.setEnabled(false); // to make it read only
    }

    public void setupSpinner() {
        // Initialize spinner
        citySpinner = findViewById(R.id.citySpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citySpinner.setAdapter(adapter);
    }
    //{A simple explanation about the methods}
    // validate the company name ,email ,password and confirm password
    // first trim the string to get rid of any spaces
    // and check if it is empty or not , set error method display error massage
    // the request Focus method to focus on the field
    private boolean validateCompanyName() {
        String companyName = companyName_edt_signAsCompany.getText().toString().trim();
        if (companyName.isEmpty()) {
            companyName_edt_signAsCompany.setError("Company name is required");
            companyName_edt_signAsCompany.requestFocus();
            return false;
        } else if (Character.isDigit(companyName.charAt(0))) {  // check if the company name start with number
            companyName_edt_signAsCompany.setError("Company name cannot start with a number");
            companyName_edt_signAsCompany.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = emailForCompany_signUp.getText().toString().trim();
        if (email.isEmpty()) {
            emailForCompany_signUp.setError("Email is required");
            emailForCompany_signUp.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailForCompany_signUp.setError("Please enter a valid email");
            emailForCompany_signUp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = password_for_company_signUp.getText().toString();
        if (password.isEmpty()) {
            password_for_company_signUp.setError("Password is required");
            password_for_company_signUp.requestFocus();
            return false;
        } else if (password.length() < 6) {
            password_for_company_signUp.setError("Password must be at least 6 characters long");
            password_for_company_signUp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword() {
        String password = password_for_company_signUp.getText().toString();
        String confirmPassword = confirmPassword_for_company_signUp.getText().toString();
        if (confirmPassword.isEmpty()) {
            confirmPassword_for_company_signUp.setError("Confirm password is required");
            confirmPassword_for_company_signUp.requestFocus();
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPassword_for_company_signUp.setError("Passwords do not match");
            confirmPassword_for_company_signUp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneNumberForCompany.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            phoneNumberForCompany.setError("Phone number is required");
            phoneNumberForCompany.requestFocus();
            return false;
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            phoneNumberForCompany.setError("Please enter a valid phone number");
            phoneNumberForCompany.requestFocus();
            return false;
        } else if (phoneNumber.replaceAll("[^0-9]", "").length() != 10) { // check if the phone number is 10 digits
            phoneNumberForCompany.setError("Phone number must be exactly 10 digits");
            phoneNumberForCompany.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateCompanyCountry() {
        String companyCountry = companyCountry_edt_signAsCompany.getText().toString().trim();
        if (companyCountry.isEmpty()) {
            companyCountry_edt_signAsCompany.setError("Company country is required");
            return false;
        }else if (Character.isDigit(companyCountry.charAt(0))) {  // check if the company country start with number
            companyName_edt_signAsCompany.setError("Company country cannot start with a number");
            companyName_edt_signAsCompany.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateCitySpinner() {
        if (citySpinner.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) citySpinner.getSelectedView();
            if (errorText != null) {
                errorText.setError("Please select a city");
            }
            return false;
        }
        return true;
    }

    private boolean validateInput() {
        if (!validateCompanyName()) return false;
        if (!validateEmail()) return false;
        if (!validatePassword()) return false;
        if (!validateConfirmPassword()) return false;
        if (!validatePhoneNumber()) return false;
        if (!validateCompanyCountry()) return false;
        if (!validateCitySpinner()) return false;
        return true;
    }
}