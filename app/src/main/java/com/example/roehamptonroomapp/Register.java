package com.example.roehamptonroomapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    // (eT - editText, tV - textView, btn- Button, pBar - progressBar, cB - checkBox) in activity_register:
    // creating impostors:
    private TextView tV_register_conditions;
    private EditText eT_register_fn, eT_register_ln, eT_register_email, eT_register_ID, eT_register_pass, eT_register_confirm;
    private Button btn_register_register;
    private ProgressBar pBar_register;
    private CheckBox cB_register_terms;

    // Necessary variables:
    private String registration_error = "";
    private String first_name, last_name, studentID, email, password;

    // Necessary list
    private ArrayList<String> courses = new ArrayList<String>();

    // Declare FirebaseAuth instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Adding values into spinner dropdown - courses list
        courses.add(Course.Computing.toString()+ " "+Course.Technology.toString()+ " "+Course.Degree.toString());
        courses.add(Course.Business.toString()+ " "+Course.And.toString()+ " "+Course.Management.toString()+ " "+Course.Degree.toString());
        courses.add(Course.Architecture.toString()+ " "+Course.Degree.toString());
        courses.add(Course.Architecture.toString()+ " "+Course.Extended.toString()+ " "+Course.Degree.toString());
        courses.add(Course.Biochemistry.toString()+ " "+Course.Degree.toString());

        // linking EditText, checkBox and progressBar impostors:
        eT_register_fn = findViewById(R.id.eT_register_fn);
        eT_register_ln = findViewById(R.id.eT_register_ln);
        eT_register_ID = findViewById(R.id.eT_register_ID);
        eT_register_email = findViewById(R.id.eT_register_email);
        eT_register_pass = findViewById(R.id.eT_register_pass);
        eT_register_confirm = findViewById(R.id.eT_register_confirm);
        pBar_register = findViewById(R.id.pBar_register);
        cB_register_terms = findViewById(R.id.cB_register_terms);

        // linking TextView impostor
        tV_register_conditions = findViewById(R.id.tV_register_conditions);

        // Linking spinner for courses list
        Spinner spinner_course = (Spinner) findViewById(R.id.spinner_registration_course);

        // Creating an ArrayAdapter to get resources from courses list
        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, courses);

        // set dropdown in adapter and connect adapter with spinner
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_course.setAdapter(coursesAdapter);

        // Assigning Buttons impostors:
        btn_register_register = findViewById(R.id.btn_register_register);

        // Initialise mAuth
        mAuth = FirebaseAuth.getInstance();

        // setting terms and conditions link to navigate the user to the Roehampton agreements website
        tV_register_conditions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.roehampton.ac.uk/corporate-information/policies/"));
                startActivity(Getintent);
            }
        });

        // Setting up the register button
        btn_register_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                // Validation method:
                try {

                    // Take all the inputs
                    first_name = eT_register_fn.getText().toString().trim();
                    last_name = eT_register_ln.getText().toString().trim();
                    studentID = eT_register_ID.getText().toString().trim();
                    email = eT_register_email.getText().toString().trim();
                    password = eT_register_pass.getText().toString().trim();


                    // checking that first name is filled
                    if (first_name.isEmpty())
                    {
                        registration_error = "Please insert first name!";
                        eT_register_fn.setError(registration_error);
                        eT_register_fn.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking that last name is filled
                    if (last_name.isEmpty())
                    {
                        registration_error = "Please insert last name!";
                        eT_register_ln.setError(registration_error);
                        eT_register_ln.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking that student ID is filled
                    if (studentID.isEmpty())
                    {
                        registration_error = "Please insert student ID!";
                        eT_register_ID.setError(registration_error);
                        eT_register_ID.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking that email is filled
                    if (email.isEmpty())
                    {
                        registration_error = "Please insert your email address!";
                        eT_register_email.setError(registration_error);
                        eT_register_email.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // Email validation

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        registration_error = "Please provide valid email address!";
                        eT_register_email.setText("");
                        eT_register_email.setError(registration_error);
                        eT_register_email.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking that password is filled
                    if (password.isEmpty())
                    {
                        registration_error = "Please insert your password!";
                        eT_register_confirm.setText("");
                        eT_register_pass.setError(registration_error);
                        eT_register_pass.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking that password has 6 characters at minimum
                    if (password.length() < 6)
                    {
                        registration_error = "Your password should be 6 characters at minimum!";
                        eT_register_pass.setText("");
                        eT_register_confirm.setText("");
                        eT_register_pass.setError(registration_error);
                        eT_register_pass.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // checking passwords
                    if (!eT_register_pass.getText().toString().equals(eT_register_confirm.getText().toString()))
                    {
                        registration_error = "Password do not match!";
                        eT_register_pass.setText("");
                        eT_register_confirm.setText("");
                        eT_register_pass.setError(registration_error);
                        eT_register_pass.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // Check the terms and conditions
                    if (!cB_register_terms.isChecked())
                    {
                        registration_error = "Please accept the terms and conditions!";
                        cB_register_terms.setError(registration_error);
                        cB_register_terms.requestFocus();
                        throw new Exception(registration_error);
                    }

                    // start progression bar spin
                    pBar_register.setVisibility(View.VISIBLE);

                    // registering
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        // calling user class and its constructor:
                                        User user = new User(first_name, last_name, studentID,
                                                email, spinner_course.getSelectedItem().toString(),
                                                password, "", false);

                                        FirebaseDatabase.getInstance().getReference("_user")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful())
                                                {
                                                    // Displaying success message
                                                    Toast.makeText(Register.this, "Account registered!", Toast.LENGTH_LONG).show();

                                                    // no more progress bar
                                                    pBar_register.setVisibility(View.GONE);

                                                    startActivity(new Intent(Register.this, Login.class));

                                                }else{

                                                    // Displaying failure message
                                                    Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();

                                                    // clear all fields
                                                    eT_register_fn.setText("");
                                                    eT_register_ln.setText("");
                                                    eT_register_ID.setText("");
                                                    eT_register_email.setText("");
                                                    eT_register_pass.setText("");
                                                    eT_register_confirm.setText("");
                                                    cB_register_terms.setChecked(false);

                                                    // no more progress bar
                                                    pBar_register.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }else{

                                        // Displaying failure message
                                        Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();

                                        // clear all fields
                                        eT_register_fn.setText("");
                                        eT_register_ln.setText("");
                                        eT_register_ID.setText("");
                                        eT_register_email.setText("");
                                        eT_register_pass.setText("");
                                        eT_register_confirm.setText("");
                                        cB_register_terms.setChecked(false);

                                        // no more progress bar
                                        pBar_register.setVisibility(View.GONE);
                                    }

                                }
                            });
                }catch (Exception e)
                {
                    // Displaying error message
                    Toast.makeText(Register.this, registration_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}