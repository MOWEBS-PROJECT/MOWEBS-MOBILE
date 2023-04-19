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
                showOptionMessage(position);
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
                deleteMessage(position);
                optionMessage.dismiss();
            }
        });
        optionMessage.show();
    }

    // update chat yang diedit
    public void updateMessage(int position) {
        dataSource = new DBDataSource(context);
        dataSource.open();

        inputMessage.setText(chatList.get(position).getValue());
        editMessageContainer.setVisibility(View.VISIBLE);
       
        // Set onclicklistener pada tombol send message
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatObject chatObject = chatList.get(position);
                String messageValue = inputMessage.getText().toString();

                // melakukan update ke database
                dataSource.updateChat(chatObject);

                // Melakukan update pada tampilan user 
                inputMessage.setText("");
                chatObject.setValue(messageValue);
                chatObject.setIsUpdated(1);
                notifyDataSetChanged();
            }
        });
    }

    // 
    public void deleteMessage(int position) {
        ChatObject chatObject = chatList.get(position);

        // Melakukan delete item pada database
        dataSource = new DBDataSource(context);
        dataSource.open();
        dataSource.deleteChat(chatObject.get_id());

        // Melakukan update pada tampilan user
        chatList.remove(position);
        notifyDataSetChanged();
    }



}
