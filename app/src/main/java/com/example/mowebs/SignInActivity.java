
	 
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
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

public class SignInActivity extends Activity {

	
	private View _bg__sign_in_ek2;
	private View rectangle_6;
	private TextView enter_your_username;
	private ImageView vector;
	private ImageView vector_ek1;
	private View rectangle_6_ek1;
	private TextView enter_your_password;
	private ImageView vector_ek2;
	private View rectangle_8;
	private TextView logo_ek1;
	private TextView welcome_;
	private TextView _20_01;
	private ImageView battery;
	private ImageView phone;
	private TextView sign_in_ek3;
	private TextView don_t_have_an_account__sign_up;
	private View rectangle_7;
	private TextView sign_in_ek4;
	private TextView forgot_password__;
	private TextView remember_me_ek1;
	private View rectangle_5;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		
		_bg__sign_in_ek2 = (View) findViewById(R.id._bg__sign_in_ek2);
		rectangle_6 = (View) findViewById(R.id.rectangle_6);
		enter_your_username = (TextView) findViewById(R.id.enter_your_username);
		vector = (ImageView) findViewById(R.id.vector);
		vector_ek1 = (ImageView) findViewById(R.id.vector_ek1);
		rectangle_6_ek1 = (View) findViewById(R.id.rectangle_6_ek1);
		enter_your_password = (TextView) findViewById(R.id.enter_your_password);
		vector_ek2 = (ImageView) findViewById(R.id.vector_ek2);
		rectangle_8 = (View) findViewById(R.id.rectangle_8);
		logo_ek1 = (TextView) findViewById(R.id.logo_ek1);
		welcome_ = (TextView) findViewById(R.id.welcome_);
		_20_01 = (TextView) findViewById(R.id._20_01);
		battery = (ImageView) findViewById(R.id.battery);
		phone = (ImageView) findViewById(R.id.phone);
		don_t_have_an_account__sign_up = (TextView) findViewById(R.id.don_t_have_an_account__sign_up);
		sign_in_ek4 = (TextView) findViewById(R.id.sign_in_ek4); //SignIn Button
		forgot_password__ = (TextView) findViewById(R.id.forgot_password__);
		remember_me_ek1 = (TextView) findViewById(R.id.remember_me_ek1);
		rectangle_5 = (View) findViewById(R.id.rectangle_5);
	
		
		//custom code goes here
		sign_in_ek4.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
				startActivity(intent);
			}
		});
	}
}
	
	