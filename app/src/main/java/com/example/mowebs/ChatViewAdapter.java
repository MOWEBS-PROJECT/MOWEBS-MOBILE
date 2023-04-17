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
import java.util.Date;
import java.util.List;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    List<ChatObject> chatList = new ArrayList<>();
    EditText inputMessage;
    Context context;
    Button btnSend;
    LinearLayout editMessageContainer;
    RecyclerView recyclerViewMessage;

    DBDataSource dataSource;
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
        View view = LayoutInflater.from(context)
        .inflate(R.layout.chat_comingin, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        try {
            holder.tvValue.setText(chatList.get(position).getValue());
            holder.tvTime.setText(chatList.get(position).getDate());
        } catch (Exception e) {
            Toast.makeText(context, "Error bind message", Toast.LENGTH_SHORT).show();
        }

        ChatObject chatObject = chatList.get(position);
        holder.bind(chatObject);

        if (chatObject.getFrom() == "CUSTOMER") {
            holder.linearLayoutMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    updateMessage(holder);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    public void updateMessage(ChatViewHolder holder) {
        inputMessage.setText(chatList.get(holder.getAdapterPosition()).getValue());
        editMessageContainer.setVisibility(View.VISIBLE);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messageValue = inputMessage.getText().toString();
                inputMessage.setText("");
                chatList.get(holder.getAdapterPosition()).setValue(messageValue);
                chatList.get(holder.getAdapterPosition()).setIsUpdated(1);
                notifyDataSetChanged();
                try{
                    dataSource.updateChat(chatList.get(holder.getAdapterPosition()));
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        });
    }


}
