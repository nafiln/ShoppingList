package com.example.nafil.shopping.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.nafil.shopping.infrastructer.ShoppingApplication;
import com.example.nafil.shopping.infrastructer.Utils;
import com.squareup.otto.Bus;

/**
 * Created by NAFIL on 11/15/2016.
 */
public class BaseDialog extends DialogFragment {


    // Extending Dialog to the main application which is ShoppingApplication

    protected ShoppingApplication shoppingApplication;
    protected Bus bus;
    protected String userName, userEmail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingApplication = (ShoppingApplication) getActivity().getApplication(); // Because of the fragment view we use getActivity then getApplication
        bus = shoppingApplication.getBus();
        bus.register(this);
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE,Context.MODE_PRIVATE).getString(Utils.USERNAME,"");


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}



