package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin_Company_Mange_Activity extends AppCompatActivity {

   private ListView listView_Company_Mange_Activity_admin_mit;

   private Button btn_delete_company_mit_admin;

   private Button button_back_admin_in_Company_mange;

   // private ArrayList<Company_admin_class> companyList;

   // private ArrayAdapter<Company_admin_class> adapter;
    private Company_admin_class selectedCompany;

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
        setupview();
        fetchCompanyData();
        button_back_admin_in_Company_mange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Company_Mange_Activity.this, test_bootunssss.class);

                startActivity(intent);
            }
        });
    }
    public void setupview(){
        listView_Company_Mange_Activity_admin_mit = findViewById(R.id.listView_Company_Mange_Activity_admin_mit);
        btn_delete_company_mit_admin = findViewById(R.id.btn_delete_company_mit_admin);
        button_back_admin_in_Company_mange = findViewById(R.id.button_back_admin_in_Company_mange);
    }

    private void fetchCompanyData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/androidPr/ReturnCompany.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Company_admin_class>  companyList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int companyID = jsonObject.getInt("CompanyID");
                                int userID = jsonObject.getInt("UserID");
                                String companyName = jsonObject.getString("CompanyName");
                                String email = jsonObject.getString("Email");
                                String phone = jsonObject.getString("Phone");
                                String address = jsonObject.getString("Address");
                                Log.e("Tag", "RESPONSE: " + companyID + " " + userID + " " + companyName +  " " + email + " " + phone+ " " + address);
                                // Create an instance of Company_admin_class and add it to companyList
                                Company_admin_class company = new Company_admin_class(companyID, userID, companyName, email, phone, address);
                                companyList.add(company);
                            }
                            ArrayAdapter<Company_admin_class>  adapter = new ArrayAdapter<>(
                                    Admin_Company_Mange_Activity.this,
                                    android.R.layout.simple_list_item_1,
                                    companyList);
                            listView_Company_Mange_Activity_admin_mit.setAdapter(adapter);
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


}