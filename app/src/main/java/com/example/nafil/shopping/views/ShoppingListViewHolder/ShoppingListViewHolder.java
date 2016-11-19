package com.example.nafil.shopping.views.ShoppingListViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nafil.shopping.R;
import com.example.nafil.shopping.entities.ShoppingList;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NAFIL on 11/19/2016.
 */
public class ShoppingListViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.list_shopping_list_listName)
    TextView listName;

    @BindView(R.id.list_shopping_list_own_name)
    TextView ownerName;

    @BindView(R.id.list_shopping_list_created_at)
    TextView createdAt;

    @BindView(R.id.list_shopping_list_lay)
   public View layout;



    public ShoppingListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }




    public void populate(ShoppingList shoppingList){


        listName.setText(shoppingList.getListName());
        ownerName.setText(shoppingList.getOwnerName());

        if (shoppingList.getDateCreated().get("timestamp") != null){

       //     createdAt.setText(convertTime((long)shoppingList.getDateCreated().get("timestamp")));

            createdAt.setText(shoppingList.getDateCreated().get("timestamp").toString());

        }

    }



    private String convertTime(Long unixTime){


        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy hh:mm");
        return simpleDateFormat.format(dateObject);

    }
}
