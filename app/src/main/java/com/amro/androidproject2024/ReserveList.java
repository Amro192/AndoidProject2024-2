package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ReserveList extends AppCompatActivity {
    private RecyclerView recycler;
    private final List<Reserve> reserves = new ArrayList<>();
    private static final String RENTAL_ALL_RESERVES_URL = "http://10.0.2.2:80/androidPr/get_reserv.php?RentalID=";
    private String rentalReservesUrl;
    private String rental_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);
        recycler = findViewById(R.id.reserved_by_user_recycler);
        Intent intent = getIntent();
        if (intent != null) {
            rental_id = intent.getStringExtra("customerID");
        }
        recycler.setLayoutManager(new LinearLayoutManager(this));
        rentalReservesUrl = RENTAL_ALL_RESERVES_URL + rental_id;
        loadItems();

    }

    private void loadItems() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        reserves.clear();
        StringRequest stringRequestReserve = new StringRequest(Request.Method.GET, rentalReservesUrl,
                this::handleJSONResponse,
                error -> Toast.makeText(ReserveList.this, error.toString(), Toast.LENGTH_LONG).show());
        requestQueue.add(stringRequestReserve);
    }

    private void handleJSONResponse(String response) {

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

                reserves.add(new Reserve(make, model, year, price, image, companyID, startDateTime, endDateTime));
            }

        } catch (Exception exception) {
            Log.d("JSONParseError", exception.toString());
        }

        recycler.setAdapter(new ReserveRecyclerAdapter(ReserveList.this, reserves));
        Toast.makeText(ReserveList.this, "Loaded: " + reserves.size(), Toast.LENGTH_LONG).show();
    }
}