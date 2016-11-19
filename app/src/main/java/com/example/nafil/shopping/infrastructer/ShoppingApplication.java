package com.example.nafil.shopping.infrastructer;

import android.app.Application;

import com.example.nafil.shopping.live.Module;
import com.firebase.client.Firebase;
import com.squareup.otto.Bus;

/**
 * Created by NAFIL on 11/15/2016.
 */
public class ShoppingApplication extends Application {

    // Bus Event
    private Bus bus;

    public ShoppingApplication() {
        // Constructor for the event Bus
        bus = new Bus();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Module.Register(this);

    }


    // Getter for the Bus Event
    public Bus getBus() {
        return bus;
    }
}
