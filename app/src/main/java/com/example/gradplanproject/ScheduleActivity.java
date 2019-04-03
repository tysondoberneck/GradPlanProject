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
        ViewGroup parent = (ViewGroup)findViewById(R.id.mondayInsertPoint);

        Course course1 = new Course();

        Course course2 = new Course();

        course1.setCode("CS235");
        course2.setCode("CS124");

        List<Course> courseList = new ArrayList<Course>();
        courseList.add(course1);
        courseList.add(course2);

        for(int i = 0; i < courseList.size(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.schedule_class_textview, null);
            parent.addView(textView);
            textView.setText(courseList.get(i).getCode());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i == 0) {
                params.topMargin = getDP("9:00 AM");
                textView.append("\n9:00 -\n10:00");
            }
            else {
                params.topMargin = getDP("12:45 PM");
                textView.append("\n12:45 -\n1:45");
            }
            textView.setLayoutParams(params);
            textView.getLayoutParams().height = (int) (60 * density);
        }
    }

    /**
     * Load's the String list of courses from SharedPreferences, then parses the list into a list
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
     * Converts a String value representing time (e.g. "9:00 AM" to an integer DP value (540)
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
