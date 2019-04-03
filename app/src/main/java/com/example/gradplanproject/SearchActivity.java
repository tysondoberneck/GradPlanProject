package com.example.gradplanproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    protected static SharedPreferences prefs;

    private boolean switchState = false;

    public static final String TAG = "SearchActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
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

        /**
         * courseListExample is just filler data at this point. We will be implementing
         * a pull from FireStore to populate the RecyclerView box. It currently just returns
         * null - null, but it will get fixed.
         */

        courseList = new ArrayList<>();

//        Course course1 = new Course("Software Design and Development", "CS235",
//                "Section 01: Burton - MWF - 9:00-10:00 AM", 3);
//        courseList.add(course1);
//
//        Course course2 = new Course("Foundations of the Restoration", "FDREL225",
//                "Section 03: Taylor - TR - 7:45-8:45 AM", 3);
//        courseList.add(course2);
//
//        Course course3 = new Course("Linear Algebra", "MATH341",
//                "Section 07: Nelson - MWF - 11:30-12:30 PM", 3);
//        courseList.add(course3);
//
//        courseList.add(course1);
//        courseList.add(course2);
//        courseList.add(course3);
//        courseList.add(course1);
//        courseList.add(course2);
//        courseList.add(course3);


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
        days = new ArrayList<>(5);

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
        String courseCodeOrName = editText2.getText().toString().toUpperCase().replaceAll(" ","");

        Spinner mySpinner1 = findViewById(R.id.spinner1);
        String endTime = mySpinner1.getSelectedItem().toString();

        Spinner mySpinner2 = findViewById(R.id.spinner2);
        String startTime = mySpinner2.getSelectedItem().toString();

        EditText editText = findViewById(R.id.editText);
        String instructor = editText.getText().toString();

        //check if the switch is on or off
        boolean sectionFull = false;
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        if (simpleSwitch.isChecked() == true) {
            sectionFull = true;
        }

        //run the function to retrieve data from the checkboxes.
        //There are private global variables to store that data
        //onCheckboxClicked(view);

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
    public void testQueryAndDisplayData(View view) {
        final WidgetDataStorage wds = getDataFromForm(view);
        //final List<Course> courses = new ArrayList<>();

        //test data
        Log.d(TAG, "Here are the values: Course Code - " + wds.getCourseCodeOrName() + " Start Time -  " + wds.getStartTime() + " End Time - " + wds.getEndTime() + " instructor - " + wds.getInstructor() + " filter courses - " + wds.isSectionFull());

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

                                //This is how we can access the data

                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                //Log.d(TAG, "This is the amount of credits of this course: " + document.get("credits"));
                                //Log.i(TAG, document.toString());

                                String courseString = gson.toJson(document.getData());
                                Course course = gson.fromJson(courseString, Course.class);

                                if (wds.getInstructor() != course.getInstructors().get(0).get("first")) {
                                    continue;
                                }

                                if (wds.getStartTime() != course.getSchedules().get(0).get("start")) {
                                    continue;
                                }

                                if (wds.getStartTime() != course.getSchedules().get(0).get("end")) {
                                    continue;
                                }

                                if (wds.isSectionFull()) {
                                    if (course.getSeatsFilled() == course.getSeatsTotal())
                                        continue;
                                }

                                if (course.getSchedules().size() == 1) {
                                    boolean matches = true;
                                    ArrayList<String> daysList = (ArrayList) course.getSchedules().get(0).get("days");
                                    for (int i = 0; i < 5; i++) {
                                        if (wds.getDays().get(i) != daysList.get(i)) {
                                            matches = false;
                                            continue;
                                        }
                                    }
                                    if (matches == false)
                                        continue;
                                }

                                //courses.add(course);
                                courseList.add(course);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Log.d(TAG, "This is the result of filtering: " + sections);
    }

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

//        String json = gson.toJson(course);
//        Set<String> defaultCourseSet = new HashSet<>();
//        Set<String> courseSet;
//        courseSet = prefs.getStringSet(String.valueOf(R.string.spring_2019), defaultCourseSet);
//        courseSet.add(json);
//        prefsEditor.putStringSet(String.valueOf(R.string.spring_2019), courseSet);
//        prefsEditor.apply();
//        Log.d(TAG, "course saved to SharedPreferences");
    }
}
