package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword;
    Button signupButton;
    TextView tvSignInText;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail    = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        signupButton     = findViewById(R.id.signupButton);
        tvSignInText     = findViewById(R.id.tvSignInText);
        requestQueue     = Volley.newRequestQueue(RegisterActivity.this);

        tvSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUsername.getText().toString().equals("")) {
                    editTextUsername.setError("Username is empty");
                    editTextUsername.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    return;
                }
                if (editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setError("Password is empty");
                    editTextPassword.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    return;
                }
                if (editTextEmail.getText().toString().equals("")) {
                    editTextEmail.setError("Email is empty");
                    editTextEmail.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    return;
                }

                signupButton.setEnabled(false);
                signUp();
            }
        });
    }

    private void signUp() {
        String path = "/register";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", editTextUsername.getText().toString());
            jsonBody.put("password", editTextPassword.getText().toString());
            jsonBody.put("email", editTextEmail.getText().toString());
            String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, "Success Register", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 409) {
                            editTextUsername.setError("Username already exist");
                            Toast.makeText(RegisterActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {}

    }
}