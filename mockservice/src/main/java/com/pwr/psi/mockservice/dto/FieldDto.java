package com.pwr.psi.mockservice.dto;

public class FieldDto {

    private String name;
    private String degree;
    private String educationCycle;
    private String faculty;

    public FieldDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEducationCycle() {
        return educationCycle;
    }

    public void setEducationCycle(String educationCycle) {
        this.educationCycle = educationCycle;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
