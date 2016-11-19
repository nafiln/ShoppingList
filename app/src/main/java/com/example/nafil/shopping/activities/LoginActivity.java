package com.example.nafil.shopping.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nafil.shopping.R;
import com.example.nafil.shopping.infrastructer.Utils;
import com.example.nafil.shopping.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NAFIL on 11/15/2016.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.activity_login_linear_layout)
    LinearLayout linearLayout;


    @BindView(R.id.activity_login_signUp_btn)
    Button signUpBTN;

    @BindView(R.id.activity_login_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_login_userPassword)
    EditText userPassword;

    @BindView(R.id.activity_login_login_btn)
    Button loginBTN;


    private ProgressDialog mProgressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //To Setup a Background all what need to do is
        linearLayout.setBackgroundResource(R.drawable.bbbg);
        // This Line incase we needed to make user sign in each time (He/She) uses the app
        //  FirebaseAuth.getInstance().signOut();

        // this is for the Context
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Logging in ....");
        mProgressDialog.setMessage("Attempting to Logging ... ");
        mProgressDialog.setCancelable(false);


        // for the shared Preference
        sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);


    }


    // OnClick Method with Butter Knife
    @OnClick(R.id.activity_login_signUp_btn)
    protected void setSignUpBTN(){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();


    }


    @OnClick(R.id.activity_login_login_btn)
    protected void setLoginBTN(){

        bus.post(new AccountServices.LogUserInRequest(userEmail.getText().toString(),userPassword.getText().toString(),mProgressDialog,sharedPreferences));

    }

    @Subscribe
    public void LogUserIn(AccountServices.LogUserInResponse response){
        if (!response.didSucceed()){
            userEmail.setError(response.getPropertyError("email"));
            userPassword.setError(response.getPropertyError("password"));


        }

    }
}
