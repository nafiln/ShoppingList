package com.example.nafil.shopping.live;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.nafil.shopping.activities.LoginActivity;
import com.example.nafil.shopping.activities.MainActivity;
import com.example.nafil.shopping.entities.User;
import com.example.nafil.shopping.infrastructer.ShoppingApplication;
import com.example.nafil.shopping.infrastructer.Utils;
import com.example.nafil.shopping.services.AccountServices;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class LiveAccountServices extends BaseLiveService {
    public LiveAccountServices(ShoppingApplication shoppingApplication) {
        super(shoppingApplication);
    }

    @Subscribe
    public void RegisterUser(final AccountServices.RegisterUserRequest request){
        AccountServices.RegisterUserResponse respones = new AccountServices.RegisterUserResponse();

        // Checking E-Mail
        if (request.userEmail.isEmpty()){
            respones.setPropertyErrors("email","Please Enter you're Email.");


        }

        // Checking User Name
        if (request.userName.isEmpty()){
            respones.setPropertyErrors("userName", "Please Enter you're Name");


        }

        // When user is Registered with UserName and Email
        if (respones.didSucceed()){

            // Showing the Progress Dialog
            request.progressDialog.show();


            // Creating the User Name with FireBase Auth
            // Application will be generating Random Password then User will Change it

            SecureRandom secureRandom = new SecureRandom(); // Creating Random Secure Pattern
            final String randomPassword = new BigInteger(32,secureRandom).toString(); // Adding 32 bit integer to the Random Secure
            auth.createUserWithEmailAndPassword(request.userEmail,randomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            // If Register Fails Error MSG will be displayed to the user
                            if (!task.isSuccessful()) {

                                request.progressDialog.dismiss();
                                Toast.makeText(shoppingApplication.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            // If Register is Successful then Rest Password will be sent to the user to Change it base on there Email Address
                            else {
                                auth.sendPasswordResetEmail(request.userEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()){

                                                    // If Reset Password wasn't sent to the email which has been registered ( Bounce Back Mail or Block or any other issue with the same pattern )
                                                    request.progressDialog.dismiss();
                                                    Toast.makeText(shoppingApplication.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                                }


                                                // Success on attempting to send the password to the right email which is activated

                                                else {

                                                    // Creating User path with his own Email as a Key
                                                    Firebase reference = new Firebase(Utils.FIRE_BASE_USER_REFFERENCE + Utils.encodeEmail(request.userEmail));
                                                    HashMap<String,Object> timeJoined = new HashMap<>();
                                                    timeJoined.put("dateJoined", ServerValue.TIMESTAMP); // Firebase method to keep track on Time/Date of User Registration

                                                    reference.child("email").setValue(request.userEmail);
                                                    reference.child("name").setValue(request.userName);
                                                    reference.child("hasLoggedInWithPassword").setValue(false);
                                                    reference.child("timeJoined").setValue(timeJoined);

                                                   // Toast.makeText(shoppingApplication.getApplicationContext(),"Please Check you're Email for the Reset Password",Toast.LENGTH_LONG).show();
                                                    request.progressDialog.dismiss();

                                                    // Sending the user to Login page
                                                    Intent intent = new Intent(shoppingApplication.getApplicationContext(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    shoppingApplication.startActivity(intent);


                                                }

                                            }

                                        });

                            }
                        }
                    });


        }

        bus.post(respones);
    }




    @Subscribe
    public void LogInUser(final AccountServices.LogUserInRequest request){

        AccountServices.LogUserInResponse response = new AccountServices.LogUserInResponse();


        if (request.userEmail.isEmpty()){

            response.setPropertyErrors("email", "Please Enter you'er Email");

        }
         if(request.userPassword.isEmpty()){

            response.setPropertyErrors("password","Please Enter you're Password");
        }
         if(response.didSucceed()){

            request.progressDialog.show();
            auth.signInWithEmailAndPassword(request.userEmail,request.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){

                                request.progressDialog.dismiss();
                                Toast.makeText(shoppingApplication.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }

                            else {

                                final Firebase userLocation = new Firebase(Utils.FIRE_BASE_USER_REFFERENCE + Utils.encodeEmail(request.userEmail));
                                userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        User user = dataSnapshot.getValue(User.class);

                                        if (user != null){

                                            userLocation.child("hasLoggedInWithPassword").setValue(true);
                                            SharedPreferences sharedPreferences = request.sharedPreferences;
                                            sharedPreferences.edit().putString(Utils.EMAIL,Utils.encodeEmail(user.getEmail())).apply();
                                            sharedPreferences.edit().putString(Utils.USERNAME,user.getName()).apply();

                                            request.progressDialog.dismiss();

                                            // Sending the user to Main page
                                            Intent intent = new Intent(shoppingApplication.getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            shoppingApplication.startActivity(intent);

                                        }

                                        else {

                                            // Incase of Network Failer or Firebase Failer
                                            request.progressDialog.dismiss();
                                            Toast.makeText(shoppingApplication.getApplicationContext(),"Failed to Connect tot the Server. Kindly Try again", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                        request.progressDialog.dismiss();
                                        Toast.makeText(shoppingApplication.getApplicationContext(),firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });

                            }

                        }
                    });

        }

        bus.post(response);

    }

}
