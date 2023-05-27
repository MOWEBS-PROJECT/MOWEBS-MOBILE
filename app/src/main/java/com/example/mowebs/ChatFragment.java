package com.example.mowebs;

import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button btnSend, btnCancelUpdate;
    EditText inputMessage;
    RecyclerView recyclerViewMessage;
    ChatViewAdapter adapterChat;
    LinearLayout editMessageContainer;
    DBDataSource dataSource;
    Context contextParent;
    RequestQueue requestQueue;
    ArrayList<ChatObject> listChat = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH.mm");

    public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(Context contextParent) {
        this.contextParent = contextParent;
    }

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

//        dataSource = new DBDataSource(contextParent);
//        dataSource.open();

        btnSend = view.findViewById(R.id.btn_send);
        btnCancelUpdate = view.findViewById(R.id.btnCancelUpdate);
        inputMessage = view.findViewById(R.id.edittext_chat);
        editMessageContainer = view.findViewById(R.id.editMessage_container);
        recyclerViewMessage = view.findViewById(R.id.recycleViewMessage);

        requestQueue = Volley.newRequestQueue(contextParent);

        // Deklarasi dan inisialisasi variabel yang bertype ArrayList dengan value berbentuk ChatObject
        // Datanya diambil dari database dengan method
//        listChat = dataSource.getAllChat();
        readMessage(Preference.getUserId(contextParent));
        // Inisialisasi adapter
        adapterChat = new ChatViewAdapter(listChat, contextParent, inputMessage, btnSend, editMessageContainer, recyclerViewMessage);
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

                createMessage(Preference.getUserId(contextParent), value);

                // Melakukan input data ke database
//                dataSource.createChat(value, "CUSTOMER","" + date.getHours() + "." + date.getMinutes(), 0);
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
                        newMessage.setDate(simpleDateFormat.format(date));
                        newMessage.set_from("CUSTOMER");
                        // tambah ke array chat
                        listChat.add(newMessage);
                        // Memberitahukan adapter bahwa data ada yang dirubah
                        adapterChat.notifyDataSetChanged();

                        // Melakukan input data ke database
//                        dataSource.createChat(value, "CUSTOMER","" + date.getHours() + "." + date.getMinutes(), 0);
                    }
                });
            }
        });

        return view;
    }

    private void readMessage(String uID) {
        String path = "/getchats";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RequestDatabase.ENDPOINT + path + "?uid=" + uID,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0; i<response.length(); i++) {
                                JSONObject jsonMessage = response.getJSONObject(i);
                                ChatObject newMessage = new ChatObject();
                                newMessage.setValue(jsonMessage.getString("value"));
                                newMessage.set_from(jsonMessage.getString("from"));
                                newMessage.setIsUpdating(jsonMessage.getBoolean("isupdate"));
                                newMessage.set_id(jsonMessage.getString("_id"));

                                newMessage.setDate(simpleDateFormat.format(new Date(jsonMessage.getLong("date"))));
                                listChat.add(newMessage);
                            }
                        } catch (JSONException e) {}

                        adapterChat.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextParent, "Failed to fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void createMessage(String uID, String messageValue) {
        String path = "/insertchat";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("value", messageValue);
            jsonBody.put("from", "CUSTOMER");
            jsonBody.put("date", new Date().getTime());
            jsonBody.put("isupdate", false);

            String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path + "?uid=" + uID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Deklarasi object Chat dan set data
                            ChatObject newMessage = new ChatObject();
                            newMessage.setValue(messageValue);
                            newMessage.setDate(simpleDateFormat.format(new Date()));
                            newMessage.set_from("CUSTOMER");
                            newMessage.setIsUpdating(false);

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                newMessage.set_id(jsonResponse.getString("insertedId"));
                            } catch (JSONException e) {}

                            // tambah ke array chat
                            listChat.add(newMessage);
                            // Memberitahukan adapter bahwa data ada yang dirubah
                            adapterChat.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(contextParent, "Failed to send message", Toast.LENGTH_SHORT).show();
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