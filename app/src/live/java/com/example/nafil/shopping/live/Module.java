package com.example.nafil.shopping.live;

import com.example.nafil.shopping.infrastructer.ShoppingApplication;

/**
 * Created by NAFIL on 11/16/2016.
 */
public class Module {
    public static void Register(ShoppingApplication shoppingApplication){

        new LiveAccountServices(shoppingApplication);
        new LiveShoppingListServices(shoppingApplication);


    }

}
