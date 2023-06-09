package com.example.mowebs;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    // Deklarasi variabel
    TextView tvTime, tvValue, tvEditedSign;
    View view;
    LinearLayout linearLayoutMessage, linearLayoutValue, linearLayoutContainer;

    /**
     * {@summary} Deklarasi Constructor 
     * @param view
     */
    public ChatViewHolder(View view) {
        super(view);

        tvTime = view.findViewById(R.id.tv_time);
        tvValue = view.findViewById(R.id.tv_value);
        tvEditedSign = view.findViewById(R.id.tv_edited_sign);
        linearLayoutMessage = view.findViewById(R.id.linearLayout_message);
        linearLayoutValue = view.findViewById(R.id.linearLayout_Value);
        linearLayoutContainer = view.findViewById(R.id.linearLayout_Container);

        this.view = view;
    }

    // melakukan penambahan setting pada holder
    public void bind(ChatObject chatObject) {
        boolean  update = chatObject.getIsUpdating();

        // merubah layout jika chat berasal dari CUSTOMER
        if (chatObject.get_from() == ChatObject.CUSTOMER) {
            linearLayoutMessage.setGravity(Gravity.END);
            linearLayoutValue.setBackgroundResource(R.drawable.background_chat_coming_out);
            linearLayoutContainer.setGravity(Gravity.END);
            tvValue.setTextColor(ColorStateList.valueOf(Color.rgb(255,255,255)));
        }

        // menampilkan Tulisan Edited pada tampilan jika chat pernah diedit
        if (update) {
            tvEditedSign.setVisibility(view.VISIBLE);

        } else {
            tvEditedSign.setVisibility(view.GONE);

        }
    }

}
