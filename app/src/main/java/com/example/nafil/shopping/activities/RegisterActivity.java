package com.example.nafil.shopping.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nafil.shopping.R;
import com.example.nafil.shopping.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NAFIL on 11/15/2016.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.activity_register_register_btn)
    Button registerBTN;


    @BindView(R.id.activity_register_linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.activity_register_userName)
    EditText registerUserName;

    @BindView(R.id.activity_register_userEmail)
    EditText registerUserEmail;


    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //To Setup a Background all what need to do is
        linearLayout.setBackgroundResource(R.drawable.bbg);


        // this is for the Context
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading ....");
        mProgressDialog.setMessage("Attempting to register ... ");
        mProgressDialog.setCancelable(false);
    }

    @OnClick(R.id.activity_register_register_btn)
    public void setRegisterBTN(){

        bus.post(new AccountServices.RegisterUserRequest(registerUserName.getText().toString().trim(),registerUserEmail.getText().toString(),mProgressDialog));




    }


    @Subscribe
    public void RegisterUser(AccountServices.RegisterUserResponse respones){
        if (!respones.didSucceed()){

            registerUserEmail.setError(respones.getPropertyError("email"));
            registerUserName.setError(respones.getPropertyError("userName"));

        }

        /*
        else {

            // If Register is Succeed Send it To Login Page or Easier to Actual Main Page
            startActivity(new Intent(this,LoginActivity.class));
            finish();

        }
        */


    }


}
