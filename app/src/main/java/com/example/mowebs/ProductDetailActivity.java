package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static final String URLDETAILMOBIL = "https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/get_mobil_byid?id=";
    ImageView imageMobil;
    Button backButton;
    TextView jenisMobil, merkMobil, tvTransmission, tvAcceleration, tvSeat, tvColour, tvFuel, tvDescription;
    RecyclerView recyclerViewReviews;
    ReviewViewAdapter adapter;
    ArrayList<ReviewObject> listReviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageMobil = findViewById(R.id.imageMobil);
        backButton = findViewById(R.id.back_btn);
        jenisMobil = findViewById(R.id.jenisMobil);
        merkMobil = findViewById(R.id.tv_brand);
        tvTransmission = findViewById(R.id.tvTransmission);
        tvAcceleration = findViewById(R.id.tvAcceleration);
        tvSeat = findViewById(R.id.tvSeat);
        tvColour = findViewById(R.id.tvColour);
        tvFuel = findViewById(R.id.tvFuel);
        tvDescription = findViewById(R.id.tvDescription);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);

        setDetailsMobil();

        adapter = new ReviewViewAdapter(listReviews, ProductDetailActivity.this);
        recyclerViewReviews.setAdapter(adapter);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setDetailsMobil() {
        String idMobil = getIntent().getStringExtra("idm");
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URLDETAILMOBIL + idMobil, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject objSpesifikasiMobil = response.getJSONObject("spesifikasi");
                    jenisMobil.setText(response.getString("jenis"));
                    merkMobil.setText(response.getString("merk"));
                    tvTransmission.setText(objSpesifikasiMobil.getString("transmisi"));
                    tvAcceleration.setText(objSpesifikasiMobil.getString("akselerasi"));
                    tvSeat.setText(objSpesifikasiMobil.getString("kursi"));
                    tvColour.setText(objSpesifikasiMobil.getString("warna"));
                    tvFuel.setText(objSpesifikasiMobil.getString("fuel"));
                    new DownloadImage(imageMobil)
                            .execute(response.getString("url_gambar"));
                    tvDescription.setText(response.getString("deskripsi"));

                    JSONArray reviews = response.getJSONArray("reviews");
                    if (reviews != null) {
                        for (int i=0; i< reviews.length(); i++) {
                            ReviewObject review = new ReviewObject();
                            try {
                                JSONObject jsonReview = reviews.getJSONObject(i);
                                review.setUid(jsonReview.getString("id_user"));
                                review.setDate(jsonReview.getLong("date"));
                                review.setRate(jsonReview.getInt("rate"));
                                review.setComments(jsonReview.getString("comments"));
                                listReviews.add(review);
                            } catch (Exception e) {}

                        }
                    }

                } catch (Exception e) {

                }

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}