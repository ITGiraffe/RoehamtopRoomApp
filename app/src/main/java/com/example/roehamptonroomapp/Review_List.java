package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review_List extends AppCompatActivity {

    // impostors, necessery list and object:
    private RecyclerView rv_recycler_review;
    private ArrayList<Review> list_review_list = new ArrayList<>();
    private DatabaseReference dbref_review_list;
    private ReviewAdapter adapter;
    private String book_PK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review__list);

        // set recycler view
        rv_recycler_review = findViewById(R.id.rv_recycler_review);
        rv_recycler_review.setLayoutManager(new LinearLayoutManager(Review_List.this));

        // get data from previous activity
        book_PK = getIntent().getStringExtra("PK");

        // set Databasereference object
        dbref_review_list = FirebaseDatabase.getInstance().getReference("_review");
        dbref_review_list.addListenerForSingleValueEvent(listener);

    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot dss: snapshot.getChildren()) {

                Review rev = dss.getValue(Review.class);

                if (rev.getBook_PK() != null && rev.getBook_PK().equals(book_PK)) {
                    list_review_list.add(rev);
                }
            }

            adapter = new ReviewAdapter(list_review_list);
            rv_recycler_review.setAdapter(adapter);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}