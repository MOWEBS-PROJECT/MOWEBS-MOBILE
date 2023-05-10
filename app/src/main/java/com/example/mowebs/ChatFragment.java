package com.example.mowebs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button navHome, navSearch, navMessage, navProfile, btnSend, btnCancelUpdate;
    EditText inputMessage;
    RecyclerView recyclerViewMessage;
    ChatViewAdapter adapterChat;
    LinearLayout editMessageContainer;
    DBDataSource dataSource;
    Context contextParent;

    public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(Context contextParent) {
        this.contextParent = contextParent;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        dataSource = new DBDataSource(contextParent);
        dataSource.open();

        btnSend = view.findViewById(R.id.btn_send);
        btnCancelUpdate = view.findViewById(R.id.btnCancelUpdate);
        inputMessage = view.findViewById(R.id.edittext_chat);
        editMessageContainer = view.findViewById(R.id.editMessage_container);
        recyclerViewMessage = view.findViewById(R.id.recycleViewMessage);

        // Deklarasi dan inisialisasi variabel yang bertype ArrayList dengan value berbentuk ChatObject
        // Datanya diambil dari database dengan method
        ArrayList<ChatObject> chat = dataSource.getAllChat();
        // Inisialisasi adapter
        adapterChat = new ChatViewAdapter(chat, contextParent, inputMessage, btnSend, editMessageContainer, recyclerViewMessage);
        // Set adapter dari RecyclerView menggunakan adapter
        recyclerViewMessage.setAdapter(adapterChat);
        recyclerViewMessage.setLayoutManager( new LinearLayoutManager(contextParent));

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

        return view;
    }
}