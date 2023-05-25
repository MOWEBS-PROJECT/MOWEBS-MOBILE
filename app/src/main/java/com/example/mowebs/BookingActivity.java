package com.example.mowebs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    EditText editTextFullname, editTextAddress, editTextPhone, editTextDuration, editTextDate;
    TextView jenisMobil, merkMobil, tvDuration, tvPriceMobil, tvTaxMobil, tvTotalPrice;
    Button cancelButton, confirmButton;
    ImageView imgMobil;
    RequestQueue requestQueue;
    SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        confirmButton   = findViewById(R.id.confirmButton);
        cancelButton    = findViewById(R.id.cancelButton);
        editTextFullname= findViewById(R.id.editTextFullname);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextDuration= findViewById(R.id.editTextDuration);
        editTextPhone   = findViewById(R.id.editTextPhone);
        jenisMobil      = findViewById(R.id.jenisMobil);
        merkMobil       = findViewById(R.id.merkMobil);
        tvDuration      = findViewById(R.id.tvDuration);
        tvPriceMobil    = findViewById(R.id.tvPriceMobil);
        tvTaxMobil      = findViewById(R.id.tvTaxMobil);
        tvTotalPrice    = findViewById(R.id.tvTotalPrice);
        imgMobil        = findViewById(R.id.imgMobil);
        editTextDate    = findViewById(R.id.editTextDate);
        requestQueue = Volley.newRequestQueue(BookingActivity.this);

        // tempelkan fungsi klik untuk button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBooking("64508e8e9198e0fc0a17588f", getIntent().getStringExtra("idm"));
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year    = calendar.get(Calendar.YEAR);
                int month   = calendar.get(Calendar.MONTH);
                int day     = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                editTextDate.setText(i2 + "-" + (i1 + 1) + "-" + i);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        editTextDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().isEmpty()) {
                    tvTaxMobil.setText(
                            (Integer.parseInt(tvPriceMobil.getText().toString()) *
                                    Integer.parseInt(charSequence.toString()) * 11 / 100) + ""
                    );
                    tvTotalPrice.setText(
                            (Integer.parseInt(tvPriceMobil.getText().toString()) * Integer.parseInt(charSequence.toString())
                                    + Integer.parseInt(tvTaxMobil.getText().toString())) + ""
                    );
                    tvDuration.setText(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getPersonalData("64508e8e9198e0fc0a17588f");
        getMobilDetail(getIntent().getStringExtra("idm"));
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        editTextDate.setText(simpleDateFormat.format(new Date()));
    }

    private void getMobilDetail(String mid) {
        String path = "/get_mobil_byid";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?id=" + mid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    new DownloadImage(imgMobil)
                            .execute(response.getString("url_gambar"));
                    merkMobil.setText(response.getString("merk"));
                    jenisMobil.setText(response.getString("jenis"));
                    tvPriceMobil.setText(response.getString("harga"));
                    tvTaxMobil.setText(
                            (Integer.parseInt(response.getString("harga")) * 11 / 100) + ""
                    );
                    tvTotalPrice.setText(
                            (Integer.parseInt(response.getString("harga"))
                                    + Integer.parseInt(tvTaxMobil.getText().toString())) + ""
                    );


                } catch (JSONException e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this, "Failure Fetch Data", Toast.LENGTH_SHORT).show();
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
                    editTextAddress.setText(response.getString("alamat"));
                    editTextFullname.setText(response.getString("nama"));
                    editTextPhone.setText(response.getString("no_hp"));

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }



    private void createBooking(String uid, String IdMobil) {

        String path = "/insertsewa";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("durasi", Integer.parseInt(editTextDuration.getText().toString()));
            jsonBody.put("total_harga", Integer.parseInt(tvTotalPrice.getText().toString()));
            jsonBody.put("status", "DITERIMA");
            jsonBody.put("w_pemesanan", new Date().getTime());

            Date w_pengembalian = simpleDateFormat.parse(editTextDate.getText().toString());
            w_pengembalian.setDate(w_pengembalian.getDate() + Integer.parseInt(editTextDuration.getText().toString()));

            jsonBody.put("w_pengembalian", w_pengembalian.getTime());
            jsonBody.put("w_pembayaran", new Date().getTime() );
            String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path
                    + "?uid=" + uid + "&mid=" + IdMobil, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(BookingActivity.this, "Success Booking a Car", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BookingActivity.this, "Failed Booking a Car", Toast.LENGTH_SHORT).show();
                }
            }) {

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

        } catch (JSONException | ParseException e) {}

    }
}