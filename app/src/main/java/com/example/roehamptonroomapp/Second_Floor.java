package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Second_Floor extends AppCompatActivity {

    // creating impostors
    private Button btn_f2_floor_map, btn_f2_timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__floor);

        //assign buttons
        btn_f2_floor_map = findViewById(R.id.btn_f2_floor_map);
        btn_f2_timetable = findViewById(R.id.btn_f2_timetable);

        // setting floor map button to navigate to Floor_Map activity using lambda expression
        btn_f2_floor_map.setOnClickListener(v -> {
            startActivity(new Intent(Second_Floor.this, Floor_Map.class));
        });

        // setting timetable button to navigate to TimeTable activity using lambda expression
        btn_f2_timetable.setOnClickListener(v -> {
            startActivity(new Intent(Second_Floor.this, TimeTable.class));
        });
    }
}