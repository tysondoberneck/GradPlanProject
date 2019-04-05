package com.example.gradplanproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Displays user created schedule based on the schedule stored in SharedPreferences (edited by Search and
 * CourseView Activities)
 */
public class ScheduleActivity extends AppCompatActivity {

    public static final String TAG = "ScheduleActivity";

    /**
     * Pixel density value for dp calculations
     */
    private float density;

    private int onlineCoursePlacement;

    /**
     * Editor to access SharedPreferences Course List
     */
    protected static SharedPreferences prefs;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.gpp));
            setSupportActionBar(toolbar);

            DrawerUtil.getDrawer(this, toolbar);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }

        onlineCoursePlacement = 0;

        prefs = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);

        density = getApplicationContext().getResources().getDisplayMetrics().density;

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        List<Course> courseList = new ArrayList<Course>(loadCourseList());

        ViewGroup mondayParent = (ViewGroup)findViewById(R.id.mondayInsertPoint);
        ViewGroup tuesdayParent = (ViewGroup)findViewById(R.id.tuesdayInsertPoint);
        ViewGroup wednesdayParent = (ViewGroup)findViewById(R.id.wednesdayInsertPoint);
        ViewGroup thursdayParent = (ViewGroup)findViewById(R.id.thursdayInsertPoint);
        ViewGroup fridayParent = (ViewGroup)findViewById(R.id.fridayInsertPoint);
        ViewGroup onlineParent = (ViewGroup)findViewById(R.id.onlineInsertPoint);

        for(int i = 0; i < courseList.size(); i++) {
            //Create template textview
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
            //Get days for current course
            ArrayList<String> daysArray = new ArrayList<String>((ArrayList<String>)courseList.get(i).getSchedules().get(0).get("days"));
            //Set text to current course's code
            textView.setText(courseList.get(i).getCourse());
            //Define layout parameters
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = (int) (getDP((String)(courseList.get(i).getSchedules().get(0).get("start"))) * density);
            textView.setLayoutParams(params);
            textView.getLayoutParams().height = (int) (getHeight((String)courseList.get(i).getSchedules().get(0).get("start"),(String)courseList.get(i).getSchedules().get(0).get("end")) * density);
            //Add text for course time
            textView.append("\n" + (String)(courseList.get(i).getSchedules().get(0).get("start")) + " - " + (String)(courseList.get(i).getSchedules().get(0).get("end")));

            //If course takes place on checked day, make a new TextView with the same text and parameters as template, and add it to the days RelativeLayout.
            if(courseList.get(i).getSingleDaysArray().get(0).equals("M")) {
                TextView mondayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                mondayText.setText(textView.getText());
                mondayText.setLayoutParams(textView.getLayoutParams());
                mondayParent.addView(mondayText);
            }
            if(courseList.get(i).getSingleDaysArray().get(1).equals("T")) {
                TextView tuesdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                tuesdayText.setText(textView.getText());
                tuesdayText.setLayoutParams(textView.getLayoutParams());
                tuesdayParent.addView(tuesdayText);
            }
            if(courseList.get(i).getSingleDaysArray().get(2).equals("W")) {
                TextView wednesdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                wednesdayText.setText(textView.getText());
                wednesdayText.setLayoutParams(textView.getLayoutParams());
                wednesdayParent.addView(wednesdayText);
            }
            if(courseList.get(i).getSingleDaysArray().get(3).equals("R")) {
                TextView thursdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                thursdayText.setText(textView.getText());
                thursdayText.setLayoutParams(textView.getLayoutParams());
                thursdayParent.addView(thursdayText);
            }
            if(courseList.get(i).getSingleDaysArray().get(4).equals("F")) {
                TextView fridayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                fridayText.setText(textView.getText());
                fridayText.setLayoutParams(textView.getLayoutParams());
                fridayParent.addView(fridayText);
            }
            if(courseList.get(i).getType().equals("ONLN"))  {
                TextView onlineText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                params.topMargin = (int) (40 + (onlineCoursePlacement * (density*65)));
                textView.setLayoutParams(params);
                textView.getLayoutParams().height = (int) (60 * density);
                onlineText.setText("   " + courseList.get(i).getCourse() + "   ");
                onlineText.setLayoutParams(textView.getLayoutParams());
                onlineParent.addView(onlineText);
                onlineCoursePlacement++;
            }
        }
    }

    /**
     * Loads the String list of courses from SharedPreferences, then parses the list into a list
     * of course objects.
     * @return The list of course objects stored in SharedPreferences
     */
    public List loadCourseList() {
        String def = "";
        String courseStrings = prefs.getString(String.valueOf(R.string.spring_2019_list), def);

        System.out.println(courseStrings);

        List<String> courseStringList = new ArrayList<>(gson.fromJson(courseStrings, List.class));
        List<Course> courseList = new ArrayList<>();
        Course addCourse;

        for(String courseString : courseStringList) {
            addCourse = gson.fromJson(courseString, Course.class);
            courseList.add(addCourse);
        }

        return courseList;
    }

    /**
     * Converts a String value representing time (e.g. "9:00 AM") to an integer DP value (540)
     * @param time A String representing the time a course takes place
     * @return A DP value representing the time a course takes place (60 DP = 1 Hour)
     */
    public int getDP(String time) {
        char [] timeChars = time.toCharArray();
        int minutes = 0;
        //If hour is single digits (i.e. 9:00 AM, 4:00 PM, not 10:00 AM
        if(timeChars[1] == ':') {
            minutes += (Character.getNumericValue(timeChars[0]) * 60);
            minutes += (((Character.getNumericValue(timeChars[2])*10) + Character.getNumericValue(timeChars[3])));
            if(timeChars[5] == 'P') {
                //If time is PM, add 12 hours
                minutes += (12*60);
            }
        }
        else {
            minutes += (((Character.getNumericValue(timeChars[0]) + 9) + Character.getNumericValue(timeChars[1])) * 60);
            minutes += ((Character.getNumericValue(timeChars[3])*10) + Character.getNumericValue(timeChars[4]));
        }

        return ((minutes-420));
    }

    public int getHeight(String startTime, String endTime) {
        return (int)((getDP(endTime) - getDP(startTime)));
    }
}
