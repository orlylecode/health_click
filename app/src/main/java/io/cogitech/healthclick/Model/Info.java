package io.cogitech.healthclick.Model;

import java.util.ArrayList;

public class Info {
    private String gender;
    private Integer year_of_birth;
    private String language;
    private ArrayList<Integer> symptoms;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(Integer year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<Integer> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<Integer> symptoms) {
        this.symptoms = symptoms;
    }
}