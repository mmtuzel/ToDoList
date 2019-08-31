package com.murat.todolist.ui.addTask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.repository.TaskRepository;
import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;

import java.util.Date;

public class AddTaskViewModel extends AndroidViewModel {
    private TaskRepository repository;

    public MutableLiveData<String> taskTitle = new MutableLiveData<>();
    public MutableLiveData<String> taskDescription = new MutableLiveData<>();
    public MutableLiveData<Date> taskDeadlineDate = new MutableLiveData<>();

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        taskDeadlineDate.setValue(new Date());
    }

    private void insertTask(Task task) {
        repository.insertTask(task);
    }

    public void createTask() {
        String currentTaskTitle = taskTitle.getValue();
        String currentTaskDescription = taskDescription.getValue();
        Date deadline = taskDeadlineDate.getValue();

        Task task = new Task(currentTaskTitle, currentTaskDescription, new Date(), deadline, Status.ACTIVE);
        insertTask(task);
    }

    public MutableLiveData<Date> getTaskDeadlineDate() {
        return taskDeadlineDate;
    }
}
