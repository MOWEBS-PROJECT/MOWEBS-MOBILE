package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    // Deklarasi Variabel
    Button navHome, navSearch, navMessage, navProfile, btnSend, btnCancelUpdate;
    EditText inputMessage;
    RecyclerView recyclerViewMessage;
    ChatViewAdapter adapterChat;
    LinearLayout editMessageContainer;
    DBDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);

        dataSource = new DBDataSource(this);
        dataSource.open();

        // Inisialisasi variabel berdasarkan layout
        navHome = findViewById(R.id.navigation_home);
        navSearch = findViewById(R.id.navigation_search);
        navMessage = findViewById(R.id.nagivation_message);
        navProfile = findViewById(R.id.nagivation_profile);

        btnSend = findViewById(R.id.btn_send);
        btnCancelUpdate = findViewById(R.id.btnCancelUpdate);
        inputMessage = findViewById(R.id.edittext_chat);
        editMessageContainer = findViewById(R.id.editMessage_container);
        recyclerViewMessage = findViewById(R.id.recycleViewMessage);

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        navSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ExploreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Deklarasi dan inisialisasi variabel yang bertype ArrayList dengan value berbentuk ChatObject
        // Datanya diambil dari database dengan method
        ArrayList<ChatObject> chat = dataSource.getAllChat();
        // Inisialisasi adapter
        adapterChat = new ChatViewAdapter(chat, this, inputMessage, btnSend, editMessageContainer, recyclerViewMessage);
        // Set adapter dari RecyclerView menggunakan adapter 
        recyclerViewMessage.setAdapter(adapterChat);
        recyclerViewMessage.setLayoutManager( new LinearLayoutManager(ChatActivity.this));

        // Inisialisaso Onclicklistener dari tombol send
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = inputMessage.getText().toString();
                Date date = new Date();

                inputMessage.setText("");

                // Deklarasi object Chat dan set data
                ChatObject newMessage = new ChatObject();
                newMessage.setValue(value);
                newMessage.setDate("" + date.getHours() + "." + date.getMinutes());
                newMessage.set_from("CUSTOMER");
                
                // tambah ke array chat 
                chat.add(newMessage);
                // Memberitahukan adapter bahwa data ada yang dirubah
                adapterChat.notifyDataSetChanged();

                // Melakukan input data ke database
                dataSource.createChat(value, "CUSTOMER","" + date.getHours() + "." + date.getMinutes(), 0);
            }
        });

        // Inisialisasi Onclicklistener tombol cancelupdate
        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMessageContainer.setVisibility(View.GONE);
                // Inisialisasi Onclicklistener dari tombol send sehingga berfungsi sebagai create chat
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = inputMessage.getText().toString();
                        Date date = new Date();

                        inputMessage.setText("");

                        // Deklarasi object Chat dan set data
                        ChatObject newMessage = new ChatObject();
                        newMessage.setValue(value);
                        newMessage.setDate("" + date.getHours() + "." + date.getMinutes());
                        newMessage.set_from("CUSTOMER");
                        // tambah ke array chat 
                        chat.add(newMessage);
                        // Memberitahukan adapter bahwa data ada yang dirubah
                        adapterChat.notifyDataSetChanged();

                        // Melakukan input data ke database
                        dataSource.createChat(value, "CUSTOMER","" + date.getHours() + "." + date.getMinutes(), 0);
                    }
                });
            }
        });
    }

}