package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Lower_Ground_2 extends AppCompatActivity {

    private Button btn_lg2_floor_map, btn_lg2_timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower__ground_2);

        //assign buttons
        btn_lg2_floor_map = findViewById(R.id.btn_lg2_floor_map);
        btn_lg2_timetable = findViewById(R.id.btn_lg2_timetable);

        // setting floor map button to navigate to Floor_Map activity using lambda expression
        btn_lg2_floor_map.setOnClickListener(v -> {
            startActivity(new Intent(Lower_Ground_2.this, Floor_Map.class));
        });

        // setting timetable button to navigate to TimeTable activity using lambda expression
        btn_lg2_timetable.setOnClickListener(v -> {
            startActivity(new Intent(Lower_Ground_2.this, TimeTable.class));
        });

    }
}