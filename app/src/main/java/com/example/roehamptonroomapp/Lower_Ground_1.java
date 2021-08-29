package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Lower_Ground_1 extends AppCompatActivity {

    // creating impostors
    private Button btn_lg1_floor_map, btn_lg1_timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower__ground_1);

        //assign buttons
        btn_lg1_floor_map = findViewById(R.id.btn_lg1_floor_map);
        btn_lg1_timetable = findViewById(R.id.btn_lg1_timetable);

        // setting floor map button to navigate to Floor_Map activity using lambda expression
        btn_lg1_floor_map.setOnClickListener(v -> {
            startActivity(new Intent(Lower_Ground_1.this, Floor_Map.class));
        });

        // setting timetable button to navigate to TimeTable activity using lambda expression
        btn_lg1_timetable.setOnClickListener(v -> {
            startActivity(new Intent(Lower_Ground_1.this, TimeTable.class));
        });

    }
}