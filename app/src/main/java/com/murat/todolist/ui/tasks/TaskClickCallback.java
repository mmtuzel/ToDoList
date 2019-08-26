package com.murat.todolist.ui.tasks;

import com.murat.todolist.data.model.Task;

public interface TaskClickCallback {
    void onTaskClick(Task task);
    void onTaskDeleteClick(Task task);
    void onTaskCompleteClick(Task task);
}
