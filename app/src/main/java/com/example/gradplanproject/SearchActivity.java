package com.example.gradplanproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity that allows the user to search for BYU Idaho courses and add them to a list stored in SharedPreferences.
 */
public class SearchActivity extends AppCompatActivity {

    protected static SharedPreferences prefs;

    public static final String TAG = "SearchActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private WeakReference<Activity> weakRef;

    public List<Course> courseList;
    private ArrayList<String> days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);

            // Set the toolbar and navDrawer for this Activity
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);
            DrawerUtil.getDrawer(this, toolbar);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }


        // Instantiate some of the main variables
        prefs = getSharedPreferences("PREF_NAME", MODE_PRIVATE);
        weakRef = new WeakReference<Activity>(this);
        courseList = new ArrayList<>();

        // Instantiate RecyclerView and setting LayoutManager and custom Adapter class
        recyclerView = findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterS(courseList, new WeakReference<Activity>(this));
        recyclerView.setAdapter(adapter);


        // Fill each Spinner with appropriate text (from strings.xml) using ArrayAdapter
        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.startTime_list, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.endTime_list, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        // Initialize the list used for filtering from the checkBoxes; used in onCheckboxClicked()
        days = new ArrayList<>(5);
        for (int i = 0; i < 5; i++)
            days.add(i, "");
    }


    /**
     * The function called when any one of the CheckBoxes is clicked. Whenever one is clicked, the
     * appropriate value in the days array is changed to reflect the state of the CheckBox.
     * @param view Specifies which CheckBox was clicked
     */
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox1:
                if(checked)
                    days.set(0, "M");
                else
                    days.set(0, "");
                break;
            case R.id.checkBox2:
                if(checked)
                    days.set(1, "T");
                else
                    days.set(1, "");
                break;
            case R.id.checkBox3:
                if(checked)
                    days.set(2, "W");
                else
                    days.set(2, "");
                break;
            case R.id.checkBox4:
                if(checked)
                    days.set(3, "R");
                else
                    days.set(3, "");
                break;
            case R.id.checkBox5:
                if(checked)
                    days.set(4, "F");
                else
                    days.set(4, "");
                break;
        }
    }


    /**
     * This function pulls filter parameters from all the Views in SearchActivity and puts them
     * in WidgetDataStorage so they can be referenced later in searchCoursesByFilter().
     * @return An instantiated and appropriately filled WidgetDataStorage object
     */

    public WidgetDataStorage getDataFromForm() {

        // Get the data from the two text boxes and two spinners

        // Course Code Search Box
        EditText editText2 = findViewById(R.id.editText2);
        String courseCode = editText2.getText().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]+", "");

        // Start Time
        Spinner mySpinner1 = findViewById(R.id.spinner1);
        String startTime = mySpinner1.getSelectedItem().toString();

        // End Time
        Spinner mySpinner2 = findViewById(R.id.spinner2);
        String endTime = mySpinner2.getSelectedItem().toString();

        // Instructor Search Box
        EditText editText = findViewById(R.id.editText);
        String instructor = "";
        String tempInstructor = editText.getText().toString().replaceAll("[^a-zA-Z]+", "");
        if (tempInstructor.length()>= 2) {
            instructor = Character.toUpperCase(tempInstructor.charAt(0)) + tempInstructor.substring(1);
        }

        // Check if the "filled section" switch is on or off
        boolean sectionFull = false;
        Switch simpleSwitch = findViewById(R.id.switch1);
        if (simpleSwitch.isChecked()) {
            sectionFull = true;
        }

        // Check if the "online only" switch is on or off
        boolean onlineOnly = false;
        Switch simpleSwitch2 = findViewById(R.id.switch2);
        if (simpleSwitch2.isChecked()) {
            onlineOnly = true;
        }

        // Return a new WidgetDataStorage object with the parameters retrieved from each widget
        return ( new WidgetDataStorage(courseCode, startTime, endTime, instructor, sectionFull, onlineOnly, days) );
    }


    /**
     * This function will call the getDataFromForm function to get all the data from the widgets
     * and then it will filter and display the courses according to the parameters that
     * the user has put in the widgets.
     * @param view Specifies the button attached to this method (the "Search" button)
     */

    public void searchCoursesByFilter(View view) {

        Toast.makeText(weakRef.get().getApplicationContext(),
                "Searching...", Toast.LENGTH_LONG).show();

        final WidgetDataStorage wds = getDataFromForm();

        // Log.d(TAG, "Here are the values: Course Code - " + wds.getCourseCodeOrName()
        // + " Start Time -  " + wds.getStartTime() + " End Time - " + wds.getEndTime()
        // + " instructor - " + wds.getInstructor() + " filter courses - " + wds.isSectionFull());

        // Any currently displayed Courses are removed from the list and the RecyclerView
        courseList.clear();
        adapter.notifyDataSetChanged();

        // Instantiate the required Firestore object, then search for all Courses that match the
        // course code provided by the user.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("semesters").document("2019;SP").collection("sections")
                .whereEqualTo("course", wds.getCourseCodeOrName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Gson gson = new Gson();

                            // This for-statement will pass over every Course returned from Firestore
                            // and check to see if it matches all the filters saved in WidgetDataStorage.
                            // If it fails any one of the filter checks, "continue" will be called
                            // immediately and the Course will not be added to the display list.
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String courseString = gson.toJson(document.getData());
                                Course course = gson.fromJson(courseString, Course.class);

                                if (wds.isOnlineOnly()) {
                                    if (!(course.getType().equals("ONLN")))
                                        continue;
                                }

                                if (wds.isSectionFull()) {
                                    if (course.getSeatsFilled() >= course.getSeatsTotal())
                                        continue;
                                }

                                if (wds.getInstructor().length() != 0) {
                                    if (!(wds.getInstructor().equals(course.getInstructors().get(0).get("first"))))
                                        continue;
                                }

                                boolean matchesDays = true;
                                List<String> daysList = course.getSingleDaysArray();
                                for (int i = 0; i < 5; i++) {
                                    if (wds.getDays().get(i).length() != 0)
                                        if (!(wds.getDays().get(i).equals(daysList.get(i)))) {
                                            matchesDays = false;
                                            break;
                                        }
                                }
                                if (!matchesDays)
                                    continue;

                                if (!(wds.getStartTime().equals("Starts after..."))) {
                                    Resources res = getResources();
                                    String[] startTimeArray = res.getStringArray(R.array.startTime_list);
                                    ArrayList<String> startTimeList = new ArrayList<>(Arrays.asList(startTimeArray));
                                    int index = startTimeList.indexOf(wds.getStartTime());

                                    boolean matchesStart = false;
                                    for (; index < startTimeList.size(); index++)
                                        if (startTimeList.get(index).equals(course.getSchedules().get(0).get("start"))) {
                                            matchesStart = true;
                                            break;
                                        }

                                    if (!matchesStart)
                                        continue;
                                }

                                if (!(wds.getEndTime().equals("Ends before..."))) {
                                    Resources res = getResources();
                                    String[] endTimeArray = res.getStringArray(R.array.endTime_list);
                                    ArrayList<String> endTimeList = new ArrayList<>(Arrays.asList(endTimeArray));
                                    int index = endTimeList.indexOf(wds.getEndTime());

                                    boolean matchesEnd = false;
                                    for (; index > 0; index--)
                                        if (endTimeList.get(index).equals(course.getSchedules().get(0).get("end"))) {
                                            matchesEnd = true;
                                            break;
                                        }

                                    if (!matchesEnd)
                                        continue;
                                }

                                // Course must have passed all filter checks to reach this point, so
                                // it can now be added to the list.
                                courseList.add(course);
                                adapter.notifyDataSetChanged();
                            }

                            // If the list is still empty, the filtering must not have returned any
                            // results. A Course will be instantiated and displayed in the guise of
                            // a message box.
                            if (courseList.size() == 0) {
                                Course noResults = new Course();
                                noResults.setCode("NO RESULTS");
                                noResults.setName("Try adjusting filter options");
                                courseList.add(noResults);
                                adapter.notifyDataSetChanged();
                            }
                        } else
                            Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        //Log.d(TAG, "This is the result of filtering: " + sections);
    }


    /**
     * Saves the provided Course to the list in SharedPreferences. Pulls the String currently saved
     * in SharedPreferences, converts it to a list of Courses using Gson, adds the new Course to the
     * list, converts the list back into a Json String, then overwrites the old String in
     * SharedPreferences with the new one.
     * @param course The course to be saved
     */
    public static void saveCourse(Course course) {

        SharedPreferences.Editor prefsEditor = prefs.edit();

        Gson gson = new Gson();
        String jsonCourse = gson.toJson(course);

        String def = "";
        String jsonCourseList = prefs.getString(String.valueOf(R.string.spring_2019_list), def);

        List<String> courseStrings = new ArrayList<>();

        if(gson.fromJson(jsonCourseList, ArrayList.class) != null) {
            courseStrings = gson.fromJson(jsonCourseList, ArrayList.class);
        }

        courseStrings.add(jsonCourse);

        prefsEditor.putString(String.valueOf(R.string.spring_2019_list), gson.toJson(courseStrings));
        prefsEditor.apply();
    }

    /* Function for attempting to retrieve the current "state" of a Spinner
    public void testSpinnerData(View view) {
        Spinner mySpinner = findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();
        Log.i(TAG, "This is the data from the spinner: " + text);
    }*/

    /* Function for attempting to retrieve the current "state" of a Switch
    public void onCheckedChanged(View view) {
        boolean switchState;
        Switch simpleSwitch = findViewById(R.id.switch1);

        switchState = simpleSwitch.isChecked();
        System.out.println("Switch: " + switchState);
    }*/
}
