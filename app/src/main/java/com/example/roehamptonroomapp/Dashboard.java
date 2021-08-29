package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Dashboard extends AppCompatActivity {

    // creating object of ViewPager2
    ViewPager2 obj_ViewPager2;

    // creating buttons and imageView
    private Button btn_dashboard_floor_map, btn_roehampton_moodle, btn_dashboard_library,
            btn_dashboard_logout, btn_dashboard_profile, btn_dashboard_timetable,
            btn_dashboard_book_sale, btn_dashboard_forum, btn_dashboard_activities;
    private ImageView iV_dashboard_picture;

    // necessary variables and objects
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, first_name, picture_dashboard_url;

    // preparing the array of pictures
    int[] images = {R.drawable.pic7, R.drawable.pic8, R.drawable.pic3, R.drawable.pic2,
            R.drawable.pic5, R.drawable.pic6, R.drawable.pic4};

    // Creating Object of PicturesAdapter
    PicturesAdapter obj_PicturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Assign the ViewPager2 Object
        obj_ViewPager2 = findViewById(R.id.vP_dashboard_slider);

        // Assign buttons
        btn_dashboard_floor_map = findViewById(R.id.btn_dashboard_floor_map);
        btn_roehampton_moodle = findViewById(R.id.btn_dashboard_moodle);
        btn_dashboard_library = findViewById(R.id.btn_dashboard_library);
        btn_dashboard_logout= findViewById(R.id.btn_dashboard_logout);
        btn_dashboard_profile = findViewById(R.id.btn_dashboard_profile);
        btn_dashboard_timetable = findViewById(R.id.btn_dashboard_timetable);
        btn_dashboard_book_sale = findViewById(R.id.btn_dashboard_book_sale);
        btn_dashboard_forum = findViewById(R.id.btn_dashboard_forum);
        btn_dashboard_activities = findViewById(R.id.btn_dashboard_activities);

        // linking imageView
        iV_dashboard_picture = findViewById(R.id.iV_dashboard_picture);

        // Initialise PictureAdapter object
        obj_PicturesAdapter = new PicturesAdapter(images);

        // Set adapter on viewpager
        obj_ViewPager2.setAdapter(obj_PicturesAdapter);

        // Initialise necessary variables / objects
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("_user");
        userID = user.getUid();

        // Initialise textView welcome object
        final TextView tV_dashboard_welcome = (TextView) findViewById(R.id.tV_dashboard_welcome);

        if (user.isAnonymous())
        {
            // welcome anonymous
            tV_dashboard_welcome.setText("Welcome Guest!");

        }else {

            // Getting data from database
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User userProfile = snapshot.getValue(User.class);

                    // checking if userProfile is not empty
                    if (userProfile != null) {

                        // saving values for profile picture:
                        picture_dashboard_url = userProfile.getUrl();

                        // checking is user have profile picture
                        if (picture_dashboard_url != null)
                        {
                            Picasso.get().load(picture_dashboard_url).into(iV_dashboard_picture);
                        }

                        // saving value and inserting into welcome tV
                        first_name = userProfile.getFirst_name();
                        tV_dashboard_welcome.setText("Welcome " + first_name + "!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    // if not having any data than just log out the user
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Dashboard.this, Login.class));
                }
            });
        }

        // setting roehampton_moodle button to navigate the user to the moodle website using lamda
        btn_roehampton_moodle.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring to moddle page
                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk/my/"));
                startActivity(Getintent);
            }
        });

        // setting profile button using lambda
        btn_dashboard_profile.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view your profile!", Toast.LENGTH_LONG).show();
            }else{

                // transferring into profile activity
                startActivity(new Intent(Dashboard.this, Profile.class));
            }
        });

        // setting floor_map button to navigate to Floor_Map activity using lambda expression
        btn_dashboard_floor_map.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into Floor_Map activity
                startActivity(new Intent(Dashboard.this, Floor_Map.class));
            }
        });

        // setting timetable button using lambda
        btn_dashboard_timetable.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into TimeTable activity
                startActivity(new Intent(Dashboard.this, TimeTable.class));
            }
        });

        // setting library button to navigate to Library activity using lambda expression
        btn_dashboard_library.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into Library activity
                startActivity(new Intent(Dashboard.this, Library.class));
            }
        });

        // setting timetable button using lambda
        btn_dashboard_book_sale.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into book sale activity
                startActivity(new Intent(Dashboard.this, Book_Sale.class));
            }
        });

        // setting forum button using lambda
        btn_dashboard_forum.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into forum activity
                startActivity(new Intent(Dashboard.this, Forum.class));
            }
        });

        // setting activities button using lambda
        btn_dashboard_activities.setOnClickListener(v -> {

            // check the user
            if (user.isAnonymous()){

                // block this button for anonymous user
                Toast.makeText(Dashboard.this, "You have to log in to view this content!", Toast.LENGTH_LONG).show();
            }else {

                // transferring into activities activity
                startActivity(new Intent(Dashboard.this, Activities.class));
            }
        });

        // logging user out
        btn_dashboard_logout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, Login.class));
        });

    }
}