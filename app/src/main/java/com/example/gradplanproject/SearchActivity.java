package com.example.gradplanproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;

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

        // Filler text for recyclerView. Eventually, the recyclerView should instead take in the
        // intended list of Courses, which will be, basically, dressed-up Maps

        courseListExample = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("code", "CS235");
        map1.put("name", "Software Design and Development");
        map1.put("details", "Burton - MWF - 9:00 AM");
        courseListExample.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("code", "FDREL225");
        map2.put("name", "Foundations of the Restoration");
        map2.put("details", "Taylor - TR - 7:45 AM");
        courseListExample.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("code", "MATH341");
        map3.put("name", "Linear Algebra");
        map3.put("details", "Nelson - MWF - 11:30 AM");
        courseListExample.add(map3);

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
    }

    public void testSpinnerData(View view) {
        Spinner mySpinner = findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();
        Log.i(TAG, "This is the data from the spinner: " + text);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox1:
                if(checked)
                    monday = true;
                else
                    monday = false;
                break;
            case R.id.checkBox2:
                if (checked)
                    tuesday = true;
                else
                    tuesday = false;
                break;
            case R.id.checkBox3:
                if(checked)
                    wednesday = true;
                else
                    wednesday = false;
                break;
            case R.id.checkBox4:
                if (checked)
                    thursday = true;
                else
                    thursday = false;
                break;
            case R.id.checkBox5:
                if (checked)
                    friday = true;
                else
                    friday = false;
                break;
        }
    }


//    public void testSearchViewData(View view) {
//
//        SearchView sv = findViewById(R.id.searchView1);
//        String text = sv.get().toString();
//
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                adapter.getFilter().filter(newText);
//            }
//            return false;
//        }
//    });
//    }

    public void testQueryAndDisplayData(View view) {

        Spinner mySpinner1 = findViewById(R.id.spinner1);
        String endTime = mySpinner1.getSelectedItem().toString();

        Spinner mySpinner2 = findViewById(R.id.spinner2);
        String startTime = mySpinner2.getSelectedItem().toString();

        System.out.println("Let's test the days of the week:" + monday + " " + tuesday + " " + wednesday + " " + thursday + " " + friday + "\n");
    }
}
