package com.example.roehamptonroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Library extends AppCompatActivity {

    // (tV - textView, btn- Button)
    // creating impostors:
    private TextView tV_library_book, tV_library_book2, tV_library_book3, tV_library_booked, tV_library_booked2, tV_library_booked3;
    private Button btn_library_search, btn_library_info, btn_library_info2, btn_library_info3,
            btn_library_reserve, btn_library_reserve2, btn_library_reserve3, btn_library_cancel,
            btn_library_cancel2, btn_library_cancel3, btn_library_previous, btn_library_next;

    // necessary variables and objects
    private DatabaseReference reference_library, reference_library2, reference_library_user;
    public String spinner_chosen_category;
    private int book_print_count = 0, book_print_count_PK;
    private FirebaseUser library_user;
    private String current_library_user;

    // Necessary lists to store categories and books
    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<_Book> books = new ArrayList<_Book>();
    public ArrayList<String> book_KEY = new ArrayList<>();
    private Boolean flag_reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Adding values into spinner dropdown - categories list
        categories.add(Course.Business.toString());
        categories.add(Course.Computing.toString());
        categories.add(Course.Economics.toString());
        categories.add(Course.Mathematics.toString());

        // Linking spinner for categories list
        Spinner spinner_library_categories = (Spinner) findViewById(R.id.spinner_library_categories);

        // Linking tV and btn:
        tV_library_book = findViewById(R.id.tV_library_book);
        tV_library_book2 = findViewById(R.id.tV_library_book2);
        tV_library_book3 = findViewById(R.id.tV_library_book3);
        tV_library_booked = findViewById(R.id.tV_library_booked);
        tV_library_booked2 = findViewById(R.id.tV_library_booked2);
        tV_library_booked3 = findViewById(R.id.tV_library_booked3);
        btn_library_search = findViewById(R.id.btn_library_search);
        btn_library_info = findViewById(R.id.btn_library_info);
        btn_library_info2 = findViewById(R.id.btn_library_info2);
        btn_library_info3 = findViewById(R.id.btn_library_info3);
        btn_library_reserve = findViewById(R.id.btn_library_reserve);
        btn_library_reserve2 = findViewById(R.id.btn_library_reserve2);
        btn_library_reserve3 = findViewById(R.id.btn_library_reserve3);
        btn_library_cancel = findViewById(R.id.btn_library_cancel);
        btn_library_cancel2 = findViewById(R.id.btn_library_cancel2);
        btn_library_cancel3 = findViewById(R.id.btn_library_cancel3);
        btn_library_previous = findViewById(R.id.btn_library_previous);
        btn_library_next = findViewById(R.id.btn_library_next);

        // adding keys into arraylist
        book_KEY.add("MW_a2YaT1TBqrSCqtwn");
        book_KEY.add("MW_a2Ydxeag33lgoz7X");
        book_KEY.add("MW_a2Ydxeag33lgoz7Y");
        book_KEY.add("MW_dQ3ECz0fPlNnM2PD");
        book_KEY.add("MW_dQ3LQLldSOnOJ2ST");
        book_KEY.add("MW_dQ3QvdYltuv8coey");
        book_KEY.add("MW_fPvjgq1WPp2SdhZq");
        book_KEY.add("MW_fPvo02lAoFtczAtq");
        book_KEY.add("MW_fPvo02lAoFtczAtr");
        book_KEY.add("MW_hsM64MLDgHYR3qW_");
        book_KEY.add("MW_hsM8ALcddftOA_UO");
        book_KEY.add("MW_hsM8ALcddftOA_UP");

        // Creating an ArrayAdapter to get resources from courses list
        ArrayAdapter<String> categories_library_Adapter = new ArrayAdapter<String>(Library.this,
                android.R.layout.simple_list_item_1, categories);

        // set dropdown in adapter and connect adapter with spinner
        categories_library_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_library_categories.setAdapter(categories_library_Adapter);

        // Initialise firebase objects
        reference_library = FirebaseDatabase.getInstance().getReference("_book");
        reference_library_user = FirebaseDatabase.getInstance().getReference("_user");
        library_user = FirebaseAuth.getInstance().getCurrentUser();
        current_library_user = library_user.getUid();

        // Getting data from user database
        reference_library_user.child(current_library_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                // checking if userProfile is not empty
                if (userProfile != null) {

                    // saving value and inserting into tV's
                    flag_reserve = userProfile.getReserved();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if not having any data than just log out the user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Library.this, Dashboard.class));
            }
        });

        // setting search button:
        btn_library_search.setOnClickListener(v -> {

            // Clearing the book array:
            books.clear();

            // getting the chosen category
            spinner_chosen_category = spinner_library_categories.getSelectedItem().toString();

            // retrieving data:
            reference_library.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dss : snapshot.getChildren()) {
                        _Book lib_book = dss.getValue(_Book.class);
                        String aaa = spinner_chosen_category;

                        //adding books into arraylist
                        if (lib_book.getBook_category() != null && lib_book.getBook_category().equals(aaa)) {

                            books.add(lib_book);
                        }
                    }

                    switch(spinner_chosen_category){
                        case "Business":
                            book_print_count_PK = 3;
                            break;
                        case "Computing":
                            book_print_count_PK = 6;
                            break;
                        case "Economics":
                            book_print_count_PK = 9;
                            break;
                        case "Mathematics":
                            book_print_count_PK = 12;
                            break;

                    }
                    // set count
                    book_print_count = 0;

                    // if no books in the category:
                    if (books.size() == 0) {
                        // Displaying appropriate message
                        Toast.makeText(Library.this, "We do not have books from this category! Please check later!", Toast.LENGTH_LONG).show();

                    } else if (books.size() == 1) {
                        // Display on screen (one row):
                        display_row(1, book_print_count);

                    } else if (books.size() == 2) {
                        // Display on screen the books
                        display_row(2, book_print_count);

                    } else if (books.size() == 3) {
                        // Display all three row's
                        display_row(3, book_print_count);

                    } else if (books.size() > 3) {
                        // Display all three row's
                        display_row(3, book_print_count);

                        // Display "next" button:
                        btn_library_next.setVisibility(View.VISIBLE);
                    }

                    // set count
                    book_print_count = 3;

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(Library.this, "Connection error!", Toast.LENGTH_LONG).show();
                }
            });
        });

        // setting the next button:
        btn_library_next.setOnClickListener(v -> {

            // unblock previous button
            btn_library_previous.setVisibility(View.VISIBLE);


            if ((books.size() - book_print_count) < 4) {
                // disabling the button
                btn_library_next.setVisibility(View.GONE);

                // printing books
                int how_many_books_left = (books.size() - book_print_count);
                switch (how_many_books_left) {
                    case 1:
                        display_row(1, book_print_count);
                        break;
                    case 2:
                        display_row(2, book_print_count);
                        break;
                    case 3:
                        display_row(3, book_print_count);
                        break;
                }
            } else {

                display_row(3, book_print_count);
            }

            // set count
            book_print_count += 3;
        });

        // setting the previous button:
        btn_library_previous.setOnClickListener(v -> {

            // unable next button:
            btn_library_next.setVisibility(View.VISIBLE);

            // decrementing the counter:
            book_print_count -= 6;

            // printing books
            display_row(3, book_print_count);

            if (book_print_count == 0) {
                // disabling the button
                btn_library_previous.setVisibility(View.GONE);

            }

            // set count
            book_print_count += 3;
        });

        // set information button for 1st book:
        btn_library_info.setOnClickListener(v -> {

            // go to book information with a book PK
            Intent i_info = new Intent(Library.this, Book_Info.class);
            String key  = book_KEY.get(book_print_count_PK-3);
            i_info.putExtra("PK", key );
            startActivity(i_info);
        });

        // set information button for 2nd book:
        btn_library_info2.setOnClickListener(v -> {

            // go to book information with a book PK
            Intent i_info = new Intent(Library.this, Book_Info.class);
            String key  = book_KEY.get(book_print_count_PK-2);
            i_info.putExtra("PK", key );
            startActivity(i_info);
        });

        // set information button for 3rd book:
        btn_library_info3.setOnClickListener(v -> {

            // go to book information with a book PK
            Intent i_info = new Intent(Library.this, Book_Info.class);
            String key  = book_KEY.get(book_print_count_PK-1);
            i_info.putExtra("PK", key );
            startActivity(i_info);
        });

        // set reservation button for 1st book:
        btn_library_reserve.setOnClickListener(v -> {

            // get connection to the book in database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // check if user already reserve a book:
            if (!flag_reserve) {

                // set book PK
                String key = book_KEY.get(book_print_count_PK - 3);

                Toast.makeText(Library.this, key, Toast.LENGTH_LONG).show();

                // set collection to save data:
                HashMap update = new HashMap();
                HashMap update2 = new HashMap();

                // set data in collection:
                update.put("book_reserve", true);
                update.put("book_reserved_user", current_library_user);

                // set a book class
                _Book book = new _Book();

                // update values
                reference_library2.child(key).updateChildren(update);

                // set data to save into user:
                update2.put("reserved", true);

                reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        // show booked message:
                        tV_library_booked.setText("Booked");

                        // set flag:
                        flag_reserve = true;

                        // show message and set buttons:
                        Toast.makeText(Library.this, "The book has been reserved", Toast.LENGTH_LONG).show();
                        btn_library_cancel.setVisibility(View.VISIBLE);
                        btn_library_reserve.setVisibility(View.GONE);
                    }
                });

            } else {

                // Displaying a message:
                Toast.makeText(Library.this, "To reserve another book, you need to cancel previous one!", Toast.LENGTH_LONG).show();
            }

        });

        // set reservation button for 2nd book:
        btn_library_reserve2.setOnClickListener(v ->
        {

            // set connection to the book in database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // check if user already reserve a book:
            if (!flag_reserve) {

                // get a book PK
                String key = book_KEY.get(book_print_count_PK - 2);

                // set collection to save data:
                HashMap update = new HashMap();
                HashMap update2 = new HashMap();

                // set data in collection:
                update.put("book_reserve", true);
                update.put("book_reserved_user", current_library_user);

                // set a book class
                _Book book = new _Book();

                // update values in book database
                reference_library2.child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });

                // set data to save into user:
                update2.put("reserved", true);

                // update values in user database
                reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        // show booked message:
                        tV_library_booked2.setText("Booked");

                        // set flag:
                        flag_reserve = true;

                        // show message and set buttons:
                        Toast.makeText(Library.this, "The book has been reserved", Toast.LENGTH_LONG).show();
                        btn_library_cancel2.setVisibility(View.VISIBLE);
                        btn_library_reserve2.setVisibility(View.GONE);
                    }
                });

            } else {

                // Displaying a message:
                Toast.makeText(Library.this, "To reserve another book, you need to cancel previous one!", Toast.LENGTH_LONG).show();
            }

        });

        // set reservation button for 3rd book:
        btn_library_reserve3.setOnClickListener(v -> {

            // get connected top the book database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // check if user already reserve a book:
            if (!flag_reserve) {

                // get book PK
                String key = book_KEY.get(book_print_count_PK - 1);

                // set collection to save data:
                HashMap update = new HashMap();
                HashMap update2 = new HashMap();

                // set data in collection:
                update.put("book_reserve", true);
                update.put("book_reserved_user", current_library_user);

                // set a book class
                _Book book = new _Book();

                // update book database
                reference_library2.child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });

                // set data to save into user:
                update2.put("reserved", true);

                // update user database that book is booked / flag
                reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        // show booked message:
                        tV_library_booked3.setText("Booked");

                        // set flag:
                        flag_reserve = true;

                        // show message and set buttons:
                        Toast.makeText(Library.this, "The book has been reserved", Toast.LENGTH_LONG).show();
                        btn_library_cancel3.setVisibility(View.VISIBLE);
                        btn_library_reserve3.setVisibility(View.GONE);
                    }
                });

            } else {

                // Displaying a message:
                Toast.makeText(Library.this, "To reserve another book, you need to cancel previous one!", Toast.LENGTH_LONG).show();
            }

        });

        // set a cancel button in row 1
        btn_library_cancel.setOnClickListener(v -> {

            // connecting to the book database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // set a book PK
            String key = book_KEY.get(book_print_count_PK - 3);

            // set collection to save data:
            HashMap update = new HashMap();
            HashMap update2 = new HashMap();

            // set data in collection:
            update.put("book_reserve", false);
            update.put("book_reserved_user", "none");

            // set a book class
            _Book book = new _Book();

            // update book details
            reference_library2.child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // show message about cancellation
                    Toast.makeText(Library.this, "Book cancelled!", Toast.LENGTH_LONG).show();

                }
            });

            // set data to save into user:
            update2.put("reserved", false);

            // update users database
            reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // hide booked message:
                    tV_library_booked.setText("");

                    // set flag:
                    flag_reserve = false;

                    // show message and set buttons:
                    btn_library_cancel.setVisibility(View.GONE);
                    btn_library_reserve.setVisibility(View.VISIBLE);
                }
            });
        });

        // set a cancel button in row 2
        btn_library_cancel2.setOnClickListener(v -> {

            // connect to the database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // get a book PK
            String key = book_KEY.get(book_print_count_PK - 2);

            // set collection to save data:
            HashMap update = new HashMap();
            HashMap update2 = new HashMap();

            // set data in collection:
            update.put("book_reserve", false);
            update.put("book_reserved_user", "none");

            // set a book class
            _Book book = new _Book();

            // update book details
            reference_library2.child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // show message about cancellation
                    Toast.makeText(Library.this, "Book cancelled!", Toast.LENGTH_LONG).show();

                }
            });

            // set data to save into user:
            update2.put("reserved", false);

            // update users database
            reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // hide booked message:
                    tV_library_booked2.setText("");

                    // set flag:
                    flag_reserve = false;

                    // show message and set buttons:
                    btn_library_cancel2.setVisibility(View.GONE);
                    btn_library_reserve2.setVisibility(View.VISIBLE);
                }
            });
        });

        // set a cancel button in row 3
        btn_library_cancel3.setOnClickListener(v -> {

            // get connection to the book database
            reference_library2 = FirebaseDatabase.getInstance().getReference("_book");

            // get book PK
            String key = book_KEY.get(book_print_count_PK - 1);

            // set collection to save data:
            HashMap update = new HashMap();
            HashMap update2 = new HashMap();

            // set data in collection:
            update.put("book_reserve", false);
            update.put("book_reserved_user", "none");

            // set a book class
            _Book book = new _Book();

            // update a book database
            reference_library2.child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // show cancellation message
                    Toast.makeText(Library.this, "Book cancelled!", Toast.LENGTH_LONG).show();

                }
            });

            // set data to save into user:
            update2.put("reserved", false);

            // update user database
            reference_library_user.child(current_library_user).updateChildren(update2).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {

                    // hide booked message:
                    tV_library_booked3.setText("");

                    // set flag:
                    flag_reserve = false;

                    // show message and set buttons:
                    btn_library_cancel3.setVisibility(View.GONE);
                    btn_library_reserve3.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    private void display_row(int row_number, int count) {

        switch (row_number)
        {
            case 1:

                tV_library_book.setVisibility(View.VISIBLE);
                tV_library_book.setText(books.get(0 + count).getBook_title());
                btn_library_info.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(0 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(0 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked.setText("Booked");
                        btn_library_cancel.setVisibility(View.VISIBLE);

                    }else {

                        // print booked information
                        tV_library_booked.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked.setText("");
                    btn_library_reserve.setVisibility(View.VISIBLE);
                    btn_library_cancel.setVisibility(View.GONE);
                }

                break;
            case 2:

                tV_library_book.setVisibility(View.VISIBLE);
                tV_library_book.setText(books.get(0 + count).getBook_title());
                btn_library_info.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(0 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(0 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked.setText("Booked");
                        btn_library_cancel.setVisibility(View.VISIBLE);

                    }else {

                        // print booked information
                        tV_library_booked.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked.setText("");
                    btn_library_reserve.setVisibility(View.VISIBLE);
                    btn_library_cancel.setVisibility(View.GONE);
                }

                tV_library_book2.setVisibility(View.VISIBLE);
                tV_library_book2.setText(books.get(1 + count).getBook_title());
                btn_library_info2.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(1 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve2.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(0 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked2.setText("Booked");
                        btn_library_cancel2.setVisibility(View.VISIBLE);

                    }else {

                        // print booked information
                        tV_library_booked2.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked2.setText("");
                    btn_library_reserve2.setVisibility(View.VISIBLE);
                    btn_library_cancel2.setVisibility(View.GONE);
                }

                break;
            case 3:

                tV_library_book.setVisibility(View.VISIBLE);
                tV_library_book.setText(books.get(0 + count).getBook_title());
                btn_library_info.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(0 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(0 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked.setText("Booked");
                        btn_library_cancel.setVisibility(View.VISIBLE);

                    }else {

                        // print booked information
                        tV_library_booked.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked.setText("");
                    btn_library_reserve.setVisibility(View.VISIBLE);
                    btn_library_cancel.setVisibility(View.GONE);
                }

                tV_library_book2.setVisibility(View.VISIBLE);
                tV_library_book2.setText(books.get(1 + count).getBook_title());
                btn_library_info2.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(1 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve2.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(1 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked2.setText("Booked");
                        btn_library_cancel2.setVisibility(View.VISIBLE);

                    }else {

                        // print booked information
                        tV_library_booked2.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked2.setText("");
                    btn_library_reserve2.setVisibility(View.VISIBLE);
                    btn_library_cancel2.setVisibility(View.GONE);
                }

                tV_library_book3.setVisibility(View.VISIBLE);
                tV_library_book3.setText(books.get(2 + count).getBook_title());
                btn_library_info3.setVisibility(View.VISIBLE);

                // check is the book already reserved:
                if (books.get(2 + count).getBook_reserve() == true)
                {
                    // hide reserve button:
                    btn_library_reserve3.setVisibility(View.GONE);

                    // if reserved than check is reserved by current user:
                    if (books.get(2 + count).getBook_reserved_user().equals(current_library_user))
                    {
                        // print booked information and give option to cancel:
                        tV_library_booked3.setText("Booked");
                        btn_library_cancel3.setVisibility(View.VISIBLE);


                    }else {

                        // print booked information
                        tV_library_booked3.setText("Not available");
                    }

                }else {

                    // Clear information:
                    tV_library_booked3.setText("");
                    btn_library_reserve3.setVisibility(View.VISIBLE);
                    btn_library_cancel3.setVisibility(View.GONE);
                }

                break;

        }
    }
}