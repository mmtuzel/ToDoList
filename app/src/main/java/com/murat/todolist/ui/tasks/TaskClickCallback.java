package com.murat.todolist.ui.tasks;

import com.murat.todolist.data.entity.Task;

public interface TaskClickCallback {
    void onTaskClick(Task task);
    void onTaskDeleteClick(Task task);
    void onTaskStatusChangeClick(Task task);
}
