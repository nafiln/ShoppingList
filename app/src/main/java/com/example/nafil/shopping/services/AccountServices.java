package com.example.nafil.shopping.services;

import android.app.ProgressDialog;
import android.content.SharedPreferences;

import com.example.nafil.shopping.infrastructer.ServiceRespones;

/**
 * Created by NAFIL on 11/16/2016.
 */


// Classes for Communication regarding the request and respones of the buses
public class AccountServices {


    private AccountServices() {

    }


    // Class for the User Request
    public static class RegisterUserRequest{

        public String userName;
        public String userEmail;
        public ProgressDialog progressDialog;

        public RegisterUserRequest(String userName, String userEmail, ProgressDialog progressDialog) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.progressDialog = progressDialog;
        }
    }


    // Class for User Registered Response
    public static class RegisterUserResponse extends ServiceRespones{


    }


    public static class LogUserInRequest{

        public String userEmail;
        public String userPassword;
        public ProgressDialog progressDialog;
        public SharedPreferences sharedPreferences;

        public LogUserInRequest(String userEmail, String userPassword, ProgressDialog progressDialog, SharedPreferences sharedPreferences) {
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.progressDialog = progressDialog;
            this.sharedPreferences = sharedPreferences;
        }
    }


    // Class for User Logged in Response
    public static class LogUserInResponse extends ServiceRespones{


    }

}
