package com.example.mikael.todo;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    //Defining variables
    private FloatingActionButton fab;
    private Realm realm;
    private String Mikael;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();


        //Set Floating Action Button and set OnClickListener to it
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_white_24px);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB IS CLICKED");

                //Creating new custom dialog
                DialogFragment newFragment = new TodoDialog();
                newFragment.show(getFragmentManager(), null);
            }
        });
       setRV();
    }

    //Method to bind the adapter to the recyclerview
    public void setRV(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RealmResults<ToDoItem> toDoItems = realm.where(ToDoItem.class).findAllAsync();
        ToDoAdapter toDoAdapter = new ToDoAdapter(this, toDoItems);
        recyclerView.setAdapter(toDoAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Closing the realm connection
        realm.close();
    }
}
