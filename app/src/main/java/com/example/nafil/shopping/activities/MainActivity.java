package com.example.nafil.shopping.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nafil.shopping.R;
import com.example.nafil.shopping.dialog.AddListDialogFragment;
import com.example.nafil.shopping.entities.ShoppingList;
import com.example.nafil.shopping.infrastructer.Utils;
import com.example.nafil.shopping.views.ShoppingListViewHolder.ShoppingListViewHolder;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_FAB)
    FloatingActionButton floatingActionButton;


    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;


    // Extending all activies to be from the Base Acitivity which was created in the same package



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_list_recycler_view);

        String toolBarName;

        if (userName.contains(" ")){

            toolBarName = userName.substring(0,userName.indexOf(" ")) + "'s Shopping List";
        }
        else {

            toolBarName = userName +"'s Shopping List";
        }

        getSupportActionBar().setTitle(toolBarName);

    }



    // FireBase RecyclerView
    @Override
    protected void onResume() {
        super.onResume();

        Firebase shoppingListReference = new Firebase(Utils.FIRE_BASE_SHOPPING_LIST_REFERENCE + userEmail);

        adapter = new FirebaseRecyclerAdapter<ShoppingList,ShoppingListViewHolder>(ShoppingList.class,R.layout.list_shopping_list, ShoppingListViewHolder.class,shoppingListReference) {
            @Override
            protected void populateViewHolder(ShoppingListViewHolder viewHolder, ShoppingList model, int position) {

            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       switch (item.getItemId()){

           case R.id.action_logout:



               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(Utils.EMAIL,null).apply();
               editor.putString(Utils.USERNAME,null).apply();
               auth.signOut();
               Intent intent = new Intent(getApplication(), LoginActivity.class);
               startActivity(intent);
               finish();

               return true;
       }



        return super.onOptionsItemSelected(item);
    }




    @OnClick(R.id.activity_main_FAB)
    public void setFloatingActionButton(){

        DialogFragment dialogFragment = AddListDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(),AddListDialogFragment.class.getSimpleName());


    }


}
