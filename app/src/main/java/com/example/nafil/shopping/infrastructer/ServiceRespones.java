package com.example.nafil.shopping.infrastructer;

import java.util.HashMap;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class ServiceRespones {

    // Keeping Track of any Error from User ( Client Side )

    private HashMap<String,String> propertyErrors;

    public ServiceRespones() {

        propertyErrors = new HashMap<>();
    }

    public void setPropertyErrors(String property, String error){

        propertyErrors.put(property,error);
    }


    public String getPropertyError(String property){

        return propertyErrors.get(property);

    }


    public boolean didSucceed(){

        // If property Error Equals 0 Then there is no Error Occurred from User Side
        return (propertyErrors.size() == 0);

    }


}
