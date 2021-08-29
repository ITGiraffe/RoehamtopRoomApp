package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Fourth_Floor extends AppCompatActivity {

    // creating impostors
    private Button btn_f4_floor_map, btn_f4_timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth__floor);

        //assign buttons
        btn_f4_floor_map = findViewById(R.id.btn_f4_floor_map);
        btn_f4_timetable = findViewById(R.id.btn_f4_timetable);

        // setting floor map button to navigate to Floor_Map activity using lambda expression
        btn_f4_floor_map.setOnClickListener(v -> {
            startActivity(new Intent(Fourth_Floor.this, Floor_Map.class));
        });

        // setting timetable button to navigate to TimeTable activity using lambda expression
        btn_f4_timetable.setOnClickListener(v -> {
            startActivity(new Intent(Fourth_Floor.this, TimeTable.class));
        });
    }
}