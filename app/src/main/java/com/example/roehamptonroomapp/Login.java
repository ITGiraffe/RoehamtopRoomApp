package com.example.roehamptonroomapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    // (et - editText, btn- Button, tV - textView, pBar - progressBar) in activity_login:
    // creating impostors:
    private EditText eT_login_studentID, eT_login_password, eT_login_email;
    private Button btn_login_register, btn_login_login, btn_login_guest;
    private TextView tV_login_forgot_Pass;
    private ProgressBar pBar_login;

    // creating FirebaseAuth object and auth_listener object to log in
    private FirebaseAuth login_auth;

    // Necessary variables
    private DatabaseReference login_reference;
    private String studentID, email, student_password, login_userID, check_student_ID;
    private String login_error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connecting EditText, textViews and progressBar:
        eT_login_studentID = findViewById(R.id.eT_login_studentID);
        eT_login_email = findViewById(R.id.eT_login_email);
        eT_login_password = findViewById(R.id.eT_login_password);
        tV_login_forgot_Pass = findViewById(R.id.tV_login_forgot_Pass);
        pBar_login = findViewById(R.id.pBar_login);

        // connecting Buttons:
        btn_login_register = findViewById(R.id.btn_login_register);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_guest = findViewById(R.id.btn_login_guest);

        // setting up the login_auth object
        login_auth = FirebaseAuth.getInstance();


        // setting register button to navigate to register activity using lambda expression
        btn_login_register.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        // log in as guest and navigate to Dashboard activity using lambda expression
        btn_login_guest.setOnClickListener(v -> {

            pBar_login.setVisibility(View.VISIBLE);

            login_auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        // signing in anonymously
                        FirebaseUser user = login_auth.getCurrentUser();
                        startActivity(new Intent(Login.this, Dashboard.class));
                        Toast.makeText(Login.this, "Signed In anonymously", Toast.LENGTH_LONG).show();

                    }else{

                        // error message
                        Toast.makeText(Login.this, "Failed to connect, try again!", Toast.LENGTH_LONG).show();
                    }

                    pBar_login.setVisibility(View.GONE);
                }
            });

        });

        btn_login_login.setOnClickListener(v -> {

            // Validation
            try {

                // Take all inputs
                studentID = eT_login_studentID.getText().toString().trim();
                email = eT_login_email.getText().toString().trim();
                student_password = eT_login_password.getText().toString().trim();


                // check if student ID is empty
                if (studentID.isEmpty())
                {

                    login_error = "Please insert student ID!";
                    eT_login_studentID.setError(login_error);
                    eT_login_studentID.requestFocus();
                    throw new Exception(login_error);
                }

                // check if email is empty
                if (email.isEmpty())
                {
                    login_error = "Please insert your email!";
                    eT_login_email.setError(login_error);
                    eT_login_email.requestFocus();
                    throw new Exception(login_error);
                }

                // Email validation
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    login_error = "Please insert valid email!";
                    eT_login_email.setText("");
                    eT_login_email.setError(login_error);
                    eT_login_email.requestFocus();
                    throw new Exception(login_error);
                }

                // checking that password is filled
                if (student_password.isEmpty())
                {
                    login_error = "Please insert your password!";
                    eT_login_password.setText("");
                    eT_login_password.setError(login_error);
                    eT_login_password.requestFocus();
                    throw new Exception(login_error);
                }

                // checking that password has 6 characters at minimum
                if (student_password.length() < 6)
                {
                    login_error = "Your password should be 6 characters at minimum!";
                    eT_login_password.setText("");
                    eT_login_password.setError(login_error);
                    eT_login_password.requestFocus();
                    throw new Exception(login_error);
                }

                pBar_login.setVisibility(View.VISIBLE);

                // login method
                roehampton_login(studentID, email, student_password);

            } catch (Exception e){

                // Displaying error message
                Toast.makeText(Login.this, login_error, Toast.LENGTH_LONG).show();
            }
        });

        // setting forgot password using lambda
        tV_login_forgot_Pass.setOnClickListener(v -> {

            // go to forgot_pass activity
            startActivity(new Intent(Login.this, Forgot_Pass.class));
        });
    }

    // login method
    private void roehampton_login(String studentID, String email, String student_password)
    {
        login_auth.signInWithEmailAndPassword(email, student_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    // get logged in user
                    FirebaseUser new_login_user = FirebaseAuth.getInstance().getCurrentUser();

                    // getting user student ID
                    login_reference = FirebaseDatabase.getInstance().getReference("_user");
                    login_userID = new_login_user.getUid();

                    login_reference.child(login_userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {

                            User userProfile = snapshot.getValue(User.class);
                            // retrieving data from user
                            check_student_ID = userProfile.getStudent_ID();

                            // checking student ID
                            if (studentID.equals(check_student_ID))
                            {

                                if (new_login_user.isEmailVerified())
                                {
                                    // go to dashboard
                                    Toast.makeText(Login.this, "Signed In", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, Dashboard.class));

                                }else
                                {

                                    // send verification message and email
                                    new_login_user.sendEmailVerification();
                                    Toast.makeText(Login.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();

                                    // clear fields
                                    eT_login_email.setText("");
                                    eT_login_studentID.setText("");
                                    eT_login_password.setText("");
                                    eT_login_studentID.requestFocus();
                                }
                            }else
                            {
                                // wrong student ID
                                Toast.makeText(Login.this, "Login failed, wrong student ID! Try again!", Toast.LENGTH_LONG).show();

                                // go to special activity to fix errors
                                startActivity(new Intent(Login.this, Login_Extra.class));

                                // clear fields
                                eT_login_email.setText("");
                                eT_login_studentID.setText("");
                                eT_login_password.setText("");
                                eT_login_studentID.requestFocus();

                            }

                            // turn off the progress bar
                            pBar_login.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            // like no connection
                            Toast.makeText(Login.this, "Login failed! Try again!", Toast.LENGTH_LONG).show();

                            // clear fields
                            eT_login_email.setText("");
                            eT_login_studentID.setText("");
                            eT_login_password.setText("");
                            eT_login_studentID.requestFocus();

                            // turn off the progress bar
                            pBar_login.setVisibility(View.GONE);
                        }
                    });
                }else{

                    Toast.makeText(Login.this, "Login failed! Try again!", Toast.LENGTH_LONG).show();

                    // clear fields
                    eT_login_email.setText("");
                    eT_login_studentID.setText("");
                    eT_login_password.setText("");
                    eT_login_studentID.requestFocus();

                    // turn off the progress bar
                    pBar_login.setVisibility(View.GONE);
                }
            }
        });
    }
}