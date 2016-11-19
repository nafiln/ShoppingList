package com.example.nafil.shopping.services;

import com.example.nafil.shopping.infrastructer.ServiceRespones;

/**
 * Created by NAFIL on 11/18/2016.
 */
public class ShoppingListServices {

    private ShoppingListServices() {
    }


    public static class AddShoppingListRequest {

        public String shoppingListName;
        public String ownerName;
        public String ownerEmail;


        public AddShoppingListRequest(String shoppingListName, String ownerName, String ownerEmail) {
            this.shoppingListName = shoppingListName;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
        }
    }


    public static class AddShoppingListResponse extends ServiceRespones{


    }
}
