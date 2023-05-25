package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class PersonalDataActivity extends AppCompatActivity {

    EditText editTextFullname, editTextKTP, editTextEmail, editTextPhone, editTextAddress;
    Button updateButton, backButton;
    Spinner spinnerGender;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        editTextPhone   = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail   = findViewById(R.id.editTextEmail);
        editTextFullname= findViewById(R.id.editTextFullname);
        editTextKTP     = findViewById(R.id.editTextKTP);
        updateButton    = findViewById(R.id.updateButton);
        backButton      = findViewById(R.id.backButton);
        spinnerGender   = findViewById(R.id.spinnerGender);

        requestQueue = Volley.newRequestQueue(PersonalDataActivity.this);

        getPersonalData("64508e8e9198e0fc0a17588f");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePersonalData("64508e8e9198e0fc0a17588f");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void getPersonalData(String uid) {

        String path = "/getprofilebyuserid";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RequestDatabase.ENDPOINT + path + "?uid=" + uid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Toast.makeText(PersonalDataActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    editTextAddress.setText(response.getString("alamat"));
                    editTextKTP.setText(response.getString("no_ktp"));
                    editTextEmail.setText(response.getString("email"));
                    editTextFullname.setText(response.getString("nama"));
                    editTextPhone.setText(response.getString("no_hp"));
                    spinnerGender.setSelection(
                            (response.getString("jenis_kelamin") == "Male")? 1 : 0
                    );
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonalDataActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void updatePersonalData(String uid) {
        String path = "/update_profile";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nama",    editTextFullname.getText().toString());
            jsonBody.put("alamat",  editTextAddress.getText().toString());
            jsonBody.put("no_hp",   editTextPhone.getText().toString());
            jsonBody.put("email",   editTextEmail.getText().toString());
            jsonBody.put("jenis_kelamin",   spinnerGender.getSelectedItem().toString());
            jsonBody.put("no_ktp",  editTextKTP.getText().toString());
            String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path + "?uid=" + uid,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(PersonalDataActivity.this, "Success update", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PersonalDataActivity.this, "Fail to update", Toast.LENGTH_SHORT).show();
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

        } catch (JSONException e) {

        }
    }
}