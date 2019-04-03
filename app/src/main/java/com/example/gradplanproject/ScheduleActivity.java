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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Displays user created schedule based on the schedule stored in SharedPrefs (edited by Search and
 * CourseView Activities)
 */
public class ScheduleActivity extends AppCompatActivity {

    public static final String TAG = "CourseViewActivity";

    /**
     * Pixel density value for dp calculations
     */
    private float density;

    /**
     * Editor to access SharedPreferences Course List
     */
    protected static SharedPreferences prefs;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        density = getApplicationContext().getResources().getDisplayMetrics().density;

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ViewGroup parent = (ViewGroup)findViewById(R.id.mondayInsertPoint);

        Course course1 = new Course();

        Course course2 = new Course();

        course1.setCode("CS235");
        course2.setCode("CS124");

        ArrayList<Map<String, Object>> course1Schedules = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapSchedule = new HashMap<>();
        List<String> array = new ArrayList<>();
        array.add("M");
        array.add("");
        array.add("W");
        array.add("");
        array.add("F");
        array.add("");
        mapSchedule.put("days", array);
        mapSchedule.put("start","9:00 AM");
        mapSchedule.put("end","10:00 AM");
        course1Schedules.add(mapSchedule);
        course1.setSchedules(course1Schedules);

        ArrayList<Map<String, Object>> course2Schedules = new ArrayList<Map<String, Object>>();
        Map<String, Object> c2MapSchedule = new HashMap<>();
        List<String> c2Array = new ArrayList<>();
        c2Array.add("");
        c2Array.add("T");
        c2Array.add("");
        c2Array.add("R");
        c2Array.add("");
        c2Array.add("");
        c2MapSchedule.put("days", c2Array);
        c2MapSchedule.put("start","12:45 PM");
        c2MapSchedule.put("end","1:45 PM");
        course2Schedules.add(c2MapSchedule);
        course2.setSchedules(course2Schedules);

        List<Course> courseList = new ArrayList<Course>();
        courseList.add(course1);
        courseList.add(course2);

        ViewGroup mondayParent = (ViewGroup)findViewById(R.id.mondayInsertPoint);
        ViewGroup tuesdayParent = (ViewGroup)findViewById(R.id.tuesdayInsertPoint);
        ViewGroup wednesdayParent = (ViewGroup)findViewById(R.id.wednesdayInsertPoint);
        ViewGroup thursdayParent = (ViewGroup)findViewById(R.id.thursdayInsertPoint);
        ViewGroup fridayParent = (ViewGroup)findViewById(R.id.fridayInsertPoint);

        for(int i = 0; i < courseList.size(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
            ArrayList<String> daysArray = new ArrayList<String>((ArrayList<String>)courseList.get(i).getSchedules().get(0).get("days"));
            textView.setText(courseList.get(i).getCode());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i == 0) {
                params.topMargin = getDP((String)(courseList.get(i).getSchedules().get(0).get("start")));
                textView.append("\n9:00 -\n10:00");
            }
            else {
                params.topMargin = getDP((String)(courseList.get(i).getSchedules().get(0).get("start")));
                textView.append("\n12:45 -\n1:45");
            }
            textView.setLayoutParams(params);
            textView.getLayoutParams().height = (int) (60 * density);
            if(daysArray.get(0).equals("M")) {
                TextView mondayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                mondayText.setText(textView.getText());
                mondayText.setLayoutParams(textView.getLayoutParams());
                mondayParent.addView(mondayText);
            }
            if(daysArray.get(1).equals("T")) {
                TextView tuesdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                tuesdayText.setText(textView.getText());
                tuesdayText.setLayoutParams(textView.getLayoutParams());
                tuesdayParent.addView(tuesdayText);
            }
            if(daysArray.get(2).equals("W")) {
                TextView wednesdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                wednesdayText.setText(textView.getText());
                wednesdayText.setLayoutParams(textView.getLayoutParams());
                wednesdayParent.addView(wednesdayText);
            }
            if(daysArray.get(3).equals("R")) {
                TextView thursdayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                thursdayText.setText(textView.getText());
                thursdayText.setLayoutParams(textView.getLayoutParams());
                thursdayParent.addView(thursdayText);
            }
            if(daysArray.get(4).equals("F")) {
                TextView fridayText = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
                fridayText.setText(textView.getText());
                fridayText.setLayoutParams(textView.getLayoutParams());
                fridayParent.addView(fridayText);
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
        int timeDP = 0;
        if(timeChars[1] == ':') {
            timeDP += (Character.getNumericValue(timeChars[0]) * 60);
            timeDP += Character.getNumericValue(timeChars[2]) + Character.getNumericValue(timeChars[3]);
        }
        else {
            timeDP += (((Character.getNumericValue(timeChars[0]) + 9) + Character.getNumericValue(timeChars[1])) * 60);
            timeDP += ((Character.getNumericValue(timeChars[3]) * 10) + Character.getNumericValue(timeChars[4]));
        }

        System.out.println(timeDP);

        return (int) (timeDP * density);
    }
}
