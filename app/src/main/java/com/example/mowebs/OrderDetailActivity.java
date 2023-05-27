package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView tvFullname, tvPhone, tvAddress, tvBrandMobilCard,
            tvDurationCard, tvPriceMobilCard, tvOrderTotalCard, tvOrderID, tvOrderTime, tvDuration, tvReturnTime;
    private Button backButton, csButton, ratingButton, bookAgainButton;
    ImageView imgMobil;
    RequestQueue requestQueue;
    String idMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        tvFullname  = findViewById(R.id.tvFullname);
        tvPhone     = findViewById(R.id.tvPhone);
        tvAddress   = findViewById(R.id.tvAddress);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvReturnTime= findViewById(R.id.tvReturnTime);
        tvOrderID   = findViewById(R.id.tvOrderID);
        tvDuration  = findViewById(R.id.tvDuration);
        tvDurationCard = findViewById(R.id.tvDurationCard);
        tvOrderTotalCard = findViewById(R.id.tvOrderTotalCard);
        tvPriceMobilCard = findViewById(R.id.tvPriceMobilCard);
        tvBrandMobilCard = findViewById(R.id.tvBrandMobilCard);

        backButton  = findViewById(R.id.backButton);
        csButton    = findViewById(R.id.csButton);
        ratingButton= findViewById(R.id.ratingButton);
        bookAgainButton = findViewById(R.id.bookAgainButton);

        imgMobil = findViewById(R.id.imgMobil);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        csButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                intent.putExtra("openFragment", "CUSTOMER_SERVICE");
                startActivity(intent);
                finish();
            }
        });

        bookAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailActivity.this, BookingActivity.class);
                intent.putExtra("idm", idMobil);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(OrderDetailActivity.this);

        getPersonalDetails(Preference.getUserId(OrderDetailActivity.this));
        getOrderDetails(getIntent().getStringExtra("orderID"));
    }

    private void getOrderDetails(String orderID) {
        String path = "/findsewabyid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?sewaid=" + orderID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvOrderTotalCard.setText(response.getString("total_harga"));
                            tvDuration.setText(response.getString("durasi"));
                            tvDurationCard.setText(response.getString("durasi"));
                            tvOrderID.setText(response.getString("_id"));

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                            tvReturnTime.setText(
                                    simpleDateFormat.format(new Date(response.getLong("w_pengembalian")))
                            );
                            tvOrderTime.setText(
                                    simpleDateFormat.format(new Date(response.getLong("w_pemesanan")))
                            );


                            getMobilDetails(response.getString("id_mobil"));
                            idMobil = response.getString("id_mobil");

                        } catch (JSONException e){}


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void getPersonalDetails(String uID) {
        String path = "/getprofilebyuserid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?uid=" + uID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvFullname.setText(response.getString("nama"));
                            tvAddress.setText(response.getString("alamat"));
                            tvPhone.setText(response.getString("no_hp"));
                        } catch (JSONException e){}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderDetailActivity.this, "Failed to fetch Personal Data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }



    private void getMobilDetails(String mID) {
        String path = "/get_mobil_byid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?id=" + mID,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvPriceMobilCard.setText(response.getString("harga"));
                            tvBrandMobilCard.setText(response.getString("merk") + " " + response.getString("jenis"));
                            new DownloadImage(imgMobil).execute(response.getString("url_gambar"));
                        } catch (JSONException e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderDetailActivity.this, "Failed to fetch Car Details", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

}