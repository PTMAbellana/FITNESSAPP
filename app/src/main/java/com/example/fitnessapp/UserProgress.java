package com.example.fitnessapp;

// Might Delete this class since it's not used
public class UserProgress {
    private String name;
    private double bmi;
    private double height;
    private double weight;
    private String currentDifficulty;
    private String targetDifficulty;
    private int day;
    private int week;

    public UserProgress(String name, double bmi, double height, double weight, String currentDifficulty, String targetDifficulty, int day, int week) {
        this.name = name;
        this.bmi = bmi;
        this.height = height;
        this.weight = weight;
        this.currentDifficulty = currentDifficulty;
        this.targetDifficulty = targetDifficulty;
        this.day = day;
        this.week = week;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCurrentDifficulty(String currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public String getTargetDifficulty() {
        return targetDifficulty;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
