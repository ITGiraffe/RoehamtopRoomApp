package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Profile extends AppCompatActivity {

    // (iV - ImageView, tV - textView):
    // creating impostors:
    private ImageView iV_profile_picture;

    // necessary variables and objects:
    private Uri imageURI;
    private FirebaseStorage profile_storage;
    private StorageReference profile_reference;
    private FirebaseUser profile_user;
    private DatabaseReference profile_database_reference_user;
    private DatabaseReference profile_database_reference_book;
    private String profile_userID, profile_first_name, profile_last_name, profile_studentID,
            profile_email, profile_course, path, extension, title;
    // necessary list to get book title:
    private ArrayList<_Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // linking impostors:
        iV_profile_picture = findViewById(R.id.iV_profile_picture);
        final TextView tV_profile_studentID = (TextView) findViewById(R.id.tV_profile_studentID);
        final TextView tV_profile_first_name = (TextView) findViewById(R.id.tV_profile_first_name);
        final TextView tV_profile_last_name = (TextView) findViewById(R.id.tV_profile_last_name);
        final TextView tV_profile_email = (TextView) findViewById(R.id.tV_profile_email);
        final TextView tV_profile_course = (TextView) findViewById(R.id.tV_profile_course);
        final TextView tV_profile_book = (TextView) findViewById(R.id.tV_profile_book);

        // initialising firebase objects:
        profile_storage = FirebaseStorage.getInstance();
        profile_reference = profile_storage.getReference();

        // Initialise necessary variables / objects
        profile_user = FirebaseAuth.getInstance().getCurrentUser();
        profile_database_reference_user = FirebaseDatabase.getInstance().getReference("_user");
        profile_userID = profile_user.getUid();

        // Getting data from user database
        profile_database_reference_user.child(profile_userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                // checking if userProfile is not empty
                if (userProfile != null) {

                    // saving value and inserting into tV's
                    profile_studentID = userProfile.getStudent_ID();
                    profile_first_name = userProfile.getFirst_name();
                    profile_last_name = userProfile.getLast_name();
                    profile_email = userProfile.getEmail();
                    profile_course = userProfile.getCourse_name();

                    // inserting
                    tV_profile_studentID.setText(profile_studentID);
                    tV_profile_first_name.setText(profile_first_name);
                    tV_profile_last_name.setText(profile_last_name);
                    tV_profile_email.setText(profile_email);
                    tV_profile_course.setText(profile_course);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if not having any data than just log out the user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Dashboard.class));
            }
        });

        // connecting to book database
        profile_database_reference_book = FirebaseDatabase.getInstance().getReference("_book");

        // Getting data from book database
        profile_database_reference_book.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // get book title for user
                for (DataSnapshot dss : snapshot.getChildren()) {
                    _Book book = dss.getValue(_Book.class);



                    books.add(book);
                }

                for (int i = 0; i<books.size(); i++)
                {

                    if (books.get(i).getBook_reserved_user().equals(profile_userID))
                    {
                        title = books.get(i).getBook_title();
                    }
                }

                // displaying book title
                tV_profile_book.setText(title);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if not having any data than just log out the user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Dashboard.class));
            }
        });



        // setting up IV_profile_picture using lambda
        iV_profile_picture.setOnClickListener(v -> {

            // creating method that allows user to select image from their device:
            choose_profile_picture();
        });
    }

    private void choose_profile_picture() {

        // creating intent to open gallery on their device:
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // setting the request code (1)
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking the request code, and data is not null -> that they select the image
        if (requestCode == 1 && resultCode==RESULT_OK && data!= null && data.getData() != null)
        {
            // saving image into variable:
            imageURI = data.getData();

            // passing in the image into IV_profile_picture:
            Picasso.get().load(imageURI).into(iV_profile_picture);

            // new method to upload picture:
            upload_profile_picture();
        }
    }

    // method to upload picture
    private void upload_profile_picture() {

        // creating a bar with percentage loading:
        final ProgressDialog profile_pD = new ProgressDialog(this);
        profile_pD.setTitle("Uploading Image...");
        profile_pD.show();

        // set path of the image and its extension
        path = "images/img/" + profile_userID + "_picture";
        extension = get_extension(imageURI);

        // Create a reference
        StorageReference profile_file_reference = profile_reference.child(path + "." + extension);

        // uploading file
        profile_file_reference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                profile_file_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String url = uri.toString();

                        // saving data for change
                        HashMap update = new HashMap();
                        update.put("url", url);

                        // saving url into firebase database:
                        profile_database_reference_user.child(profile_userID).updateChildren(update);

                        // when the upload is finished -> hide the progress dialog
                        profile_pD.dismiss();

                        // go to dashboard
                        startActivity(new Intent(Profile.this, Dashboard.class));

                        // like progress bar who will tell that picture is uploading and successfully uploaded
                        Toast.makeText(getApplicationContext(), "Image Uploaded!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // when the upload is finished -> hide the progress dialog
                        profile_pD.dismiss();

                        // error message that something went wrong:
                        Toast.makeText(getApplicationContext(), "Failed to Upload!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        // getting the percentage of the upload:
                        // by 100 times the bytes transferred over the total bytes:
                        double profile_progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                        // setting message progress:
                        profile_pD.setMessage("Percentage: " + (int) profile_progressPercentage + "%");
                    }
                });
    }

    // method to get extension of the file:
    private String get_extension(Uri uri)
    {
        ContentResolver contentResolver_profile = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver_profile.getType(uri));
    }
}