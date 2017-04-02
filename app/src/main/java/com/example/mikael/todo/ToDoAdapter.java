package com.example.mikael.todo;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by mikael on 01/04/2017.
 */

public class ToDoAdapter extends RealmRecyclerViewAdapter<ToDoItem, ToDoAdapter.ToDoViewHolder> {

    private OrderedRealmCollection<ToDoItem> data;
    private MainActivity activity;

    public ToDoAdapter(MainActivity activity,OrderedRealmCollection<ToDoItem> data){
        super(data, true);
        this.activity = activity;
        this.data = data;

    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Defining the variables
        private TextView titleTextView;
        private Button doneButton;

        public ToDoViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.todo_item_title);
            doneButton = (Button) v.findViewById(R.id.doneBtn);
            doneButton.getBackground().setColorFilter(Color.rgb(129,199,132), PorterDuff.Mode.MULTIPLY);
            doneButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            removeToDo(getAdapterPosition());

        }
    }

    //Inflating the layout and returning the holder
    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo_item, parent, false);
        return new ToDoViewHolder(itemView);
    }

    //Populating data to the item via holder
    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        ToDoItem toDoItem = getData().get(position);
        holder.titleTextView.setText(toDoItem.getTodoTitle());
    }

    //Method for removing items from the database
    public void removeToDo(final int position){
        Realm realm = null;

        try{
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    //Checking whats in the database
                    RealmResults<ToDoItem> realmResults = realm.where(ToDoItem.class).findAll();
                    //Deleting item from the db
                    realmResults.deleteFromRealm(position);
                    notifyItemRemoved(position);
                    System.out.println(realmResults);
                }
            });
        } finally {

            if(realm != null){
                realm.close();
            }
        }
    }
}
