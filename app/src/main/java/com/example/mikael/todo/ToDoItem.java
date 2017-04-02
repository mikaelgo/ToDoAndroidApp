package com.example.mikael.todo;

import io.realm.RealmObject;

/**
 * Created by mikael on 31/03/2017.
 */

public class ToDoItem extends RealmObject {

    private String todoTitle;

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }


}
