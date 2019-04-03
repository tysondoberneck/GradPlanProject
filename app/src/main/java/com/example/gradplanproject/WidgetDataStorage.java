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
