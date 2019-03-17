package com.example.gradplanproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Map<String, String>> courseListExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Create Toolbar/NavDrawer
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);

            DrawerUtil.getDrawer(this, toolbar);
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        //

        // Filler text for recyclerView. Eventually, the recyclerView should instead take in the
        // intended list of Courses, which will be, basically, dressed-up Maps

        courseListExample = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "CS235");
        map1.put("name", "Software Design and Development");
        map1.put("details", "Burton - MWF - 9:00 AM");
        courseListExample.add(map1);
//        courseListExample.get(0).put("code", "CS235");
//        courseListExample.get(0).put("name", "Software Design and Development");
//        courseListExample.get(0).put("details", "Burton - MWF - 9:00 AM");

        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "FDREL225");
        map2.put("name", "Foundations of the Restoration");
        map2.put("details", "Taylor - TR - 7:45 AM");
        courseListExample.add(map2);
//        courseListExample.get(1).put("code", "FDREL225");
//        courseListExample.get(1).put("name", "Foundations of the Restoration");
//        courseListExample.get(1).put("details", "Taylor - TR - 7:45 AM");

        Map<String, String> map3 = new HashMap<>();
        map3.put("code", "MATH341");
        map3.put("name", "Linear Algebra");
        map3.put("details", "Nelson - MWF - 11:30 AM");
        courseListExample.add(map3);
//        courseListExample.get(2).put("code", "MATH341");
//        courseListExample.get(2).put("name", "Linear Algebra");
//        courseListExample.get(2).put("details", "Nelson - MWF - 11:30 AM");

        courseListExample.add(map1);
        courseListExample.add(map2);
        courseListExample.add(map3);
        courseListExample.add(map1);
        courseListExample.add(map2);
        courseListExample.add(map3);

        // Instantiating recyclerView and setting layoutManager and custom Adapter class

        recyclerView = findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(courseListExample);
        recyclerView.setAdapter(adapter);


        // Filling each Spinner with appropriate text (from strings.xml) using ArrayAdapter

        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.beforeTime_list, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.afterTime_list, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

//        Spinner spinner3 = findViewById(R.id.spinner3);
//        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
//                R.array.day_list, android.R.layout.simple_spinner_item);
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner3.setAdapter(adapter3);
//
//        Spinner spinner4 = findViewById(R.id.spinner4);
//        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
//                R.array.instructor_list, android.R.layout.simple_spinner_item);
//        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner4.setAdapter(adapter4);
    }

    public void testSpinnerData(View view) {
        Spinner mySpinner = findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();
        Log.i(TAG, "This is the data from the spinner: " + text);
    }
}
