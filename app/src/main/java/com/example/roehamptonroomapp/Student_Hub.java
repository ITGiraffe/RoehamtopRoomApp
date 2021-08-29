package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Student_Hub extends AppCompatActivity {

    // creating impostors
    private Button btn_student_hub_floor_map, btn_student_hub_timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__hub);

        //assign buttons
        btn_student_hub_floor_map = findViewById(R.id.btn_student_hub_floor_map);
        btn_student_hub_timetable = findViewById(R.id.btn_student_hub_timetable);

        // setting floor map button to navigate to Floor_Map activity using lambda expression
        btn_student_hub_floor_map.setOnClickListener(v -> {
            startActivity(new Intent(Student_Hub.this, Floor_Map.class));
        });

        // setting timetable button to navigate to TimeTable activity using lambda expression
        btn_student_hub_timetable.setOnClickListener(v -> {
            startActivity(new Intent(Student_Hub.this, TimeTable.class));
        });
    }
}