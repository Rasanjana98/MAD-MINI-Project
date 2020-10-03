package com.rasanjana.anyhelp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Mechanic {
    private String name;
    private String availableTime;
    private String Location;
    private String Time;
    private String Qualifications;
    private String Description;
    private List<String> Fields = new ArrayList<>();
    private List<Appoinment> appoinments = new ArrayList<>();

    @Exclude
    private String key;
    @Exclude
    private String profession;

    public Mechanic() {
    }

    public List<String> getFields() {
        return Fields;
    }

    public void setFields(List<String> fields) {
        Fields = fields;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<Appoinment> getAppoinments() {
        return appoinments;
    }

    public void setAppoinments(List<Appoinment> appoinments) {
        this.appoinments = appoinments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }
}
