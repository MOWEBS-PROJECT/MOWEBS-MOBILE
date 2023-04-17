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
        ChatObject chatObject = chatList.get(position);

        try {
            holder.tvValue.setText(chatObject.getValue());
            holder.tvTime.setText(chatObject.getDate());
        } catch (Exception e) {
            Toast.makeText(context, "Error bind message", Toast.LENGTH_SHORT).show();
        }
        holder.bind(chatObject);

        if (chatObject.get_from() != ChatObject.CUSTOMER) return ;

        holder.linearLayoutMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showOptionMessage(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    public void showOptionMessage(int position) {
        Dialog optionMessage = new Dialog(context);
        optionMessage.setContentView(R.layout.dialog_option_message);

        Button btnUpdate = (Button) optionMessage.findViewById(R.id.btn_update);
        Button btnDelete = (Button) optionMessage.findViewById(R.id.btn_delete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMessage( position );
                optionMessage.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMessage(position);
                optionMessage.dismiss();
            }
        });
        optionMessage.show();
    }

    public void updateMessage(int position) {
        inputMessage.setText(chatList.get(position).getValue());
        editMessageContainer.setVisibility(View.VISIBLE);
        dataSource = new DBDataSource(context);
        dataSource.open();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatObject chatObject = chatList.get(position);
                String messageValue = inputMessage.getText().toString();
                inputMessage.setText("");
                chatObject.setValue(messageValue);
                chatObject.setIsUpdated(1);

                dataSource.updateChat(chatObject);
                notifyDataSetChanged();
            }
        });
    }

    public void deleteMessage(int position) {
        ChatObject chatObject = chatList.get(position);

        dataSource = new DBDataSource(context);
        dataSource.open();
        dataSource.deleteChat(chatObject.get_id());

        chatList.remove(position);
        notifyDataSetChanged();
    }



}
