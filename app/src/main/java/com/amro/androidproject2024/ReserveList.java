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

public class ReserveList extends AppCompatActivity {
    private RecyclerView recycler;
    private final List<Reserve> reserves = new ArrayList<>();
    private static final String RENTAL_ALL_RESERVES_URL = "http://10.0.2.2:80/androidPr/get_reserv.php?RentalID=";
    private RequestQueue queue;
    String rentalReservesUrl;
    String rental_id;
    String id;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reserve_list);
        Intent intent = getIntent();

        queue = Volley.newRequestQueue(this);
        id = intent.getStringExtra("id");
        user_id = intent.getStringExtra("user_id");
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();

        rentalReservesUrl=RENTAL_ALL_RESERVES_URL+rental_id;


    }

    private void loadItems() {
        reserves.clear();
        StringRequest stringRequestReserve = new StringRequest(Request.Method.GET, rentalReservesUrl,
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
                            String startDateTime = object.getString("StartDateTime");
                            String endDateTime = object.getString("EndDateTime");

                            Reserve res = new Reserve(make, model, year, price, image, companyID,startDateTime,endDateTime);
                            reserves.add(res);
                        }

                    } catch (Exception ignored) {
                    //handel
                    }



                    ReserveRecyclerAdapter reserveAdapter = new ReserveRecyclerAdapter(ReserveList.this, reserves);
                    recycler.setAdapter(reserveAdapter);
                    Toast.makeText(ReserveList.this, "Loaded" + reserves.size(), Toast.LENGTH_LONG).show();

                }, error -> Toast.makeText(ReserveList.this, error.toString(), Toast.LENGTH_LONG).show());
        queue.add(stringRequestReserve);
    }
}