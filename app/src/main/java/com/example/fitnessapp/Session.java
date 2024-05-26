package com.example.fitnessapp;

public class Session {
    private static int uid;
    private static String username;

    public Session (){

    }

    public Session(int uid, String username){
        this.uid = uid;
        this.username = username;
    }

    public static int getUid() {
        return uid;
    }

    public static void setUid(int uid) {
        Session.uid = uid;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }
}
