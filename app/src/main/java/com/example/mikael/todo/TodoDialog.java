package com.example.mikael.todo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mikael on 31/03/2017.
 */

public class TodoDialog extends DialogFragment {

    //Defining variables
    private EditText todoTitle;
    private Button todoAddBtn, todoCancelBtn;
    private Realm realm;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Initializing Realm
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();

        final AlertDialog.Builder todoBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflate and set the layout for the dialog
        View todoView = inflater.inflate(R.layout.addtodo_dialog, null);

        todoBuilder.setView(todoView);

        todoTitle = (EditText) todoView.findViewById(R.id.etTitle);
        todoAddBtn = (Button) todoView.findViewById(R.id.btnAdd);
        todoAddBtn.getBackground().setColorFilter(Color.rgb(129,199,132), PorterDuff.Mode.MULTIPLY);
        todoCancelBtn = (Button) todoView.findViewById(R.id.btnCancel);
        todoCancelBtn.getBackground().setColorFilter(Color.rgb(255,183,77), PorterDuff.Mode.MULTIPLY);

        todoAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("ADD CLICKED");
                addToDoItem(todoTitle.getText().toString());
            }
        });

        todoCancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("CANCEL CLICKED");
                TodoDialog.this.getDialog().cancel();
            }
        });

       return todoBuilder.create();
    }

    //Method for add items to realm db
    private void addToDoItem(String toDoText) {

        //Notify if trying to add empty text field
        if (toDoText == null || toDoText.length() == 0) {
            Toast.makeText(getContext(), "You cannot add empty ToDos!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String title = todoTitle.getText().toString();

        //Realm transaction
        realm.beginTransaction();
        ToDoItem toDoItem = realm.createObject(ToDoItem.class);
        //Adding the task to the database
        toDoItem.setTodoTitle(title);
        realm.commitTransaction();
        //Check whats in the realm database
        RealmResults<ToDoItem> realmResults = realm.where(ToDoItem.class).findAll();
        System.out.println(realmResults);
        TodoDialog.this.getDialog().cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Closing the realm connection
        realm.close();
    }
}
