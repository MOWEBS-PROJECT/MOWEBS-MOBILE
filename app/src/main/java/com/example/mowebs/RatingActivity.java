package com.example.mowebs;

import static android.media.tv.TvContentRating.createRating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RatingActivity extends AppCompatActivity {
    
    TextView alamat, nama, merk, durasi, harga, total;
    EditText comment;
    RatingBar ratingbar;
    Button send, cancel;
    RequestQueue requestQueue;
    SimpleDateFormat date;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        nama = findViewById(R.id.id_NamaRating);
        alamat = findViewById(R.id.id_AlamatRating);
        merk = findViewById(R.id.id_MerkMobilRating);
        durasi = findViewById(R.id.id_DurasiRating);
        harga = findViewById(R.id.id_HargaRating);
        total = findViewById(R.id.id_TotalRating);
        cancel = findViewById(R.id.id_cancel);
        comment = findViewById(R.id.id_comment);
        send = findViewById(R.id.id_send);
        cancel = findViewById(R.id.id_cancel);
        ratingbar = findViewById(R.id.id_rating);

        requestQueue = Volley.newRequestQueue(RatingActivity.this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRating(Preference.getUserId(RatingActivity.this), getIntent().getStringExtra("idm"));
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getPersonalData(Preference.getUserId(RatingActivity.this));
        getMobilDetail(getIntent().getStringExtra("idm"));
        getSewa(Preference.getUserId(RatingActivity.this));
    }

    private void getSewa(String uid) {
        String path = "/findsewabyid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?id=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    durasi.setText(response.getString("durasi"));
                } catch (JSONException e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RatingActivity.this, "Failure Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getMobilDetail(String mid) {
        String path = "/get_mobil_byid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?id=" + mid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    merk.setText(response.getString("merk"));
                    harga.setText(response.getString("harga"));
                    total.setText(
                            (Integer.parseInt(response.getString("harga"))
                                    + Integer.parseInt(total.getText().toString())) + ""
                    );
                } catch (JSONException e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RatingActivity.this, "Failure Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getPersonalData(String uid) {
        String path = "/getprofilebyuserid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?uid=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    alamat.setText(response.getString("alamat"));
                    nama.setText(response.getString("nama"));
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RatingActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void createRating(String uid, String mid) {
        String path = "/insertreview";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("rate", (int) ratingbar.getRating());
            jsonBody.put("comments", comment.getText().toString());
            jsonBody.put("date", new Date().getTime());

            String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path
                    + "?uid=" + uid + "&mid=" + mid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RatingActivity.this, "Success Submit a Review", Toast.LENGTH_SHORT).show();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RatingActivity.this, "Failed Submit a Review", Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {

                    return requestBody == null? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }
            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {}
    }
}
