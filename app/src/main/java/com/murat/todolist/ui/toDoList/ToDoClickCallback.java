package com.murat.todolist.ui.toDoList;

import com.murat.todolist.data.entity.ToDo;

public interface ToDoClickCallback {
    void onToDoClick(ToDo toDo);
    void onToDoDelete(ToDo toDo);
}
