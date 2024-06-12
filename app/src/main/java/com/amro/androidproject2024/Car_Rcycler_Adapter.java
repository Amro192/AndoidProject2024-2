package com.amro.androidproject2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Car_Rcycler_Adapter extends RecyclerView.Adapter<Car_Rcycler_Adapter.ViewHolder> {
    private final List<Car_B> cars;
    private final Context context; // Add context field

    public Car_Rcycler_Adapter(Context context, List<Car_B> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_car, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Car_B car = cars.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.imageCar);
        // Use Glide to load image
        Glide.with(context).load(car.getImage()).into(imageView);

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

        cardView.setOnClickListener(v -> {
            // Handle click event
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