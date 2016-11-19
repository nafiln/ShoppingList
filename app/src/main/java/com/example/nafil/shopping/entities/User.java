package com.example.nafil.shopping.entities;

import java.util.HashMap;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class User {

    private String email;
    private String name;
    private HashMap<String,Object> timeJoined;
    private boolean hasLoggedInWithPassword; // This to make sure his actual password was entered to log in not the random one




    // Constructor for Firebase always empty
    public User() {

    }


    // Application Constructor
    public User(String email, String name, HashMap<String, Object> dateJoined, boolean hasLoggedInWithPassword) {
        this.email = email;
        this.name = name;
        this.timeJoined = dateJoined;
        this.hasLoggedInWithPassword = hasLoggedInWithPassword;
    }


    // Getters

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Object> getTimeJoined() {
        return timeJoined;
    }

    public boolean isHasLoggedInWithPassword() {
        return hasLoggedInWithPassword;
    }
}
