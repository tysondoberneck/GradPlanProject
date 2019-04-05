package com.example.gradplanproject;

import java.util.ArrayList;

/**
 * This class will store all the data from the widgets in the course search activity.
 */
public class WidgetDataStorage {
    private String courseCodeOrName;
    private String startTime;
    private String endTime;
    private String instructor;
    private boolean sectionFull;
    private ArrayList<String> days;

    /**
     *
     * A Non-Default Constructor
     * @param courseCodeOrName A string to contain the course code or name.
     *                         Right now the app only works with course codes.
     * @param startTime A string to contain the start time of a given course.
     * @param endTime   A string to contain the end time of a given course.
     * @param instructor A string to contain the name of the instructor of the course.
     * @param filterFull A boolean to indicate if the current course should be filtered
     *                   out in the case of it being full.
     *                   True - filter the course
     *                   False - don't filter the course
     * @param days An ArrayList of strings to contain the days that the given course is offered.
     *             Right now it is being inizialied to 5 (Monday - Friday) and if the course is offered
     *             on a particular day, for example Monday, then it would be represented by days[0] == "M"
     */
    public WidgetDataStorage(String courseCodeOrName, String startTime, String endTime, String instructor, boolean filterFull, ArrayList<String> days) {
        this.courseCodeOrName = courseCodeOrName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructor = instructor;
        this.sectionFull = filterFull;
        this.days = days;
    }

    public String getCourseCodeOrName() {
        return courseCodeOrName;
    }

    public void setCourseCodeOrName(String courseCodeOrName) {
        this.courseCodeOrName = courseCodeOrName;
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

    public ArrayList<String> getDays() { return days; }

    public void setDays(ArrayList<String> days) { this.days = days; }
}
