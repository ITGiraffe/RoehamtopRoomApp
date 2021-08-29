package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Book_Info extends AppCompatActivity {

    // (iV - ImageView, tV - textView, btn - Button)
    // creating btn, tV and iV impostors:
    private ImageView iV_info_picture;
    private TextView tV_info_title, tV_info_author, tV_info_edition, tV_info_ISBN;
    private Button btn_info_view_reviews, btn_info_add_review;

    // necessary variables and objects
    private FirebaseUser info_user;
    private DatabaseReference reference_info;
    private String book_PK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__info);


        // linking impostors
        iV_info_picture = findViewById(R.id.iV_info_picture);
        tV_info_title = findViewById(R.id.tV_info_title);
        tV_info_author = findViewById(R.id.tV_info_author);
        tV_info_edition = findViewById(R.id.tV_info_edition);
        tV_info_ISBN = findViewById(R.id.tV_info_ISBN);
        btn_info_view_reviews = findViewById(R.id.btn_info_view_reviews);
        btn_info_add_review = findViewById(R.id.btn_info_add_review);

        // Initialise necessary variables / objects
        info_user = FirebaseAuth.getInstance().getCurrentUser();
        reference_info = FirebaseDatabase.getInstance().getReference("_book");

        // get data from previous activity
        book_PK = getIntent().getStringExtra("PK");

        reference_info.child(book_PK).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                _Book book_data = snapshot.getValue(_Book.class);

                Picasso.get().load(book_data.getBook_URL()).into(iV_info_picture);
                tV_info_title.setText(book_data.getBook_title());
                tV_info_author.setText(book_data.getBook_author());
                tV_info_edition.setText(book_data.getBook_edition());
                tV_info_ISBN.setText(book_data.getBook_ISBN());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // show error message
                Toast.makeText(Book_Info.this, "Failed to connect!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Book_Info.this, Dashboard.class));

            }
        });

        // set the check review button
        btn_info_view_reviews.setOnClickListener(v -> {

            // go to book information with a book PK
            Intent i = new Intent(Book_Info.this, Review_List.class);
            String key  = book_PK;
            i.putExtra("PK", key );
            startActivity(i);
        });

        // set the add review button
        btn_info_add_review.setOnClickListener(v -> {

            // go to book information with a book PK
            Intent i = new Intent(Book_Info.this, Add_Review.class);
            String key  = book_PK;
            i.putExtra("PK", key );
            startActivity(i);
        });

    }
}