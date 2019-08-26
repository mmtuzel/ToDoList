package com.murat.todolist.ui.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murat.todolist.data.model.Status;
import com.murat.todolist.data.model.Task;
import com.murat.todolist.data.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = repository.getTasks();
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public void completeTask(Task task) {
        if (task.getStatus() == Status.ACTIVE) {
            task.setStatus(Status.COMPLETED);
        } else if (task.getStatus() == Status.COMPLETED) {
            task.setStatus(Status.ACTIVE);
        }
        repository.updateTask(task);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }
}
