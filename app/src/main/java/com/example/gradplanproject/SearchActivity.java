package com.example.gradplanproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;

    protected static SharedPreferences prefs;

    private boolean switchState = false;

    public static final String TAG = "SearchActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Map<String, String>> courseListExample;
    public List<Course> courseList;

    /**
     * Creates the Toolbar/NavDrawer
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        prefs = getSharedPreferences("PREF_NAME", MODE_PRIVATE);

        /**
         * courseListExample is just filler data at this point. We will be implementing
         * a pull from FireStore to populate the RecyclerView box. It currently just returns
         * null - null, but it will get fixed.
         */

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
//
//        courseListExample.add(map1);
//        courseListExample.add(map2);
//        courseListExample.add(map3);
//        courseListExample.add(map1);
//        courseListExample.add(map2);
//        courseListExample.add(map3);

        courseList = new ArrayList<>();

        Course course1 = new Course("Software Design and Development", "CS235",
                "Section 01: Burton - MWF - 9:00-10:00 AM");
        courseList.add(course1);

        Course course2 = new Course("Foundations of the Restoration", "FDREL225",
                "Section 03: Taylor - TR - 7:45-8:45 AM");
        courseList.add(course2);

        Course course3 = new Course("Linear Algebra", "MATH341",
                "Section 07: Nelson - MWF - 11:30-12:30 PM");
        courseList.add(course3);

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);


        /**
         * Instantiating recyclerView and setting layoutManager and custom Adapter class
         */

        recyclerView = findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterS(courseList, new WeakReference<Activity>(this));
        recyclerView.setAdapter(adapter);

        /**
         * Filling each Spinner with appropriate text (from strings.xml) using ArrayAdapter
         */


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

    /**
     * Just a test to see what spinner data is returned into the log.
     * @param view
     */
    public void testSpinnerData(View view) {
        Spinner mySpinner = findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();
        Log.i(TAG, "This is the data from the spinner: " + text);
    }

    /**
     * Early version of the day selector checkboxes. Boolean true/false returns based on click.
     * @param view
     */
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

    /**
     * Test function for the switch to check what it returns.
     * @param view
     */
    public void onCheckedChanged(View view) {
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);

        switchState = simpleSwitch.isChecked();
        System.out.println("Switch: " + switchState);
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


    public WidgetDataStorage getDataFromForm(View view) {

        // Get the data from the two text boxes and two spinners
        EditText editText2 = findViewById(R.id.editText2);
        String courseCodeOrName = editText2.getText().toString();

        Spinner mySpinner1 = findViewById(R.id.spinner1);
        String endTime = mySpinner1.getSelectedItem().toString();

        Spinner mySpinner2 = findViewById(R.id.spinner2);
        String startTime = mySpinner2.getSelectedItem().toString();

        EditText editText = findViewById(R.id.editText);
        String instructor = editText.getText().toString();

        //check if the switch is on or off
        boolean filterFull = false;
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        if (simpleSwitch.isChecked() == true) {
            filterFull = true;
        }

        //run the function to retrieve data from the checkboxes.
        //There are private global variables to store that data
        //onCheckboxClicked(view);

        //create a new WidgetDataStorage object with the parameters
        //retrieved from the widgets
        //and then return it
        WidgetDataStorage wds = new WidgetDataStorage(courseCodeOrName, startTime, endTime, instructor, filterFull);
        return wds;
    }

    /**
     * Another test to see what is returned on day preference checkboxes based on which ones are clicked.
     * @param view
     */
    public void testQueryAndDisplayData(View view) {
        WidgetDataStorage wds = getDataFromForm(view);

        //test data
        Log.d(TAG, "Here are the values: Course Code - " + wds.getCourseCodeOrName() + " Start Time -  " + wds.getStartTime() + " End Time - " + wds.getEndTime() + " instructor - " + wds.getInstructor() + " filter courses - " + wds.isFilterFull());
    }

    public static void saveCourse(Course course) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(course);
        Set defaultCourseSet = new HashSet<>();
        Set courseSet;
        courseSet = prefs.getStringSet(String.valueOf(R.string.spring_2019_list), defaultCourseSet);
        courseSet.add(json);
        prefsEditor.putStringSet(String.valueOf(R.string.spring_2019_list), courseSet);
        prefsEditor.apply();
        Log.d(TAG, "course saved to SharedPreferences");
    }
}
