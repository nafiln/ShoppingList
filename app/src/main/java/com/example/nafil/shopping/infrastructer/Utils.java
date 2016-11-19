package com.example.nafil.shopping.infrastructer;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class Utils {

    public static final String FIRE_BASE_DATABASE_URL = "https://shopping-8b2ba.firebaseio.com/";
    public static final String FIRE_BASE_USER_REFFERENCE = FIRE_BASE_DATABASE_URL + "users/";
    public static final String FIRE_BASE_SHOPPING_LIST_REFERENCE = FIRE_BASE_DATABASE_URL + "userShoppingList/";


    public static final String MY_PREFERENCE = "MY_SHARED_PREFERENCE";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";
    public static final String USER_LATITUDE = "USER_LATITUDE";
    public static final String USER_LONGITUDE = "USER_LONGITUDE";


    // Replacing ( . ) with ( , ) to be acceptable in the path of Firebase As registered User name
    public static String encodeEmail(String userEmail){
        return userEmail.replace("." , ",");

    }

    // Replacing ( , ) with ( . ) to save user Email in Firebase As registered User name
    public static String decodeEmail(String userEmail){
        return userEmail.replace("," , ".");

    }
}
