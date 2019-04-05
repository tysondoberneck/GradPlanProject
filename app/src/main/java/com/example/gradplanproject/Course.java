package com.example.gradplanproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class designed to represent a BYU Idaho Course as stored in FireBase
 */
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
    private List<String> singleDaysArray;

    // member variable used only for filler tests
    private String details;

    /**
     * This is a default constructor for the course class.
     * All the data that is initialized is just filler data.
     * For example, we want to initialize all the values in the arrays so that we won't
     * get cases where we are trying to dereference a NULL.
     */
    public Course() {

        code = "Code";
        course = "Course";
        credits = 3;

        instructors = new ArrayList<Map<String, String>>();
        Map<String, String> mapInstructor = new HashMap<>();
        mapInstructor.put("first", "LastName");
        mapInstructor.put("middle", "MiddleName");
        mapInstructor.put("last", "FirstName");
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
        mapSchedule.put("time", "0:00 - 0:00 PM");
        mapSchedule.put("start", "0:00 PM");
        mapSchedule.put("end", "0:00 PM");
        mapSchedule.put("location", "BYUI");
        mapSchedule.put("method", "Method");
        schedules.add(mapSchedule);

        singleDaysArray = new ArrayList<>();

        seatsFilled = 0;
        seatsTotal = 100;
        section = "XX";
        status = "Status";
        type = "Type";

        details = "NULL";
    }

    /**
     * Some courses contain multiple arrays for days. This function will form any extra day arrays into a single array.
     * @return A single array containing Strings for every day this course takes place.
     */
    public List<String> getSingleDaysArray() {
        if(this.getSchedules().size() == 1) {
            singleDaysArray = (ArrayList<String>)this.getSchedules().get(0).get("days");
        }
        if(this.getSchedules().size() == 2) {
            singleDaysArray = (ArrayList<String>)this.getSchedules().get(0).get("days");
            List<String> tempArray = (ArrayList<String>)this.getSchedules().get(1).get("days");
            for(int i = 0; i < singleDaysArray.size(); i++) {
                if(singleDaysArray.get(i).isEmpty() && !tempArray.get(i).isEmpty()) {
                    singleDaysArray.set(i, (tempArray.get(i)));
                }
            }
        }
        if(this.getSchedules().size() == 3) {
            singleDaysArray = (ArrayList<String>)this.getSchedules().get(0).get("days");
            List<String> tempArray1 = (ArrayList<String>)this.getSchedules().get(1).get("days");
            List<String> tempArray2 = (ArrayList<String>)this.getSchedules().get(2).get("days");
            for(int i = 0; i < singleDaysArray.size(); i++) {
                if(singleDaysArray.get(i).isEmpty() && !tempArray1.get(i).isEmpty()) {
                    singleDaysArray.set(i, (tempArray1.get(i)));
                }
                if(singleDaysArray.get(i).isEmpty() && !tempArray2.get(i).isEmpty()) {
                    singleDaysArray.set(i, (tempArray2.get(i)));
                }
            }
        }
        return singleDaysArray;
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
