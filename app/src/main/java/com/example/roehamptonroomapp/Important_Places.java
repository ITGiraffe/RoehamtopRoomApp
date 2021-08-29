package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Important_Places extends AppCompatActivity {

    // creating impostors
    private Button btn_important_student_services, btn_important_student_finance, btn_important_student_wellbeing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important__places);

        //assign buttons
        btn_important_student_services = findViewById(R.id.btn_important_student_services);
        btn_important_student_finance = findViewById(R.id.btn_important_student_finance);
        btn_important_student_wellbeing = findViewById(R.id.btn_important_student_wellbeing);

        // setting student services button to navigate to Ground_Floor activity using lambda expression
        btn_important_student_services.setOnClickListener(v -> {
            startActivity(new Intent(Important_Places.this, Ground_Floor.class));
        });

        // setting student finance button to navigate to First_Floor activity using lambda expression
        btn_important_student_finance.setOnClickListener(v -> {
            startActivity(new Intent(Important_Places.this, First_Floor.class));
        });

        // setting student wellbeing button to navigate to Second_Floor activity using lambda expression
        btn_important_student_wellbeing.setOnClickListener(v -> {
            startActivity(new Intent(Important_Places.this, Second_Floor.class));
        });
    }
}