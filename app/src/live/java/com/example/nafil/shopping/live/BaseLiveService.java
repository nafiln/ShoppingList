package com.example.nafil.shopping.live;

import com.example.nafil.shopping.infrastructer.ShoppingApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class BaseLiveService {

    protected Bus bus;
    protected ShoppingApplication shoppingApplication;
    protected FirebaseAuth auth;

    public BaseLiveService(ShoppingApplication shoppingApplication) {
        this.shoppingApplication = shoppingApplication;
        bus = shoppingApplication.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance();
    }
}
