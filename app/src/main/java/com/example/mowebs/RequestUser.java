package com.example.mowebs;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestUser {

    private Context parentContext;
    private RequestQueue requestQueue;
    private String username = null;

    public RequestUser(Context parentContext) {
        this.parentContext = parentContext;
        this.requestQueue = Volley.newRequestQueue(parentContext);
    }

    public String getUsernameById(TextView tvUsername, String uid) {
        String path = "/findusernamebyid?uid=" + uid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String username = response.getString("username");
                    tvUsername.setText(username);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(parentContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
        return this.username;
    }


}
