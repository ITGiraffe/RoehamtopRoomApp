package com.example.roehamptonroomapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    // creating an arraylist for cards
    private ArrayList<Review> review_list;

    public ReviewAdapter(ArrayList<Review> review_list) {
        this.review_list = review_list;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // attachind reviewcard into recyclerView
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcard, parent, false);
        ReviewHolder holder = new ReviewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

        Review review = review_list.get(position);

        holder.tV_review_desc.setText(review_list.get(position).getReview_desc());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(review.getReview_timestamp())).getTime());

        holder.tV_review_date.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return review_list.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView tV_review_desc, tV_review_date;


        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            tV_review_desc = itemView.findViewById(R.id.tV_review_desc);
            tV_review_date = itemView.findViewById(R.id.tV_review_date);
        }
    }
}
