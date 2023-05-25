package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recyclerViewOrdersContent;
    Button backButton;
    OrderViewAdapter adapter;
    RequestQueue requestQueue;

    ArrayList<OrderObject> listOrder = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerViewOrdersContent = findViewById(R.id.recyclerViewOrdersContent);
        backButton                = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getOrders("64508e8e9198e0fc0a17588f");

        adapter = new OrderViewAdapter(OrderActivity.this, listOrder);
        recyclerViewOrdersContent.setAdapter(adapter);
        recyclerViewOrdersContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewOrdersContent.addItemDecoration(new SpaceItemDecorationRecyclerView(20,30));
    }

    private void getOrders(String uid) {
        String path = "/findsewa";
        requestQueue = Volley.newRequestQueue(OrderActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RequestDatabase.ENDPOINT + path + "?uid=" + uid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i< response.length(); i++) {
                        JSONObject orderJsonObject = response.getJSONObject(i);
                        OrderObject orderObject = new OrderObject();
                        orderObject.setStatus(orderJsonObject.getString("status"));
                        orderObject.setDurasi(orderJsonObject.getInt("durasi"));
                        orderObject.setId(orderJsonObject.getString("_id"));
                        orderObject.setTotalHarga(orderJsonObject.getInt("total_harga"));
                        orderObject.setId_mobil(orderJsonObject.getString("id_mobil"));
                        orderObject.setW_pengembalian(orderJsonObject.getLong("w_pengembalian"));
                        orderObject.setW_pemesanan(orderJsonObject.getLong("w_pemesanan"));
                        orderObject.setW_pembayaran(orderJsonObject.getLong("w_pembayaran"));

                        listOrder.add(orderObject);
                    }
                } catch (JSONException e){}

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}