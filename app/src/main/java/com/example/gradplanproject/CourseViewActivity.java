package com.example.gradplanproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseViewActivity extends AppCompatActivity {

    public static final String TAG = "CourseViewActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Set defaultCourse;
    public List<Map<String, String>> courseListExample;
    public List<Course> courseList;

    protected static SharedPreferences prefs;

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
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }

        defaultCourse = new HashSet<>();

        prefs = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);

        Spinner spinner = findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        courseListExample = new ArrayList<>();
//
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("code", "CS235");
//        map1.put("name", "Software Design and Development");
//        map1.put("details", "Section 01: Burton - MWF - 9:00-10:00 AM");
//        courseListExample.add(map1);
//
//        Map<String, String> map2 = new HashMap<>();
//        map2.put("code", "FDREL225");
//        map2.put("name", "Foundations of the Restoration");
//        map2.put("details", "Section 03: Taylor - TR - 7:45-8:45 AM");
//        courseListExample.add(map2);
//
//        Map<String, String> map3 = new HashMap<>();
//        map3.put("code", "MATH341");
//        map3.put("name", "Linear Algebra");
//        map3.put("details", "Section 07: Nelson - MWF - 11:30-12:30 PM");
//        courseListExample.add(map3);

        courseList = new ArrayList<>();

        List<String> courseStringList = new ArrayList<>(loadCourseList());
        Gson gson = new Gson();
        Course addCourse;

        for(String courseString : courseStringList) {
            addCourse = gson.fromJson(courseString, Course.class);
            courseList.add(addCourse);
        }

        System.out.println(courseList.size());

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new RecyclerAdapterCV(courseList, new WeakReference<Activity>(this));
        recyclerView.setAdapter(rAdapter);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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


    public void callFirebaseCollection(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("semesters").document("2019;SP").collection("sections")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Task is successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            System.out.println("In the else");
                        }
                        System.out.println("outside");
                    }
                });
    }

    public List loadCourseList() {
        Gson gson = new Gson();
        String def = "";
        String courseStrings = prefs.getString(String.valueOf(R.string.spring_2019New), def);
        List<String> courseList = new ArrayList<>(gson.fromJson(courseStrings, List.class));

        return courseList;
    }
}
