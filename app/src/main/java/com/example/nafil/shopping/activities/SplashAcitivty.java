package com.example.nafil.shopping.activities;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class SplashAcitivty extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
