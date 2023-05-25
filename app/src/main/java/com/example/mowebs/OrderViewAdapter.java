package com.example.mowebs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    ArrayList<OrderObject> listOrder = new ArrayList<>();
    RequestQueue requestQueue;
    Context parentContext;
    public OrderViewAdapter( Context parentContext, ArrayList<OrderObject> listOrder) {
        this.listOrder      = listOrder;
        this.parentContext  = parentContext;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_view, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        OrderObject order = listOrder.get(position);
        holder.tvOrderID.setText(order.getId());
        holder.tvOrderTotal.setText(order.getTotalHarga() + "");
        holder.tvOrderTime.setText(
                simpleDateFormat.format(
                        new Date(order.getW_pemesanan())
                )
        );
        holder.tvReturnTime.setText(
                simpleDateFormat.format(
                        new Date(order.getW_pengembalian())
                )
        );
        holder.tvDuration.setText(order.getDurasi() + "");
        int colorStatus = 4095;
        switch (order.getStatus()) {
            case "DITERIMA":
                colorStatus = R.color.receivedOrder;
                break;
            case "DIBATALKAN":
                colorStatus = R.color.cancelOrder;
                break;
            case "SELESAI":
                colorStatus = R.color.completeOrder;
                break;
        }
        holder.tvStatusSign.setText(order.getStatus());
        holder.cardViewStatusSign.setCardBackgroundColor(parentContext.getColor(colorStatus));

        getDetailsMobil(parentContext, order.getId_mobil(), holder.tvMerkMobil, holder.imgMobil);

        holder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentContext, OrderDetailActivity.class);
                intent.putExtra("orderID", order.getId());
                parentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }


    private void getDetailsMobil(Context parentContext, String IDmobil, TextView merkMobil, ImageView imgMobil) {

        String path = "/get_mobil_byid";
        MobilObject mobilObject = new MobilObject();
        JSONObject jsonMobil = null;
        requestQueue = Volley.newRequestQueue(parentContext);
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT + path + "?id=" + IDmobil, new JSONObject(), requestFuture, requestFuture);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?id=" + IDmobil, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    merkMobil.setText(
                            response.getString("merk") + " " + response.getString("jenis")
                    );
                    new DownloadImage(imgMobil).execute(response.getString("url_gambar"));
                } catch (Exception e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
