package com.example.mikael.todo;

import io.realm.RealmObject;

/**
 * Created by mikael on 31/03/2017.
 */

public class ToDoItem extends RealmObject {

    //Defining the varible
    private String todoTitle;

    //Generating the getters and setters
    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }


}
