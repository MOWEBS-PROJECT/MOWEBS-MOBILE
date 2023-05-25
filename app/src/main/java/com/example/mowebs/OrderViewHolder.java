package com.example.mowebs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    CardView cardViewContainer, cardViewStatusSign;
    ImageView imgMobil;
    TextView tvStatusSign, tvMerkMobil, tvOrderID, tvOrderTime, tvDuration, tvReturnTime, tvOrderTotal;

    public OrderViewHolder(View view) {
        super(view);
        cardViewStatusSign = view.findViewById(R.id.cardViewStatusSign);
        cardViewContainer  = view.findViewById(R.id.cardViewContainer);
        tvStatusSign    = view.findViewById(R.id.tvStatusSign);
        tvMerkMobil     = view.findViewById(R.id.tvMerkMobil);
        tvOrderID       = view.findViewById(R.id.tvOrderID);
        tvOrderTime     = view.findViewById(R.id.tvOrderTime);
        tvDuration      = view.findViewById(R.id.tvDuration);
        tvReturnTime    = view.findViewById(R.id.tvReturnTime);
        tvOrderTotal    = view.findViewById(R.id.tvOrderTotal);
        imgMobil        = view.findViewById(R.id.imgMobil);
    }

}
