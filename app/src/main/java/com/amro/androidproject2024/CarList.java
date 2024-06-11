package com.amro.androidproject2024;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarList extends AppCompatActivity {
    private final List<Car_B> cars = new ArrayList<>();
    private RecyclerView recycler;

    private static final String BASE_URL = "http://10.0.2.2:80/androidPr/get_cars.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list);

        recycler = findViewById(R.id.car_recycler);


        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
    }

    private void loadItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);


                                String make = object.getString("make");
                                String model = object.getString("model");
                                String year = object.getString("year");
                                String price = object.getString("price");
                                String companyName = object.getString("companyName");
                                String image = object.getString("image");


                                Car_B car = new Car_B(make, model, year, price, companyName, image);
                                cars.add(car);
                            }

                        } catch (Exception e) {

                        }

                        Car_Rcycler_Adapter adapter = new Car_Rcycler_Adapter(CarList.this,
                                cars);
                        recycler.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(CarList.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(CarList.this).add(stringRequest);

    }
}