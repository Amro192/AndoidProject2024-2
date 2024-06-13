package com.amro.androidproject2024;

import android.os.Bundle;
import android.widget.Toast;

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

public class CarListForCustomer extends AppCompatActivity {

    private final List<Car> cars = new ArrayList<>();
    private RecyclerView recycler;
    private static final String USER_ALL_CARS_URL = "http://10.0.2.2:80/androidPr/get_all_cars.php";
    private String userAllCarsUrl;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        queue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.car_recycler);
        userAllCarsUrl = USER_ALL_CARS_URL;
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
    }

    private void loadItems() {
        cars.clear();
        StringRequest allCarsStringRequest = new StringRequest(Request.Method.GET, userAllCarsUrl,
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

                            Car car = new Car(make, model, year, price, image);
                            cars.add(car);
                        }
                    } catch (Exception ignored) {

                    }
                    CarRecyclerAdapterForCustomer adapter = new CarRecyclerAdapterForCustomer(CarListForCustomer.this, cars);
                    recycler.setAdapter(adapter);
                    Toast.makeText(CarListForCustomer.this, "Loaded" + cars.size(), Toast.LENGTH_LONG).show();

                }, error -> Toast.makeText(CarListForCustomer.this, error.toString(), Toast.LENGTH_LONG).show());

        queue.add(allCarsStringRequest);

    }
}