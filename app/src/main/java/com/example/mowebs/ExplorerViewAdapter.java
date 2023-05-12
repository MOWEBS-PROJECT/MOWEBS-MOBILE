package com.example.mowebs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExplorerViewAdapter extends RecyclerView.Adapter<MobilCardViewHolder> {

    private ArrayList<MobilObject> listMobil = new ArrayList<>();
    Context parentContext;

    public ExplorerViewAdapter(ArrayList<MobilObject> listMobil, Context parentContext) {
        this.listMobil      = listMobil;
        this.parentContext  = parentContext;
    }


    @NonNull
    @Override
    public MobilCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentContext)
                .inflate(R.layout.product_view, parent, false);

        return new MobilCardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MobilCardViewHolder holder, int position) {
        MobilObject mobilObject = listMobil.get(position);
        holder.merkMobil.setText(mobilObject.getMerk());
        holder.hargaMobil.setText(mobilObject.getHarga());
        holder.jenisMobil.setText(mobilObject.getJenis());
        new DownloadImage(holder.imgMobil)
                .execute(mobilObject.getUrl_gambar());
    }


    @Override
    public int getItemCount() {
        if (listMobil.size() == 0) return 0;
        return listMobil.size();
    }
}
