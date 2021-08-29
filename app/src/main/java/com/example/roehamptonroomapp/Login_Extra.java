package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Extra extends AppCompatActivity {

    // (et - editText, btn- Button, tV - textView, pBar - progressBar) in activity_login:
    // creating impostors:
    private EditText eT_login_extra_studentID;
    private Button btn_login_extra_back, btn_login_extra_login;
    private ProgressBar pBar_login_extra;

    // necessary variables
    private FirebaseUser user_login_extra;
    private DatabaseReference reference_login_extra;
    private String userID_login_extra, email_login_extra, studentID_login_extra, studentID_extra_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__extra);

        // Linking all impostors
        eT_login_extra_studentID = findViewById(R.id.eT_login_extra_studentID);
        btn_login_extra_back = findViewById(R.id.btn_login_extra_back);
        btn_login_extra_login = findViewById(R.id.btn_login_extra_login);
        final TextView tV_login_extra_email = (TextView) findViewById(R.id.tV_login_extra_email);
        pBar_login_extra = findViewById(R.id.pBar_login_extra);

        // Initialise necessary variables
        user_login_extra = FirebaseAuth.getInstance().getCurrentUser();
        reference_login_extra = FirebaseDatabase.getInstance().getReference("_user");
        userID_login_extra = user_login_extra.getUid();

        // Getting data from database
        reference_login_extra.child(userID_login_extra).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                // checking if userProfile is not empty
                if (userProfile != null)
                {

                    // saving value and inserting into welcome tV
                    email_login_extra = userProfile.getEmail();
                    studentID_extra_database = userProfile.getStudent_ID();
                    tV_login_extra_email.setText(email_login_extra);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if not having any data than just log out the user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Login_Extra.this, Login.class));
            }
        });

        btn_login_extra_back.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Login_Extra.this, Login.class));
        });

        btn_login_extra_login.setOnClickListener(v -> {

            // set the progressbar
            pBar_login_extra.setVisibility(View.VISIBLE);

            // get the new typed student ID
            studentID_login_extra = eT_login_extra_studentID.getText().toString().trim();

            // checking student ID's
            if (studentID_login_extra.equals(studentID_extra_database))
            {

                // connect to the app
                startActivity(new Intent(Login_Extra.this, Dashboard.class));

            }else{

                // go back to the login activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Login_Extra.this, Login.class));

            }

            // set the progressbar
            pBar_login_extra.setVisibility(View.GONE);
        });
    }
}