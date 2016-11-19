package com.example.nafil.shopping.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.nafil.shopping.infrastructer.ShoppingApplication;
import com.example.nafil.shopping.infrastructer.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Bus;

/**
 * Created by NAFIL on 11/15/2016.
 */
public class BaseActivity extends AppCompatActivity {

    // All Methods should be protected

    protected ShoppingApplication shoppingApplication;
    protected Bus bus;
    protected FirebaseAuth auth;
    protected FirebaseAuth.AuthStateListener authStateListener;
    protected String userName, userEmail; // All the Activities will have access to the user name and email. ** Location will be included As well when dealing with Locations
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting the Bus to allow data within all activities
        shoppingApplication = (ShoppingApplication) getApplication();
        bus = shoppingApplication.getBus();
        bus.register(this);

        // Fetching User Name and Email from Shared Preference
        sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
        userName = sharedPreferences.getString(Utils.USERNAME,"");
        userEmail = sharedPreferences.getString(Utils.EMAIL,"");

        // Checking if the user is already signed in
        auth = FirebaseAuth.getInstance();

        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity) || (this instanceof SplashAcitivty))){

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null){


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Utils.EMAIL,null).apply();
                        editor.putString(Utils.USERNAME,null).apply();
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);
                        finish();


                        //  startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                      //  finish();

                    }


                }
            };
            if (userEmail.equals("")){

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Utils.EMAIL,null).apply();
                editor.putString(Utils.USERNAME,null).apply();
                auth.signOut();
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
                finish();


            }


        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity) || (this instanceof SplashAcitivty))){


            auth.addAuthStateListener(authStateListener);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // to Unregister the data
        bus.unregister(this);

        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity) || (this instanceof SplashAcitivty))){


            auth.removeAuthStateListener(authStateListener);

        }
    }
}
