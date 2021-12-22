package com.example.myapplication.Models;


public class User {
    private String fname;
    private String sname;
    private String password;
    private String email;
    private String phone;
    private String temperature;

    public User() {

    }

    public User(String fname, String sname, String password, String email, String phone, String temperature) {
        this.fname = fname;
        this.sname = sname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.temperature = temperature;
    }

    public String getTemperature() {return temperature;}

    public void setTemperature(String temperature) {this.temperature = temperature;}

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
