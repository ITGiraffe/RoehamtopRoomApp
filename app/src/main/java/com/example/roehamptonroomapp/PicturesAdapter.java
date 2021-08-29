package com.example.roehamptonroomapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {

    // Initialize variable
    int[] images;

    // Create constructor
    public PicturesAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roehampton_pictures, parent, false);

        // Return view
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Set image on image view
        holder.imageView.setBackgroundResource(images[position]);

    }

    @Override
    public int getItemCount() {

        // Return array length
        return images.length;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Initialize variables
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Assign variables
            imageView = itemView.findViewById(R.id.iV_roehampton_pictures);
        }
    }
}