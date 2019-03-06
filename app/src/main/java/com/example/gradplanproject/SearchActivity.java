package com.example.gradplanproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //public String myDataset[];
    public List<Map<String, String>> courseListExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // RECYCLERVIEW

//        myDataset = new String[1000];
//        for ( int i = 0; i < 1000; i++) {
//            myDataset[i] = "String Example #" + i;
//        }
//
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(mAdapter);

        courseListExample = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        courseListExample.add(map1);
        courseListExample.get(0).put("code", "CS235");
        courseListExample.get(0).put("name", "Software Design and Development");
        courseListExample.get(0).put("details", "Burton - MWF - 9:00 AM");

        Map<String, String> map2 = new HashMap<>();
        courseListExample.add(map2);
        courseListExample.get(1).put("code", "FDREL225");
        courseListExample.get(1).put("name", "Foundations of the Restoration");
        courseListExample.get(1).put("details", "Taylor - TR - 7:45 AM");

        Map<String, String> map3 = new HashMap<>();
        courseListExample.add(map3);
        courseListExample.get(2).put("code", "MATH341");
        courseListExample.get(2).put("name", "Linear Algebra");
        courseListExample.get(2).put("details", "Nelson - MWF - 11:30 AM");

        recyclerView = findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(courseListExample);
        recyclerView.setAdapter(adapter);

        // RECYCLERVIEW

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

        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.day_list, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        Spinner spinner4 = findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.instructor_list, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
    }
}
