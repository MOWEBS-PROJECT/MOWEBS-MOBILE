package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExploreActivity extends AppCompatActivity {

    Button navHome, navSearch, navMessage, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_explore);

        navHome = findViewById(R.id.navigation_home);
        navSearch = findViewById(R.id.navigation_search);
        navMessage = findViewById(R.id.nagivation_message);
        navProfile = findViewById(R.id.nagivation_profile);

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        navMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}