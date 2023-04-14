package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    Button navHome, navSearch, navMessage, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);

        navHome = findViewById(R.id.navigation_home);
        navSearch = findViewById(R.id.navigation_search);
        navMessage = findViewById(R.id.nagivation_message);
        navProfile = findViewById(R.id.nagivation_profile);

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        navMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<ChatObject> chat = new ArrayList<>();

        ChatObject customer = new ChatObject();
        customer.setValue("Hello");
        customer.setDate("14-04-2023");
        customer.setId(1);
        customer.setFrom("CUSTOMER");

        ChatObject cs = new ChatObject();
        customer.setValue("Hello mr/mrs, good morning what can we do for you?");
        customer.setDate("14-04-2023");
        customer.setId(1);
        customer.setFrom("CS");

        chat.add(customer);
        chat.add(cs);

    }
}