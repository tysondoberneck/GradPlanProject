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
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity that allows the user to search for BYU Idaho courses and add them to a list stored in SharedPreferences.
 */
public class SearchActivity extends AppCompatActivity {

    /**
     * Editor to store list in SharedPreferences.
     */
    protected static SharedPreferences prefs;

    private boolean switchState = false;

    public static final String TAG = "SearchActivity";

    /**
     * The view that displays courses that fit search criteria.
     */
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private WeakReference<Activity> weakRef;

    public List<Map<String, String>> courseListExample;
    public List<Course> courseList;

    private ArrayList<String> days;

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
        weakRef = new WeakReference<Activity>(this);

        /**
         * courseListExample is just filler data at this point. We will be implementing
         * a pull from FireStore to populate the RecyclerView box. It currently just returns
         * null - null, but it will get fixed.
         */

        courseList = new ArrayList<>();

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
                R.array.startTime_list, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.endTime_list, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // For filtering from the checkBoxes; used in onCheckboxClicked()
        days = new ArrayList<>(5);
        for (int i = 0; i < 5; i++)
            days.add(i, "");
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
     * Test function for the switch to check what it returns.
     * @param view
     */
    public void onCheckedChanged(View view) {
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);

        switchState = simpleSwitch.isChecked();
        System.out.println("Switch: " + switchState);
    }

    public WidgetDataStorage getDataFromForm(View view) {
        // Get the data from the two text boxes and two spinners
        //Course Code Search Box
        EditText editText2 = findViewById(R.id.editText2);
        String courseCodeOrName = editText2.getText().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]+", "");

        //Start Time
        Spinner mySpinner1 = findViewById(R.id.spinner1);
        String startTime = mySpinner1.getSelectedItem().toString();

        //End Time
        Spinner mySpinner2 = findViewById(R.id.spinner2);
        String endTime = mySpinner2.getSelectedItem().toString();

        //Instructor Search Box
        EditText editText = findViewById(R.id.editText);
        String instructor = "";
        String tempinstructor = editText.getText().toString().replaceAll("[^a-zA-Z]+", "");
        if (tempinstructor.length()>= 2) {
            instructor = Character.toUpperCase(tempinstructor.charAt(0)) + tempinstructor.substring(1);
        }

        //check if the switch is on or off
        boolean sectionFull = false;
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        if (simpleSwitch.isChecked()) {
            sectionFull = true;
        }

        //create a new WidgetDataStorage object with the parameters
        //retrieved from the widgets
        //and then return it
        WidgetDataStorage wds = new WidgetDataStorage(courseCodeOrName, startTime, endTime, instructor, sectionFull, days);
        return wds;
    }

    /**
     * This function will call the getDataFromForm function to get all the data from the widgets
     * and then it will filter and display the courses according to those parameters that
     * the user has input in the widgets
     * @param view
     */
    public void searchCoursesByFilter(View view) {

        Toast.makeText(weakRef.get().getApplicationContext(),
                "Searching...", Toast.LENGTH_LONG).show();

        final WidgetDataStorage wds = getDataFromForm(view);

        courseList.clear();
        adapter.notifyDataSetChanged();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("semesters").document("2019;SP").collection("sections").whereEqualTo("course", wds.getCourseCodeOrName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Gson gson = new Gson();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Remove this line from the above to have SEARCH call the whole course list
                                // .whereEqualTo("course", wds.getCourseCodeOrName())

                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                //Log.d(TAG, "This is the amount of credits of this course: " + document.get("credits"));

                                //creat a Json string of the current section
                                //and create a new course with that data
                                String courseString = gson.toJson(document.getData());
                                Course course = gson.fromJson(courseString, Course.class);

                                //check if the user wants to filter by instructor name
                                if (wds.getInstructor().length() != 0) {
                                    if (!(wds.getInstructor().equals(course.getInstructors().get(0).get("first"))))
                                        continue;
                                }

                                //filter by start time
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

                                    if (!matchesStart) {
                                        continue;
                                    }
                                }

                                //filter by end time
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

                                    if (!matchesEnd) {
                                        continue;
                                    }
                                }

                                //if the user wants to filter out the full sections, dont add them to the list
                                if (wds.isSectionFull()) {
                                    if (course.getSeatsFilled() >= course.getSeatsTotal())
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

                                courseList.add(course);
                                adapter.notifyDataSetChanged();
                            }

                            //if at the end of the search there are no courses or sections that passed the filters
                            //create a new course that would just prompt the user to check filters.
                            if (courseList.size() == 0) {
                                Course noResults = new Course();
                                noResults.setCode("NO RESULTS");
                                noResults.setName("Try adjusting filter options");
                                courseList.add(noResults);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Saves a course to the list in SharedPreferences
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
}
