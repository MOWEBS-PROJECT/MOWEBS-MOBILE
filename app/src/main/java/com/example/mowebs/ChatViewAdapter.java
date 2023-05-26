package com.example.mowebs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    // Deklarasi variabel
    List<ChatObject> chatList = new ArrayList<>();
    EditText inputMessage;
    Context context;
    Button btnSend;
    LinearLayout editMessageContainer;
    RecyclerView recyclerViewMessage;
    DBDataSource dataSource;
    RequestQueue requestQueue;

    /**
     * {@summary} Deklarasi Contructor 
     * @param chatList
     * @param context
     * @param inputMessage
     * @param btnSend
     * @param editMessageContainer
     * @param recyclerViewMessage
     */
    
    public ChatViewAdapter(List<ChatObject> chatList, Context context,
                           EditText inputMessage, Button btnSend,
                           LinearLayout editMessageContainer, RecyclerView recyclerViewMessage) {
        this.chatList = chatList;
        this.context = context;
        this.inputMessage = inputMessage;
        this.btnSend = btnSend;
        this.editMessageContainer = editMessageContainer;
        this.recyclerViewMessage = recyclerViewMessage;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Mengisi ViewHolder dengan layout Chat
        View view = LayoutInflater.from(context)
        .inflate(R.layout.chat_comingin, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        requestQueue = Volley.newRequestQueue(context);
        // Menangani data yang akan masuk kedalam RecyclerView
        ChatObject chatObject = chatList.get(position);

        // Set semua data kedalam ViewHolder 
        holder.tvValue.setText(chatObject.getValue());
        holder.tvTime.setText(chatObject.getDate());
        holder.bind(chatObject);

        if (chatObject.get_from() != ChatObject.CUSTOMER) return ;

        // Set onlongclicklistener pada sebuah item yang berinteraksi
        holder.linearLayoutMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showOptionMessage(holder.getAdapterPosition());
                return true;
            }
        });
    }

    // Mengembalikan ukuran dari chatList
    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    // menampilkan dialog tombol update dan delete
    public void showOptionMessage(int position) {
        Dialog optionMessage = new Dialog(context);
        optionMessage.setContentView(R.layout.dialog_option_message);

        Button btnUpdate = (Button) optionMessage.findViewById(R.id.btn_update);
        Button btnDelete = (Button) optionMessage.findViewById(R.id.btn_delete);

        // Set onclicklistener pada tombol update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMessage( position );
                optionMessage.dismiss();
            }
        });

        // Set onclicklistener pada tombol delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMessage(chatList.get(position).get_id(), position);
//                deleteMessage(position);
                optionMessage.dismiss();
            }
        });
        optionMessage.show();
    }

    // update chat yang diedit
    private void updateMessage(int position) {
//        dataSource = new DBDataSource(context);
//        dataSource.open();

        inputMessage.setText(chatList.get(position).getValue());
        editMessageContainer.setVisibility(View.VISIBLE);
       
        // Set onclicklistener pada tombol send message
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ChatObject chatObject = chatList.get(position);
                String messageValue = inputMessage.getText().toString();

                // melakukan update ke database
//                dataSource.updateChat(chatObject);

                // Melakukan update pada tampilan user 
                inputMessage.setText("");
//                chatObject.setValue(messageValue);
//                chatObject.setIsUpdating(true);
                updateMessage(messageValue, position);
                notifyDataSetChanged();
            }
        });
    }

    private void updateMessage( String messageValueUpdated, int position) {
        String path = "/updatechat";
        ChatObject chatObject = chatList.get(position);
        String id = chatObject.get_id();

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("value", messageValueUpdated);
            jsonBody.put("isupdate", true);
            String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path + "?id=" + id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            chatObject.setValue(messageValueUpdated);
                            chatObject.setIsUpdating(true);
                            notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
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

    // 
//    public void deleteMessage(int position) {
//        ChatObject chatObject = chatList.get(position);
//
//        // Melakukan delete item pada database
//        dataSource = new DBDataSource(context);
//        dataSource.open();
//        dataSource.deleteChat(chatObject.get_id());
//
//        // Melakukan update pada tampilan user
//        chatList.remove(position);
//        notifyDataSetChanged();
//    }

    private void deleteMessage(String id, int position) {
        String path = "/deletechat";

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestDatabase.ENDPOINT + path + "?id=" + id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            chatList.remove(position);
                            notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        } catch (Exception e) {}
    }

}
