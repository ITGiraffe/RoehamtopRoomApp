package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Pass extends AppCompatActivity {

    // (et - editText, btn- Button, pBar - progressBar)
    // creating impostors:
    private EditText eT_forgot_email;
    private Button btn_forgot_reset;
    private ProgressBar pBar_forgot;
    private String email, forgot_error;

    // creating firebaseAuth object
    FirebaseAuth forgot_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass);

        // Linking impostors
        eT_forgot_email = findViewById(R.id.eT_forgot_email);
        btn_forgot_reset = findViewById(R.id.btn_forgot_reset);
        pBar_forgot = findViewById(R.id.pBar_forgot);

        // initialise forgot_auth object
        forgot_auth = FirebaseAuth.getInstance();

        // implement reset button
        btn_forgot_reset.setOnClickListener(v -> {

            // reset password method:
            resetpass();
        });
    }

    // reset password method
    private void resetpass() {

        // validation
        try {

            email = eT_forgot_email.getText().toString().trim();

            // checking that email is filled
            if (email.isEmpty())
            {
                forgot_error = "Please insert your email address!";
                eT_forgot_email.setError(forgot_error);
                eT_forgot_email.requestFocus();
                throw new Exception(forgot_error);
            }

            // Email validation

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                forgot_error = "Please provide valid email address!";
                eT_forgot_email.setText("");
                eT_forgot_email.setError(forgot_error);
                eT_forgot_email.requestFocus();
                throw new Exception(forgot_error);
            }

            // if all requirements are done set the progression bar:
            pBar_forgot.setVisibility(View.VISIBLE);

            // sending password using lambda
            forgot_auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                // check if successful
                if (task.isSuccessful())
                {
                    // show success message
                    Toast.makeText(Forgot_Pass.this, "Check your email to reset your password.", Toast.LENGTH_LONG).show();
                    // disable progression bar:
                    pBar_forgot.setVisibility(View.GONE);
                }else{
                    // transfer to login page
                    startActivity(new Intent(Forgot_Pass.this, Login.class));

                    // show failure message
                    Toast.makeText(Forgot_Pass.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                    eT_forgot_email.setText("");

                    // disable progression bar:
                    pBar_forgot.setVisibility(View.GONE);
                }
            });

        }catch (Exception e)
        {
            // Displaying error message
            Toast.makeText(Forgot_Pass.this, forgot_error, Toast.LENGTH_LONG).show();
        }


    }
}