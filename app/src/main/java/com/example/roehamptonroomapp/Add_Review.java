package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Add_Review extends AppCompatActivity {

    private DatabaseReference add_rev_reference;
    private EditText eT_add_desc;
    private Button btn_add_submit;
    private String book_PK;
    private ProgressDialog progres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__review);

        // linking
        btn_add_submit = findViewById(R.id.btn_add_submit);
        eT_add_desc = findViewById(R.id.eT_add_desc);
        add_rev_reference = FirebaseDatabase.getInstance().getReference("_review");
        progres = new ProgressDialog(this);

        // get data from previous activity
        book_PK = getIntent().getStringExtra("PK");

        btn_add_submit.setOnClickListener(v -> {

            startPosting();
        });

    }

    private void startPosting() {

        progres.setMessage("Adding the review...");
        progres.show();

        final String descRev = eT_add_desc.getText().toString().trim();
        final String timestamp = String.valueOf(java.lang.System.currentTimeMillis());

        if (descRev != null && !TextUtils.isEmpty(descRev))
        {
            // start uploading

        Review r = new Review(book_PK, descRev,timestamp);


//            // set collection to save data:
//            HashMap update = new HashMap();
//
//            update.put("book_PK", book_PK);
//            update.put("review_desc", descRev);
//            update.put("review_timestamp", String.valueOf(java.lang.System.currentTimeMillis()));

            add_rev_reference.child(add_rev_reference.push().getKey()).setValue(r).addOnSuccessListener(aVoid -> {

                progres.dismiss();
                Toast.makeText(Add_Review.this, "Review added", Toast.LENGTH_LONG).show();

                // go to book information with a book PK
                Intent i = new Intent(Add_Review.this, Book_Info.class);
                String key  = book_PK;
                i.putExtra("PK", key );
                startActivity(i);

            }).addOnFailureListener(e -> {

                Toast.makeText(Add_Review.this, "Connection lost!", Toast.LENGTH_LONG).show();
            });
        }
    }
}