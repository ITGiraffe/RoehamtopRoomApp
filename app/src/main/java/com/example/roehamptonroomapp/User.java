package com.example.roehamptonroomapp;

public class User {

    private String first_name, last_name, student_ID, email, course_name, password, url;
    private Boolean reserved;

    public User(String first_name, String last_name, String student_ID, String email,
                String course_name, String password, String url, Boolean reserved) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.student_ID = student_ID;
        this.email = email;
        this.course_name = course_name;
        this.password = password;
        this.url = url;
        this.reserved = reserved;
    }

    public User()
    {
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}