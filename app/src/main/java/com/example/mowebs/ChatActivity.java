package com.example.mowebs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<ChatObject> chat = new ArrayList<>();
        chat.add(new ChatObject("Hello", "8.10", "CUSTOMER"));
        chat.add(new ChatObject("Hello mr/mrs, good morning what can we do for you?", "8.13", "CS"));
        chat.add(new ChatObject("Can i get some information about Rules for late returns?", "10.10", "CUSTOMER"));
        chat.add(new ChatObject("Yes, for a delay of 12 hours will be fined $50, and for further delays will be charged $30 per 12 hours", "10.18", "CS"));

    }
}