package com.example.mowebs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductCardViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private ArrayList<MobilObject> listMobil = new ArrayList<>();
    Context parentContext;

    public ProductCardViewAdapter(ArrayList<MobilObject> listMobil, Context parentContext) {
        this.listMobil      = listMobil;
        this.parentContext  = parentContext;
    }


    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentContext)
                .inflate(R.layout.product_view, parent, false);

        return new ProductCardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        MobilObject mobilObject = listMobil.get(position);
        holder.merkMobil.setText(mobilObject.getMerk());
        holder.hargaMobil.setText(mobilObject.getHarga());
        holder.jenisMobil.setText(mobilObject.getJenis());
        new DownloadImage(holder.imgMobil)
                .execute(mobilObject.getUrl_gambar());
        holder.containerMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("idm", mobilObject.getId());
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listMobil.size() == 0) return 0;
        return listMobil.size();
    }
}
