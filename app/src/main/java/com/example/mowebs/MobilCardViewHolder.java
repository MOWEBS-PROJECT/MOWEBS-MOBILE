package com.example.mowebs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MobilCardViewHolder extends RecyclerView.ViewHolder {

    ImageView imgMobil;
    LinearLayout containerMobil;
    TextView jenisMobil, merkMobil, hargaMobil;

    public MobilCardViewHolder(View view) {
        super(view);
        imgMobil        = view.findViewById(R.id.imgMobil);
        jenisMobil      = view.findViewById(R.id.jenisMobil);
        merkMobil       = view.findViewById(R.id.merkMobil);
        hargaMobil      = view.findViewById(R.id.hargaMobil);
        containerMobil  = view.findViewById(R.id.containerMobil);
    }

}
