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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
 * An activity designed to display all courses the user has added via SearchActivity, as well as provide an option to delete them.
 */
public class CourseViewActivity extends AppCompatActivity {

    public static final String TAG = "CourseViewActivity";

    /**
     * The view displaying the CourseList.
     */
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Course> courseList;
    private WeakReference<Activity> weakRef;

    /**
     * Editor to access the list of courses stored in SharedPreferences
     */
    protected static SharedPreferences prefs;
    private WidgetDataStorage wds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Toolbar/NavDrawer
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_view);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);

            DrawerUtil.getDrawer(this, toolbar);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }

        weakRef = new WeakReference<Activity>(this);

        //Initialize SharedPreferences
        prefs = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);

        Spinner spinner = findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Gson gson = new Gson();

        //Initialize SharedPreferences if nothing is stored.
        if(!prefs.contains(String.valueOf(R.string.spring_2019_list))) {
            SharedPreferences.Editor prefsEditor = prefs.edit();
            Course defaultCourse = new Course();
            String jsonExample = gson.toJson(defaultCourse);
            List<String> exampleList = new ArrayList<>();
            exampleList.add(jsonExample);
            prefsEditor.putString(String.valueOf(R.string.spring_2019_list), gson.toJson(exampleList));
            prefsEditor.apply();
        }

        courseList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new RecyclerAdapterCV(courseList, new WeakReference<Activity>(this));
        recyclerView.setAdapter(rAdapter);
    }

    /**
     * Load CourseList when this activity is resumed.
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

        rAdapter.notifyDataSetChanged();
    }

    /**
     * Save CourseList to SharedPreferences when this activity is paused.
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

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void callFirebaseDocument(View view) {
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
    }


    public void openSchedule(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        view.getContext().startActivity(intent);
    }

    /**
     * Load CourseList from SharedPreferences and parse it using Json.
     * @return An ArrayList of Strings. Each String is a Course object stored as a Json String.
     */
    public List loadCourseList() {
        Gson gson = new Gson();
        String def = "";
        String courseStrings = prefs.getString(String.valueOf(R.string.spring_2019_list), def);

        System.out.println(courseStrings);

        List<String> courseList = new ArrayList<>(gson.fromJson(courseStrings, List.class));

        return courseList;
    }

    /**
     * Function similar to searchCourseByFilter in SearchActivity. This is the function that refreshes the list
     * of courses the student made when the ,"Update" button is pressed.
     * @param view
     */
    public void updateList(View view) {

        Toast.makeText(weakRef.get().getApplicationContext(),
                "Updating saved courses...", Toast.LENGTH_LONG).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        int listSize = courseList.size();

        for (Course c : courseList) {

            db.collection("semesters").document("2019;SP").collection("sections").whereEqualTo("code", c.getCode()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Gson gson = new Gson();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String courseString = gson.toJson(document.getData());
                                Course course = gson.fromJson(courseString, Course.class);

                                courseList.add(course);
                                rAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        }
        for (int i = 0; i < listSize; i++) {
            courseList.remove(0);
        }
        rAdapter.notifyDataSetChanged();
    }
}