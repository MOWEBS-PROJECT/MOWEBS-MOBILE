package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    Button navHome, navSearch, navMessage, navProfile, btnSend, btnCancelUpdate;
    EditText inputMessage;
    RecyclerView recyclerViewMessage;
    ChatViewAdapter adapterChat;
    LinearLayout editMessageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);

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
        customer.setIsUpdated(true);

        ChatObject cs = new ChatObject();
        cs.setValue("Hello mr/mrs, good morning what can we do for you?");
        cs.setDate("14-04-2023");
        cs.setId(1);
        cs.setFrom("CS");
        cs.setIsUpdated(true);

        chat.add(customer);
        chat.add(cs);

        adapterChat = new ChatViewAdapter(chat, getApplicationContext(), inputMessage, btnSend, editMessageContainer, recyclerViewMessage);
        recyclerViewMessage.setAdapter(adapterChat);
        recyclerViewMessage.setLayoutManager( new LinearLayoutManager(ChatActivity.this));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = inputMessage.getText().toString();
                Date date = new Date();

                inputMessage.setText("");

                ChatObject newMessage = new ChatObject();
                newMessage.setValue(value);
                newMessage.setDate("" + date.getHours() + "." + date.getMinutes());
                newMessage.setId(10);
                newMessage.setFrom("CUSTOMER");
                chat.add(newMessage);

                adapterChat.notifyDataSetChanged();
            }
        });

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMessageContainer.setVisibility(View.GONE);
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = inputMessage.getText().toString();
                        Date date = new Date();

                        inputMessage.setText("");

                        ChatObject newMessage = new ChatObject();
                        newMessage.setValue(value);
                        newMessage.setDate("" + date.getHours() + "." + date.getMinutes());
                        newMessage.setId(10);
                        newMessage.setFrom("CUSTOMER");
                        chat.add(newMessage);

                        adapterChat.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void showOpitonMessage() {
        Dialog optionMessage = new Dialog(this);
        optionMessage.setContentView(R.layout.dialog_option_message);
        Button btnUpdate = (Button) optionMessage.findViewById(R.id.btn_update);
        Button btnDelete = (Button) optionMessage.findViewById(R.id.btn_delete);

        Toast.makeText(this, "Udah kebuka", Toast.LENGTH_SHORT).show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updateMessage( holder );
                optionMessage.dismiss();
            }
        });
        optionMessage.show();
    }
}