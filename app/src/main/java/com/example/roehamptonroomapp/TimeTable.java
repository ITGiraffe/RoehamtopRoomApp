package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeTable extends AppCompatActivity {

    // (tV - textView, btn - button)
    // creating impostors for tV, btn:
    private TextView tV_timeTable_date1, tV_timeTable_date2, tV_timeTable_date3, tV_timeTable_week,
            tV_timeTable_date4, tV_timeTable_date5, tV_timeTable_date6, tV_timeTable_date7, tV_timeTable_semester,
            tV_timeTable_MON1, tV_timeTable_MON2, tV_timeTable_MON3,
            tV_timeTable_TUE1, tV_timeTable_TUE2, tV_timeTable_TUE3,
            tV_timeTable_WED1, tV_timeTable_WED2, tV_timeTable_WED3,
            tV_timeTable_THU1, tV_timeTable_THU2, tV_timeTable_THU3,
            tV_timeTable_FRI1, tV_timeTable_FRI2, tV_timeTable_FRI3,
            tV_timeTable_SAT1, tV_timeTable_SAT2, tV_timeTable_SAT3,
            tV_timeTable_SUN1, tV_timeTable_SUN2, tV_timeTable_SUN3;
    private Button btn_timeTable_next_week, btn_timeTable_previous_week;


    // necessary variables and objects
    private Date timeTable_current_date;
    private int multiplayer = 0;
    private FirebaseUser timetable_user;
    private DatabaseReference timetable_reference, timetable_reference2;
    private String timetable_userID, user_course;
    private Calendar timeTable_calendar = Calendar.getInstance();

    // necessary list of modules
    private ArrayList<Modules> module = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // linking the impostors:
        tV_timeTable_date1 = findViewById(R.id.tV_timeTable_date1);
        tV_timeTable_date2 = findViewById(R.id.tV_timeTable_date2);
        tV_timeTable_date3 = findViewById(R.id.tV_timeTable_date3);
        tV_timeTable_date4 = findViewById(R.id.tV_timeTable_date4);
        tV_timeTable_date5 = findViewById(R.id.tV_timeTable_date5);
        tV_timeTable_date6 = findViewById(R.id.tV_timeTable_date6);
        tV_timeTable_date7 = findViewById(R.id.tV_timeTable_date7);
        tV_timeTable_week = findViewById(R.id.tV_timeTable_week);
        tV_timeTable_semester = findViewById(R.id.tV_timeTable_semester);
        tV_timeTable_MON1 = findViewById(R.id.tV_timeTable_MON1);
        tV_timeTable_MON2 = findViewById(R.id.tV_timeTable_MON2);
        tV_timeTable_MON3 = findViewById(R.id.tV_timeTable_MON3);
        tV_timeTable_TUE1 = findViewById(R.id.tV_timeTable_TUE1);
        tV_timeTable_TUE2 = findViewById(R.id.tV_timeTable_TUE2);
        tV_timeTable_TUE3 = findViewById(R.id.tV_timeTable_TUE3);
        tV_timeTable_WED1 = findViewById(R.id.tV_timeTable_WED1);
        tV_timeTable_WED2 = findViewById(R.id.tV_timeTable_WED2);
        tV_timeTable_WED3 = findViewById(R.id.tV_timeTable_WED3);
        tV_timeTable_THU1 = findViewById(R.id.tV_timeTable_TUE1);
        tV_timeTable_THU2 = findViewById(R.id.tV_timeTable_THU2);
        tV_timeTable_THU3 = findViewById(R.id.tV_timeTable_TUE3);
        tV_timeTable_FRI1 = findViewById(R.id.tV_timeTable_FRI1);
        tV_timeTable_FRI2 = findViewById(R.id.tV_timeTable_FRI2);
        tV_timeTable_FRI3 = findViewById(R.id.tV_timeTable_FRI3);
        tV_timeTable_SAT1 = findViewById(R.id.tV_timeTable_SAT1);
        tV_timeTable_SAT2 = findViewById(R.id.tV_timeTable_SAT2);
        tV_timeTable_SAT3 = findViewById(R.id.tV_timeTable_SAT3);
        tV_timeTable_SUN1 = findViewById(R.id.tV_timeTable_SUN1);
        tV_timeTable_SUN2 = findViewById(R.id.tV_timeTable_SUN2);
        tV_timeTable_SUN3 = findViewById(R.id.tV_timeTable_SUN3);
        btn_timeTable_next_week = findViewById(R.id.btn_timeTable_next_week);
        btn_timeTable_previous_week = findViewById(R.id.btn_timeTable_previous_week);

        // Initialise necessary variables / objects
        timetable_user = FirebaseAuth.getInstance().getCurrentUser();
        timetable_reference = FirebaseDatabase.getInstance().getReference("_user");
        timetable_userID = timetable_user.getUid();

        // Getting data from database
        timetable_reference.child(timetable_userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                // checking if userProfile is not empty
                if (userProfile != null) {

                    user_course = userProfile.getCourse_name();

                    if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) > 5 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 18)
                    {

                        if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 11) {

                            tV_timeTable_semester.setText("BREAK!");
                            // clear timetable
                            clear_timetable();

                        }else {

                            tV_timeTable_semester.setText("Semester 1");

                            // show modules
                            show_modules(user_course);

                        }

                    }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) >= 22 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 34)
                    {

                        if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 27)
                        {
                            tV_timeTable_semester.setText("BREAK!");
                            // clear timetable
                            clear_timetable();

                        }else{

                            tV_timeTable_semester.setText("Semester 2");

                            // show modules
                            show_modules2(user_course);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if not having any data than just log out the user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TimeTable.this, Login.class));
            }
        });

        // get current date:
        timeTable_current_date = Calendar.getInstance().getTime();

        //get semester:
        if (timeTable_current_date.getMonth() < 5)
        {
            tV_timeTable_semester.setText("Semester: 1");
        }else{
            tV_timeTable_semester.setText("Semester: 2");
        }

        // get week:
        timeTable_calendar.setMinimalDaysInFirstWeek(3);
        timeTable_calendar.setTime(timeTable_current_date);
        tV_timeTable_week.setText("Wk: " + timeTable_calendar.get(Calendar.WEEK_OF_YEAR));

        // spread dates for each day:
        timetable_date_spread(timeTable_calendar, multiplayer);

        // setting the next week button:
        btn_timeTable_next_week.setOnClickListener(v -> {

            // set multiplayer:
            multiplayer++;

            // call timetable_date_spred
            timetable_date_spread(timeTable_calendar, multiplayer);

            // retrieving month
            timeTable_calendar.setTime(timeTable_current_date);
            timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));

            //set semester:
            if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 5)
            {
                tV_timeTable_semester.setText("BREAK!");

                // clear timetable
                clear_timetable();

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 2)
                {
                    btn_timeTable_previous_week.setVisibility(View.VISIBLE);
                }

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) > 5 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 18)
            {

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 11) {

                    tV_timeTable_semester.setText("BREAK!");
                    // clear timetable
                    clear_timetable();

                }else {

                    tV_timeTable_semester.setText("Semester 1");

                    // show modules
                    show_modules(user_course);

                }

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) >= 18 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 22)
            {
                tV_timeTable_semester.setText("BREAK!");
                // clear timetable
                clear_timetable();

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) >= 22 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 34)
            {

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 27)
                {
                    tV_timeTable_semester.setText("BREAK!");
                    // clear timetable
                    clear_timetable();

                }else{

                    tV_timeTable_semester.setText("Semester 2");

                    // show modules
                    show_modules2(user_course);
                }
            }else
            {
                tV_timeTable_semester.setText("BREAK!");
                // clear timetable
                clear_timetable();

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) > 51)
                {
                    btn_timeTable_next_week.setVisibility(View.GONE);
                }
            }

            // set week:
            tV_timeTable_week.setText("Wk: " + timeTable_calendar.get(Calendar.WEEK_OF_YEAR));
        });

        // setting the previous week button:
        btn_timeTable_previous_week.setOnClickListener(v -> {

            // set multiplayer:
            multiplayer--;

            // call timetable_date_spred
            timetable_date_spread(timeTable_calendar, multiplayer);

            // retrieving month
            timeTable_calendar.setTime(timeTable_current_date);
            timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));

            //set semester:
            if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 5)
            {
                tV_timeTable_semester.setText("BREAK!");
                // clear timetable
                clear_timetable();

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 2)
                {
                    btn_timeTable_previous_week.setVisibility(View.GONE);
                }

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) > 5 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 18)
            {

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 11) {

                    tV_timeTable_semester.setText("BREAK!");
                    // clear timetable
                    clear_timetable();

                }else {

                    tV_timeTable_semester.setText("Semester 1");
                    // show modules
                    show_modules(user_course);

                }

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) >= 18 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 22)
            {
                tV_timeTable_semester.setText("BREAK!");
                // clear timetable
                clear_timetable();

            }else if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) >= 22 && timeTable_calendar.get(Calendar.WEEK_OF_YEAR) < 34)
            {

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 27)
                {
                    tV_timeTable_semester.setText("BREAK!");
                    // clear timetable
                    clear_timetable();

                }else{

                    tV_timeTable_semester.setText("Semester 2");
                    // show modules
                    show_modules2(user_course);
                }
            }else
            {
                tV_timeTable_semester.setText("BREAK!");
                // clear timetable
                clear_timetable();

                if (timeTable_calendar.get(Calendar.WEEK_OF_YEAR) == 51)
                {
                    btn_timeTable_next_week.setVisibility(View.VISIBLE);
                }

            }

            // set week:
            tV_timeTable_week.setText("Wk: " + timeTable_calendar.get(Calendar.WEEK_OF_YEAR));
        });

    }

    private void clear_timetable() {

        // Monday
        tV_timeTable_MON1.setText("");
        tV_timeTable_MON1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_MON2.setText("");
        tV_timeTable_MON2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_MON3.setText("");
        tV_timeTable_MON3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Tuesday
        tV_timeTable_TUE1.setText("");
        tV_timeTable_TUE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_TUE2.setText("");
        tV_timeTable_TUE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_TUE3.setText("");
        tV_timeTable_TUE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Wednesday
        tV_timeTable_WED1.setText("");
        tV_timeTable_WED1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_WED2.setText("");
        tV_timeTable_WED2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_WED3.setText("");
        tV_timeTable_WED3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Thursday
        tV_timeTable_THU1.setText("");
        tV_timeTable_THU1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_THU2.setText("");
        tV_timeTable_THU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_THU3.setText("");
        tV_timeTable_THU3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Friday
        tV_timeTable_FRI1.setText("");
        tV_timeTable_FRI1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_FRI2.setText("");
        tV_timeTable_FRI2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tV_timeTable_FRI3.setText("");
        tV_timeTable_FRI3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void show_modules2(String user_course) {

        timetable_reference2 = FirebaseDatabase.getInstance().getReference("_module");

        module.clear();

        timetable_reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // get book title for user
                for (DataSnapshot dss : snapshot.getChildren()) {
                    Modules _modules = dss.getValue(Modules.class);

                    module.add(_modules);
                }

                //check on which course is current student
                switch (user_course)
                {

                    case "Computing Technology Degree":

                        // Monday
                        tV_timeTable_MON1.setText(module.get(3).getModule_short() + ", " +module.get(3).getModule_room());
                        tV_timeTable_MON1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                        startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                            }
                        });
                        tV_timeTable_MON2.setText(module.get(3).getModule_short() + ", " +module.get(3).getModule_room());
                        tV_timeTable_MON2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                            }
                        });
                        tV_timeTable_MON3.setText(module.get(5).getModule_short() + ", " +module.get(5).getModule_room());
                        tV_timeTable_MON3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                            }
                        });

                        // Wednesday
                        tV_timeTable_WED1.setText(module.get(4).getModule_short() + ", " +module.get(4).getModule_room());
                        tV_timeTable_WED1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                            }
                        });

                        tV_timeTable_WED2.setText(module.get(4).getModule_short() + ", " +module.get(4).getModule_room());
                        tV_timeTable_WED2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                            }
                        });
                        tV_timeTable_WED3.setText(module.get(5).getModule_short() + ", " +module.get(5).getModule_room());
                        tV_timeTable_WED3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                            }
                        });


                        break;
                    case "Business And Management Degree":

                        // Tuesday
                        tV_timeTable_TUE1.setText(module.get(9).getModule_short() + ", " +module.get(9).getModule_room());
                        tV_timeTable_TUE1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        tV_timeTable_TUE2.setText(module.get(9).getModule_short() + ", " +module.get(9).getModule_room());
                        tV_timeTable_TUE2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        tV_timeTable_TUE3.setText(module.get(11).getModule_short() + ", " +module.get(11).getModule_room());
                        tV_timeTable_TUE3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        //Thursday
                        tV_timeTable_THU1.setText(module.get(11).getModule_short() + ", " +module.get(11).getModule_room());
                        tV_timeTable_THU1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        tV_timeTable_THU2.setText(module.get(10).getModule_short() + ", " +module.get(10).getModule_room());
                        tV_timeTable_THU2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        tV_timeTable_THU3.setText(module.get(10).getModule_short() + ", " +module.get(10).getModule_room());
                        tV_timeTable_THU3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });

                        break;
                    case "Architecture Degree":

                        //Thursday
                        tV_timeTable_THU1.setText(module.get(15).getModule_short() + ", " +module.get(15).getModule_room());
                        tV_timeTable_THU1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_THU2.setText(module.get(16).getModule_short() + ", " +module.get(16).getModule_room());
                        tV_timeTable_THU2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_THU3.setText(module.get(16).getModule_short() + ", " +module.get(16).getModule_room());
                        tV_timeTable_THU3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });

                        // Friday
                        tV_timeTable_FRI1.setText(module.get(17).getModule_short() + ", " +module.get(17).getModule_room());
                        tV_timeTable_FRI1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_FRI2.setText(module.get(17).getModule_short() + ", " +module.get(17).getModule_room());
                        tV_timeTable_FRI2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_FRI3.setText(module.get(15).getModule_short() + ", " +module.get(15).getModule_room());
                        tV_timeTable_FRI3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });

                        break;
                    case "Architecture Extended Degree":

                        //Wednesday
                        tV_timeTable_WED1.setText(module.get(23).getModule_short() + ", " +module.get(23).getModule_room());
                        tV_timeTable_WED1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });
                        tV_timeTable_WED2.setText(module.get(23).getModule_short() + ", " +module.get(23).getModule_room());
                        tV_timeTable_WED2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });
                        tV_timeTable_WED3.setText(module.get(22).getModule_short() + ", " +module.get(22).getModule_room());
                        tV_timeTable_WED3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });

                        // Friday
                        tV_timeTable_FRI1.setText(module.get(21).getModule_short() + ", " +module.get(21).getModule_room());
                        tV_timeTable_FRI1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });
                        tV_timeTable_FRI2.setText(module.get(21).getModule_short() + ", " +module.get(21).getModule_room());
                        tV_timeTable_FRI2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });
                        tV_timeTable_FRI3.setText(module.get(22).getModule_short() + ", " +module.get(22).getModule_room());
                        tV_timeTable_FRI3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Third_Floor.class));
                            }
                        });

                        break;
                    case "Biochemistry Degree":

                        // Monday
                        tV_timeTable_MON1.setText(module.get(27).getModule_short() + ", " +module.get(27).getModule_room());
                        tV_timeTable_MON1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_MON2.setText(module.get(27).getModule_short() + ", " +module.get(27).getModule_room());
                        tV_timeTable_MON2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, First_Floor.class));
                            }
                        });
                        tV_timeTable_MON3.setText(module.get(28).getModule_short() + ", " +module.get(28).getModule_room());
                        tV_timeTable_MON3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                            }
                        });

                        // Tuesday
                        tV_timeTable_TUE1.setText(module.get(28).getModule_short() + ", " +module.get(28).getModule_room());
                        tV_timeTable_TUE1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                            }
                        });
                        tV_timeTable_TUE2.setText(module.get(29).getModule_short() + ", " +module.get(29).getModule_room());
                        tV_timeTable_TUE2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });
                        tV_timeTable_TUE3.setText(module.get(29).getModule_short() + ", " +module.get(29).getModule_room());
                        tV_timeTable_TUE3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TimeTable.this, Second_Floor.class));
                            }
                        });

                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(TimeTable.this, "connection lost!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void show_modules(String user_course) {


        timetable_reference2 = FirebaseDatabase.getInstance().getReference("_module");

        module.clear();

        timetable_reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // get book title for user
                for (DataSnapshot dss : snapshot.getChildren()) {
                    Modules _modules = dss.getValue(Modules.class);

                    module.add(_modules);
                }


                //check on which course is current student
                switch (user_course)
                {

                    case "Computing Technology Degree":


                    // Monday
                    tV_timeTable_MON1.setText(module.get(0).getModule_short() + ", " +module.get(0).getModule_room());
                    tV_timeTable_MON1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                        }
                    });
                    tV_timeTable_MON2.setText(module.get(1).getModule_short() + ", " +module.get(1).getModule_room());
                    tV_timeTable_MON2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                        }
                    });
                    tV_timeTable_MON3.setText(module.get(2).getModule_short() + ", " +module.get(2).getModule_room());
                    tV_timeTable_MON3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                        }
                    });

                    // Wednesday
                    tV_timeTable_WED1.setText(module.get(2).getModule_short() + ", " +module.get(2).getModule_room());
                    tV_timeTable_WED1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                        }
                    });
                    tV_timeTable_WED2.setText(module.get(0).getModule_short() + ", " +module.get(0).getModule_room());
                    tV_timeTable_WED2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Ground_Floor.class));
                        }
                    });
                    tV_timeTable_WED3.setText(module.get(1).getModule_short() + ", " +module.get(1).getModule_room());
                    tV_timeTable_WED3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Lower_Ground_1.class));
                        }
                    });


                    break;
                case "Business And Management Degree":

                    // Tuesday
                    tV_timeTable_TUE1.setText(module.get(6).getModule_short() + ", " +module.get(6).getModule_room());
                    tV_timeTable_TUE1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });
                    tV_timeTable_TUE2.setText(module.get(7).getModule_short() + ", " +module.get(7).getModule_room());
                    tV_timeTable_TUE2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });
                    tV_timeTable_TUE3.setText(module.get(7).getModule_short() + ", " +module.get(7).getModule_room());
                    tV_timeTable_TUE3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });
                    //Thursday
                    tV_timeTable_THU1.setText(module.get(6).getModule_short() + ", " +module.get(6).getModule_room());
                    tV_timeTable_THU1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });
                    tV_timeTable_THU2.setText(module.get(8).getModule_short() + ", " +module.get(8).getModule_room());
                    tV_timeTable_THU2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });
                    tV_timeTable_THU3.setText(module.get(8).getModule_short() + ", " +module.get(8).getModule_room());
                    tV_timeTable_THU3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Second_Floor.class));
                        }
                    });

                    break;
                case "Architecture Degree":

                    //Thursday
                    tV_timeTable_THU1.setText(module.get(12).getModule_short() + ", " +module.get(12).getModule_room());
                    tV_timeTable_THU1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_THU2.setText(module.get(12).getModule_short() + ", " +module.get(12).getModule_room());
                    tV_timeTable_THU2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_THU3.setText(module.get(13).getModule_short() + ", " +module.get(13).getModule_room());
                    tV_timeTable_THU3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });

                    // Friday
                    tV_timeTable_FRI1.setText(module.get(14).getModule_short() + ", " +module.get(14).getModule_room());
                    tV_timeTable_FRI1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_FRI2.setText(module.get(14).getModule_short() + ", " +module.get(14).getModule_room());
                    tV_timeTable_FRI2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_FRI3.setText(module.get(13).getModule_short() + ", " +module.get(13).getModule_room());
                    tV_timeTable_FRI3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });

                    break;
                case "Architecture Extended Degree":

                    //Wednesday
                    tV_timeTable_WED1.setText(module.get(18).getModule_short() + ", " +module.get(18).getModule_room());
                    tV_timeTable_WED1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });
                    tV_timeTable_WED2.setText(module.get(18).getModule_short() + ", " +module.get(18).getModule_room());
                    tV_timeTable_WED2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });
                    tV_timeTable_WED3.setText(module.get(19).getModule_short() + ", " +module.get(19).getModule_room());
                    tV_timeTable_WED3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });

                    // Friday
                    tV_timeTable_FRI1.setText(module.get(20).getModule_short() + ", " +module.get(20).getModule_room());
                    tV_timeTable_FRI1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });
                    tV_timeTable_FRI2.setText(module.get(20).getModule_short() + ", " +module.get(20).getModule_room());
                    tV_timeTable_FRI2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });
                    tV_timeTable_FRI3.setText(module.get(19).getModule_short() + ", " +module.get(19).getModule_room());
                    tV_timeTable_FRI3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Third_Floor.class));
                        }
                    });

                    break;
                case "Biochemistry Degree":

                    // Monday
                    tV_timeTable_MON1.setText(module.get(25).getModule_short() + ", " +module.get(25).getModule_room());
                    tV_timeTable_MON1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_MON2.setText(module.get(24).getModule_short() + ", " +module.get(24).getModule_room());
                    tV_timeTable_MON2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Lower_Ground_2.class));
                        }
                    });
                    tV_timeTable_MON3.setText(module.get(24).getModule_short() + ", " +module.get(24).getModule_room());
                    tV_timeTable_MON3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, Lower_Ground_2.class));
                        }
                    });

                    // Tuesday
                    tV_timeTable_TUE1.setText(module.get(26).getModule_short() + ", " +module.get(26).getModule_room());
                    tV_timeTable_TUE1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_TUE2.setText(module.get(25).getModule_short() + ", " +module.get(25).getModule_room());
                    tV_timeTable_TUE2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });
                    tV_timeTable_TUE3.setText(module.get(26).getModule_short() + ", " +module.get(26).getModule_room());
                    tV_timeTable_TUE3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TimeTable.this, First_Floor.class));
                        }
                    });

                    break;
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            Toast.makeText(TimeTable.this, "connection lost!", Toast.LENGTH_LONG).show();
        }
    });

    }

    private void timetable_date_spread(Calendar timeTable_calendar, int multiplayer) {

        // get the current day (assign necessary variables and objects):
        int day = timeTable_current_date.getDay();
        SimpleDateFormat sdf_timeTable = new SimpleDateFormat("dd.MM");

        switch (day){
            case 1:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,2 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,3 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,4 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,5 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,6 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));

                break;
            case 2:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,2 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,3 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,4 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,5 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));


                break;
            case 3:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-2 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,2 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,3 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,4 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));

                break;
            case 4:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-3 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-2 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,2 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,3 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));

                break;
            case 5:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-4 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-3 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-2 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,2 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                break;
            case 6:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-5 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-4 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-3 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-2 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,1 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                break;
            case 7:

                // setting dates in timeTable:
                // Monday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-6 + (multiplayer*7));
                tV_timeTable_date1.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Tuesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-5 + (multiplayer*7));
                tV_timeTable_date2.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Wednesday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-4 + (multiplayer*7));
                tV_timeTable_date3.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Thursday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-3 + (multiplayer*7));
                tV_timeTable_date4.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Friday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-2 + (multiplayer*7));
                tV_timeTable_date5.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Saturday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,-1 + (multiplayer*7));
                tV_timeTable_date6.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                // Sunday
                timeTable_calendar.setTime(timeTable_current_date);
                timeTable_calendar.add(Calendar.DAY_OF_MONTH,0 + (multiplayer*7));
                tV_timeTable_date7.setText(sdf_timeTable.format(timeTable_calendar.getTime()));
                break;
        }

    }
}