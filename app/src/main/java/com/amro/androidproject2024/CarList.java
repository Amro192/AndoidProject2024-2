package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarList extends AppCompatActivity {
    private final List<Car_B> cars = new ArrayList<>();
    private RecyclerView recycler;

    private static final String BASE_URL = "http://10.0.2.2:80/androidPr/get_cars.php?CompanyID=";
    String id;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list);

        recycler = findViewById(R.id.car_recycler);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        url = BASE_URL + id;


        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
    }

    private void loadItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {

                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);


                            String make = object.getString("Make");
                            String model = object.getString("Model");
                            String year = object.getString("Year");
                            String price = object.getString("RentPricePerDay");
                            String image = object.getString("Image");
                            String companyID = object.getString("CompanyID");


                            Car_B car = new Car_B(make, model, year, price, image, companyID);
                            cars.add(car);
                        }

                    } catch (Exception ignored) {

                    }

                    Car_Rcycler_Adapter adapter = new Car_Rcycler_Adapter(CarList.this, cars);
                    recycler.setAdapter(adapter);
                    Toast.makeText(CarList.this, "Loaded" + cars.size(), Toast.LENGTH_LONG).show();

                }, error -> Toast.makeText(CarList.this, error.toString(), Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(CarList.this).add(stringRequest);

    }
}