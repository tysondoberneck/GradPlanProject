package com.example.gradplanproject;

import java.util.ArrayList;

/**
 * This class will store all the data from the widgets in the course search activity. This way,
 * the filtering options a user selects can be stored and compared against the Courses in Firebase.
 */

public class WidgetDataStorage {
    private String courseCode;
    private String startTime;
    private String endTime;
    private String instructor;
    private boolean sectionFull;
    private boolean onlineOnly;
    private ArrayList<String> days;

    /**
     *
     * A Non-Default Constructor
     * @param courseCode A string to contain the course code or name.
     * @param startTime A string to contain the start time of a given course.
     * @param endTime   A string to contain the end time of a given course.
     * @param instructor A string to contain the name of the instructor of the course.
     * @param filterFull A boolean to indicate if the current course should be filtered
     *                   out in the case of it being full.
     *                   True - filter the course
     *                   False - don't filter the course
     * @param onlineOnly Similar to "filterFull" in that this variable determines whether Courses
     *                   will be filtered by their type ("DAY" or "ONLN")
     * @param days An ArrayList of strings to contain the days that the given course is offered.
     *             Right now it is being initialized to 5 (Monday - Friday) and if the course is offered
     *             on a particular day, for example Monday, then it would be represented by days[0] == "M"
     */

    public WidgetDataStorage(String courseCode, String startTime, String endTime,
                             String instructor, boolean filterFull, boolean onlineOnly, ArrayList<String> days) {
        this.courseCode = courseCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructor = instructor;
        this.sectionFull = filterFull;
        this.onlineOnly = onlineOnly;
        this.days = days;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public boolean isSectionFull() {
        return sectionFull;
    }

    public void setSectionFull(boolean sectionFull) {
        this.sectionFull = sectionFull;
    }

    public boolean isOnlineOnly() {
        return onlineOnly;
    }

    public void setOnlineOnly(boolean onlineOnly) {
        this.onlineOnly = onlineOnly;
    }

    public ArrayList<String> getDays() { return days; }

    public void setDays(ArrayList<String> days) { this.days = days; }
}
