package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Main_Campus extends AppCompatActivity {

    // creating impostors
    private Button btn_main_campus_lg2, btn_main_campus_lg1, btn_main_campus_gf,
            btn_main_campus_1floor, btn_main_campus_2floor, btn_main_campus_3floor, btn_main_campus_4floor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__campus);

        // assign buttons
        btn_main_campus_lg2 = findViewById(R.id.btn_main_campus_lg2);
        btn_main_campus_lg1 = findViewById(R.id.btn_main_campus_lg1);
        btn_main_campus_gf = findViewById(R.id.btn_main_campus_gf);
        btn_main_campus_1floor = findViewById(R.id.btn_main_campus_1floor);
        btn_main_campus_2floor = findViewById(R.id.btn_main_campus_2floor);
        btn_main_campus_3floor = findViewById(R.id.btn_main_campus_3floor);
        btn_main_campus_4floor = findViewById(R.id.btn_main_campus_4floor);

        // setting lower ground 2 button to navigate to Lower_Ground_2 activity using lambda expression
        btn_main_campus_lg2.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Lower_Ground_2.class));
        });

        // setting lower ground 1 button to navigate to Lower_Ground_1 activity using lambda expression
        btn_main_campus_lg1.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Lower_Ground_1.class));
        });

        // setting ground floor button to navigate to Ground_Floor activity using lambda expression
        btn_main_campus_gf.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Ground_Floor.class));
        });

        // setting first floor button to navigate to First_Floor activity using lambda expression
        btn_main_campus_1floor.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, First_Floor.class));
        });

        // setting second floor button to navigate to Second_Floor activity using lambda expression
        btn_main_campus_2floor.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Second_Floor.class));
        });

        // setting third floor button to navigate to Third_Floor activity using lambda expression
        btn_main_campus_3floor.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Third_Floor.class));
        });

        // setting fourth floor button to navigate to Fourt_Floor activity using lambda expression
        btn_main_campus_4floor.setOnClickListener(v -> {
            startActivity(new Intent(Main_Campus.this, Fourth_Floor.class));
        });
    }
}