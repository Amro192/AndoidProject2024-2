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

public class ReserveRecyclerAdapter extends RecyclerView.Adapter<ReserveRecyclerAdapter.ViewHolderr>{

    private final List<Reserve> reserveList;
    private final Context context; // Add context field
    public ReserveRecyclerAdapter(Context context, List<Reserve> reserveList) {
        this.context = context;
        this.reserveList = reserveList;
    }
    @NonNull
    @Override
    public ReserveRecyclerAdapter.ViewHolderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reserv, parent, false);
        return new ReserveRecyclerAdapter.ViewHolderr(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveRecyclerAdapter.ViewHolderr holder, int position) {
        final Reserve res = reserveList.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.imageCar_Reserve);
        // Use Glide to load image
        Glide.with(context).load(res.getImage()).into(imageView);

        TextView txtMake = cardView.findViewById(R.id.carMake_Reserve);
        txtMake.setText(res.getMake());
        TextView txtModel = cardView.findViewById(R.id.carModel_Reserve);
        txtModel.setText(res.getModel());
        TextView txtYear = cardView.findViewById(R.id.carYear_Reserve);
        txtYear.setText(res.getYear());
        TextView txtPrice = cardView.findViewById(R.id.carPrice_Reserve);
        txtPrice.setText(res.getPrice());
        TextView txtCompany = cardView.findViewById(R.id.companyName_edt_signAsCompany);
        txtCompany.setText(res.getCompanyName());
        TextView txtStartTime = cardView.findViewById(R.id.startDates_Reserve);
        txtStartTime.setText(res.getStartDateTime());
        TextView txtEndTime = cardView.findViewById(R.id.endDates_Reserve);
        txtEndTime.setText(res.getEndDateTime());
        cardView.setOnClickListener(v -> {
            // Handle click event
        });
    }

    @Override
    public int getItemCount() {
        return reserveList.size();
    }

    public class ViewHolderr extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolderr(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}








