package com.example.nafil.shopping.live;

import android.widget.Toast;

import com.example.nafil.shopping.entities.ShoppingList;
import com.example.nafil.shopping.infrastructer.ShoppingApplication;
import com.example.nafil.shopping.infrastructer.Utils;
import com.example.nafil.shopping.services.ShoppingListServices;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

/**
 * Created by NAFIL on 11/18/2016.
 */
public class LiveShoppingListServices extends BaseLiveService {


    public LiveShoppingListServices(ShoppingApplication shoppingApplication) {
        super(shoppingApplication);

    }

    @Subscribe
    public void AddingShoppingList(ShoppingListServices.AddShoppingListRequest request){

        ShoppingListServices.AddShoppingListResponse response = new ShoppingListServices.AddShoppingListResponse();


        if (request.shoppingListName.isEmpty()){

            response.setPropertyErrors("listName","Shopping List must have a name");
        }

        if (response.didSucceed()){


            Firebase reference = new Firebase(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + request.ownerEmail).push();
            HashMap<String,Object> timeStampedCreated = new HashMap<>();
            timeStampedCreated.put("timestamp", ServerValue.TIMESTAMP);
            ShoppingList shoppingList = new ShoppingList(reference.getKey(),request.shoppingListName
                        , Utils.decodeEmail(request.ownerEmail), request.ownerName,timeStampedCreated);

            reference.child("id").setValue(shoppingList.getId());
            reference.child("listName").setValue(shoppingList.getListName());
            reference.child("ownerEmail").setValue(shoppingList.getOwnerEmail());
            reference.child("ownerName").setValue(shoppingList.getOwnerName());
            reference.child("dateCreated").setValue(shoppingList.getDateCreated());
            reference.child("dateLastChange").setValue(shoppingList.getDateLastChange());


            Toast.makeText(shoppingApplication.getApplicationContext()," List has been Created",Toast.LENGTH_LONG).show();


        }

        bus.post(response);
    }



}

