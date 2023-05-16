package com.example.mowebs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewViewAdapter  extends RecyclerView.Adapter<ReviewViewHolder> {

    ArrayList<ReviewObject> listReviews = new ArrayList<>();
    Context parentContext;
    public ReviewViewAdapter(ArrayList<ReviewObject> listReviews, Context parentContext) {
        this.listReviews    = listReviews;
        this.parentContext  = parentContext;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_view, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.tvCommentReview.setText(listReviews.get(position).getComments());
        holder.tvRate.setText(listReviews.get(position).getRate() + "");
            new RequestUser(parentContext)
                    .getUsernameById( holder.tvUserName, listReviews.get(position).getUid());
        Date reviewDate = new Date(listReviews.get(position).getDate());
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd MMMM yyyy");
        holder.tvDate.setText(  dateFormat.format(reviewDate)  );

    }

    @Override
    public int getItemCount() {
        if (listReviews.size() == 0) return 0;
        return listReviews.size();
    }
}

class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView tvUserName, tvDate, tvCommentReview, tvRate;
    public ReviewViewHolder(View view) {
        super(view);
        tvUserName  = view.findViewById(R.id.tvUserName);
        tvDate      = view.findViewById(R.id.tvDate);
        tvRate      = view.findViewById(R.id.tvRate);
        tvCommentReview = view.findViewById(R.id.tvCommentReview);
    }
}
