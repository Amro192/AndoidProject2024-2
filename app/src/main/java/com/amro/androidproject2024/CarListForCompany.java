package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarListForCompany extends AppCompatActivity {
    private final List<Car> cars = new ArrayList<>();
    private RecyclerView recycler;
    private static final String BASE_URL_COMPANY = "http://10.0.2.2:80/androidPr/get_cars.php?CompanyID=";
    private static final String USER_ALL_CARS_URL = "http://10.0.2.2:80/androidPr/get_all_cars.php";
    String CompanyId;
    String flage;
    String companiesCarsUrl;
    String userAllCarsUrl;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list);
        queue = Volley.newRequestQueue(this);

        recycler = findViewById(R.id.car_recycler);
        Intent intent = getIntent();
        CompanyId = intent.getStringExtra("customerID");
        flage = intent.getStringExtra("flage");
        companiesCarsUrl = BASE_URL_COMPANY + CompanyId;
        userAllCarsUrl = USER_ALL_CARS_URL;

        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
    }

    private void loadItems() {
        cars.clear();
        if(flage.equals("YES")) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, companiesCarsUrl,
                    response -> {
                        try {
                            cars.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);


                                String make = object.getString("Make");
                                String model = object.getString("Model");
                                String year = object.getString("Year");
                                String price = object.getString("RentPricePerDay");
                                String image = object.getString("Image");
                                String companysID = object.getString("CompanyID");


                                Car car = new Car(make,model,year,price,image,companysID);
                                Toast.makeText(CarListForCompany.this, cars.size()+"", Toast.LENGTH_LONG).show();
                                cars.add(car);
                            }

                        } catch (Exception ignored) {

                            Toast.makeText(CarListForCompany.this,  ignored.getMessage(), Toast.LENGTH_LONG).show();

                        }

                        CarRecyclerAdapterForCompany adapterr = new CarRecyclerAdapterForCompany(CarListForCompany.this, cars);
                        recycler.setAdapter(adapterr);
                        Toast.makeText(CarListForCompany.this, "Loaded" + cars.size(), Toast.LENGTH_LONG).show();

                    }, error -> Toast.makeText(CarListForCompany.this, error.toString(), Toast.LENGTH_LONG).show());
            queue.add(stringRequest);
       }
        else if(flage.equals("NO")) {

            StringRequest allCarsStringRequest = new StringRequest(Request.Method.GET, userAllCarsUrl,
                    response -> {
                        try {
                            cars.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);


                                String make = object.getString("Make");
                                String model = object.getString("Model");
                                String year = object.getString("Year");
                                String price = object.getString("RentPricePerDay");
                                String image = object.getString("Image");
                                String CompanysID = object.getString("CompanyID");


                                Car car = new Car(make, model, year, price, image, CompanysID);
                                cars.add(car);
                            }

                        } catch (Exception ignored) {

                        }

                        CarRecyclerAdapterForCompany adapter = new CarRecyclerAdapterForCompany(CarListForCompany.this, cars);
                        recycler.setAdapter(adapter);
                        Toast.makeText(CarListForCompany.this, "Loaded" + cars.size(), Toast.LENGTH_LONG).show();

                    }, error -> Toast.makeText(CarListForCompany.this, error.toString(), Toast.LENGTH_LONG).show());
            queue.add(allCarsStringRequest);
        }



    }
}