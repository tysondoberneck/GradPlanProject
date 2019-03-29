package com.example.gradplanproject;

public class Course {
    private String name;
    private String code;
    private String details;
    private int credits;

    public Course() {
        name = "";
        code = "";
        details = "";
        credits = 0;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Course(String name, String code, String details, int credits) {
        this.name = name;
        this.code = code;
        this.details = details;
        this.credits = credits;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }
}
