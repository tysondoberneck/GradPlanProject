package com.example.gradplanproject;

import java.util.ArrayList;
import java.util.Map;

public class Course {

    private String code;
    private String course;
    private int credits;
    private ArrayList<Map<String, String>> instructors;
    private String name;
    private ArrayList<Map<String, Object>> schedules;
    private int seatsFilled;
    private int seatsTotal;
    private String section;
    private String status;
    private String type;


    // member variable used only for filler tests
    private String details;


    public Course() {
        code = "Code";
        course = "Course";
        credits = 3;
        // instructors = ???
        name = "Name";
        // schedules = ???
        seatsFilled = 0;
        seatsTotal = 100;
        section = "01";
        status = "Status";
        type = "Type";

        details = "NULL";
    }

    public Course(String name, String code, String details, int credits) {
        this.name = name;
        this.code = code;
        this.details = details;
        this.credits = credits;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }


    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }


    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }


    public ArrayList<Map<String, String>> getInstructors() { return instructors; }

    public void setInstructors(ArrayList<Map<String, String>> instructors) { this.instructors = instructors; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }
}
