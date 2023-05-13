package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static final String URLDETAILMOBIL = "https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/get_mobil_byid?id=";
    ImageView imageMobil;
    Button backButton;
    TextView jenisMobil, merkMobil, tvTransmission, tvAcceleration, tvSeat, tvColour, tvFuel;

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

//        imageMobil.setImageBitmap(Bitmap.);

        setDetailsMobil();
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
                } catch (Exception e) {
                    Toast.makeText(ProductDetailActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }

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