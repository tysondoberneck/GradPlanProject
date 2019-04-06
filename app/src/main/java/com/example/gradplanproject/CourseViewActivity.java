package com.example.gradplanproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An activity designed to display all courses the user has added via SearchActivity,
 * as well as provide an option to delete them.
 */

public class CourseViewActivity extends AppCompatActivity {

    public static final String TAG = "CourseViewActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Course> courseList;
    private WeakReference<Activity> weakRef;

    protected static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_view);

            // Create Toolbar/NavDrawer
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);
            DrawerUtil.getDrawer(this, toolbar);

        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }


        // Initialize variables
        weakRef = new WeakReference<Activity>(this);
        prefs = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        courseList = new ArrayList<>();

        // Prepare Spinner View
        Spinner spinner = findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        // If this is the first time someone is opening the app, something needs to be saved
        // to the slot to be used in SharedPreferences before it can be referenced.
        if(!prefs.contains(String.valueOf(R.string.spring_2019_list))) {
            SharedPreferences.Editor prefsEditor = prefs.edit();
            Course defaultCourse = new Course();
            String jsonExample = gson.toJson(defaultCourse);
            List<String> exampleList = new ArrayList<>();
            exampleList.add(jsonExample);
            prefsEditor.putString(String.valueOf(R.string.spring_2019_list), gson.toJson(exampleList));
            prefsEditor.apply();
        }


        // Initialize RecyclerView and set LayoutManager and Adapter
        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        this.adapter = new RecyclerAdapterCV(courseList, new WeakReference<Activity>(this));
        recyclerView.setAdapter(this.adapter);
    }


    /**
     * Implementation in onResume() ensures that the displayed list of Courses always reflects
     * any Courses the user may have just saved in SearchActivity.
     */

    @Override
    protected void onResume() {
        super.onResume();

        Gson gson = new Gson();

        List<String> courseStringList = new ArrayList<>(loadCourseList());
        Course addCourse;
        courseList.clear();

        for(String courseString : courseStringList) {
            addCourse = gson.fromJson(courseString, Course.class);
            courseList.add(addCourse);
        }

        adapter.notifyDataSetChanged();
    }


    /**
     * Save CourseList to SharedPreferences when this activity (or the whole app) is paused.
     */

    @Override
    protected void onPause() {
        super.onPause();

        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = prefs.edit();

        List<String> courseStrings = new ArrayList<>();

        for(Course course : courseList) {
            String jsonCourse = gson.toJson(course);
            courseStrings.add(jsonCourse);
        }

        prefsEditor.putString(String.valueOf(R.string.spring_2019_list), gson.toJson(courseStrings));
        prefsEditor.apply();
    }


    /**
     * A very simple onClick function to take the user from this Activity to SearchActivity. No
     * information or parameters are passed.
     * @param view Specifies the button attached to this function.
     */

    public void onClickSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    /**
     * onClick function for moving to ScheduleActivity.
     * @param view Specifies the button attached to this function.
     */

    public void onClickScheduleActivity(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        view.getContext().startActivity(intent);
    }


    /**
     * Load CourseList from SharedPreferences and parse it using Gson.
     * @return An ArrayList of Strings. Each String is a Course object stored as a Json String.
     */

    public List loadCourseList() {
        Gson gson = new Gson();
        String def = "";
        String courseStrings = prefs.getString(String.valueOf(R.string.spring_2019_list), def);

        System.out.println(courseStrings);

        return new ArrayList<>(gson.fromJson(courseStrings, List.class));
    }


    /**
     * Function similar to searchCourseByFilter in SearchActivity. This function refreshes the list
     * of courses saved by the user. Called when the "Update" button is clicked.
     * @param view Specifies the button attached to this function.
     */

    public void updateList(View view) {

        Toast.makeText(weakRef.get().getApplicationContext(),
                "Updating saved courses...", Toast.LENGTH_LONG).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        int listSize = courseList.size();

        for (Course c : courseList) {

            // Find each Course saved by the user. As the search is made by "code" only one Course
            // should ever be returned by each Firebase call. However, the syntax for these calls is
            // a little mysterious, so the implementation is left in the for-loop.
            db.collection("semesters").document("2019;SP")
                    .collection("sections").whereEqualTo("code", c.getCode()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Gson gson = new Gson();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String courseString = gson.toJson(document.getData());
                                Course course = gson.fromJson(courseString, Course.class);

                                courseList.add(course);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        }

        // Get rid of the old Courses
        for (int i = 0; i < listSize; i++) {
            courseList.remove(0);
        }

        adapter.notifyDataSetChanged();
    }


    // Function for testing connection to and information retrieval from Firebase.
    /*public void callFirebaseDocument(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("semesters").document("2019;SP")
                .collection("sections").document("CS124-01");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        System.out.println("yay " + document);
                    } else {
                        Log.d(TAG, "No such document");
                        System.out.println("nay " + document);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    System.out.println("nothing worked =\\");
                }
            }
        });
    }*/
}