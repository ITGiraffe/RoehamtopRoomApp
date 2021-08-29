package com.example.roehamptonroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Floor_Map extends AppCompatActivity {

    //creating impostors
    private  Button btn_google_map, btn_floor_map_main_campus, btn_floor_map_students_hub, btn_floor_map_important_places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor__map);

        // Assign buttons
        btn_google_map = findViewById(R.id.btn_floor_map_view_map);
        btn_floor_map_main_campus = findViewById(R.id.btn_floor_map_main_campus);
        btn_floor_map_students_hub = findViewById(R.id.btn_floor_map_students_hub);
        btn_floor_map_important_places = findViewById(R.id.btn_floor_map_important_places);


        // setting google_map button to navigate to Google_Map activity using lambda expression
        btn_google_map.setOnClickListener(v -> {
            startActivity(new Intent(Floor_Map.this, Google_Map.class));
        });

        // setting main campus button to navigate to Main_Campus activity using lambda expression
        btn_floor_map_main_campus.setOnClickListener(v -> {
            startActivity(new Intent(Floor_Map.this, Main_Campus.class));
        });

        // setting student hub button to navigate to Student_Hub activity using lambda expression
        btn_floor_map_students_hub.setOnClickListener(v -> {
            startActivity(new Intent(Floor_Map.this, Student_Hub.class));
        });

        // setting important places button to navigate to Important_Places activity using lambda expression
        btn_floor_map_important_places.setOnClickListener(v -> {
            startActivity(new Intent(Floor_Map.this, Important_Places.class));
        });

    }
}
