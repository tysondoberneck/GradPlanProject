package com.example.gradplanproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {

    private String code;
    private String course;
    private float credits;
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

        instructors = new ArrayList<Map<String, String>>();
        Map<String, String> mapInstructor = new HashMap<>();
        mapInstructor.put("first", "Instructor");
        instructors.add(mapInstructor);

        name = "Name";

        schedules = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapSchedule = new HashMap<>();
        List<String> array = new ArrayList<>();
        array.add("D");
        array.add("A");
        array.add("Y");
        array.add("S");
        array.add("");
        array.add("");
        mapSchedule.put("days", array);
        mapSchedule.put("time", "12:00 - 12:00 PM");
        schedules.add(mapSchedule);

        seatsFilled = 0;
        seatsTotal = 100;
        section = "XX";
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


    public float getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }


    public ArrayList<Map<String, String>> getInstructors() { return instructors; }

    public void setInstructors(ArrayList<Map<String, String>> instructors) { this.instructors = instructors; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


    public ArrayList<Map<String, Object>> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Map<String, Object>> schedules) {
        this.schedules = schedules;
    }


    public int getSeatsFilled() {
        return seatsFilled;
    }

    public void setSeatsFilled(int seatsFilled) {
        this.seatsFilled = seatsFilled;
    }


    public int getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(int seatsTotal) {
        this.seatsTotal = seatsTotal;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }
}
