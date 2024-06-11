package com.amro.androidproject2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Car_Rcycler_Adapter extends RecyclerView.Adapter<Car_Rcycler_Adapter.ViewHolder> {
    private final Context context;
    private final List<Car_B> cars;

    @NonNull
    @Override
    public Car_Rcycler_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_car_list,
                parent,
                false);

        return new ViewHolder(v);
    }

    public Car_Rcycler_Adapter(Context context, List<Car_B> cars) {
        this.context = context;
        this.cars = cars;
    }

    @Override
    public void onBindViewHolder(@NonNull Car_Rcycler_Adapter.ViewHolder holder, int position) {
        final Car_B car = cars.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.imageCar);
//        Glide.with(context).load(car.getImage()).into(imageView);

        TextView txtMake = cardView.findViewById(R.id.txtMake);
        txtMake.setText(car.getMake());
        TextView txtModel = cardView.findViewById(R.id.txtModel);
        txtModel.setText(car.getModel());
        TextView txtYear = cardView.findViewById(R.id.txtYear);
        txtYear.setText(car.getYear());
        TextView txtPrice = cardView.findViewById(R.id.txtPrice);
        txtPrice.setText(car.getPrice());
        TextView txtCompany = cardView.findViewById(R.id.txtCompanyName);
        txtCompany.setText(car.getCompanyName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}