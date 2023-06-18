
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		sign_in
	 *	@date 		Saturday 18th of March 2023 02:21:57 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.mowebs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import java.util.HashMap;
import java.util.Map;

	public class SignInActivity extends Activity {

	EditText editTextUsername, editTextPassword;
	Button signinButton, signUpButton;
//	TextView tvSignUpText;
	RequestQueue requestQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		signUpButton = findViewById(R.id.signUpButton);
		signinButton = findViewById(R.id.signinButton);
		editTextUsername = findViewById(R.id.editTextUsername);
		editTextPassword = findViewById(R.id.editTextPassword);
		requestQueue	 = Volley.newRequestQueue(SignInActivity.this);

		signUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
				startActivity(intent);
				finish();

			}
		});

		signinButton.setOnClickListener(new View.OnClickListener() {
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
				signinButton.setEnabled(false);
				signIn();
			}
		});

	}


	private void signIn() {
		String path = "/login";

		try {
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("username", editTextUsername.getText().toString());
			jsonBody.put("password", editTextPassword.getText().toString());
			String requestBody = jsonBody.toString();
			StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							try {
								JSONObject jsonResponse = new JSONObject(response);
								String userID = jsonResponse.getString("_id");
								Preference.setUserId(SignInActivity.this, userID);
								Preference.setUsername( SignInActivity.this, editTextUsername.getText().toString());

								Intent intent = new Intent(SignInActivity.this, MainActivity.class);
								startActivity(intent);
								finish();

							} catch (JSONException e) {
								Toast.makeText(SignInActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
							}

						}
					}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (error.networkResponse.statusCode == 404) {
						editTextUsername.setError("Username is wrong");
						editTextUsername.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
						editTextPassword.setError("Password is wrong");
						editTextPassword.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
						Toast.makeText(SignInActivity.this, "Username and Password is wrong", Toast.LENGTH_SHORT).show();
						return;
					}
					Toast.makeText(SignInActivity.this, "Failed to authentication", Toast.LENGTH_SHORT).show();
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
	
	