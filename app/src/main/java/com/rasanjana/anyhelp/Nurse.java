package com.rasanjana.anyhelp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Nurse {
    //private String BabyCare,WoundDressing,FirstAid,ElderlyCare,VigilantObservations,Injections;
    private String Name;
    private String Gender;
    private String Location;
    private String AvailableTime;
    private String  Qualifications;
    private String Description;
    private List<String> serviceCategories = new ArrayList<>();
    private List<Appoinment> appoinments = new ArrayList<>();

    @Exclude
    private String key;
    @Exclude
    private String profession;


    public Nurse() {
    }
    public List<String> getServiceCategories() {
        return serviceCategories;
    }

    public void setServiceCategories(List<String> serviceCategories) {
        this.serviceCategories = serviceCategories;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    //    public String getBabyCare() {

//        return BabyCare;
//    }
//
//    public void setBabyCare(String babyCare) {
//        BabyCare = babyCare;
//    }
//
//    public String getWoundDressing() {
//        return WoundDressing;
//    }
//
//    public void setWoundDressing(String woundDressing) {
//        WoundDressing = woundDressing;
//    }
//
//    public String getFirstAid() {
//        return FirstAid;
//    }
//
//    public void setFirstAid(String firstAid) {
//        FirstAid = firstAid;
//    }
//
//    public String getElderlyCare() {
//        return ElderlyCare;
//    }
//
//    public void setElderlyCare(String elderlyCare) {
//        ElderlyCare = elderlyCare;
//    }
//
//    public String getVigilantObservations() {
//        return VigilantObservations;
//    }
//
//    public void setVigilantObservations(String vigilantObservations) {
//        VigilantObservations = vigilantObservations;
//    }
//
//    public String getInjections() {
//        return Injections;
//    }
//
//    public void setInjections(String injections) {
//        Injections = injections;
//    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAvailableTime() {
        return AvailableTime;
    }

    public void setAvailableTime(String availableTime) {
        AvailableTime = availableTime;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Appoinment> getAppoinments() {
        return appoinments;
    }

    public void setAppoinments(List<Appoinment> appoinments) {
        this.appoinments = appoinments;
    }
}