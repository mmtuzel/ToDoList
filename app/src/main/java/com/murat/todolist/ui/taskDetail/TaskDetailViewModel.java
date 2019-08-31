package com.murat.todolist.ui.taskDetail;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.repository.TaskRepository;
import com.murat.todolist.data.entity.Task;

import java.util.Date;

public class TaskDetailViewModel extends AndroidViewModel {
    private static final String TAG = "TaskDetailViewModel";
    private TaskRepository repository;

    private LiveData<Task> task;
    private Task editableTask;
    //public ObservableField<Task> editedTask = new ObservableField<>();
    public MutableLiveData<String> taskTitle = new MutableLiveData<>();
    public MutableLiveData<String> taskDescription = new MutableLiveData<>();
    public MutableLiveData<Date> taskDeadlineDate = new MutableLiveData<>();

    public TaskDetailViewModel(@NonNull Application application, int taskId) {
        super(application);
        repository = new TaskRepository(application);
        task = repository.getTask(taskId);
        taskDeadlineDate.setValue(null);
    }

    public LiveData<Task> getTask() {
        return task;
    }

    public void onTaskLoaded(Task task) {
        editableTask = task;
        Log.d(TAG, "onTaskLoaded title: " + task.getTitle());
        Log.d(TAG, "onTaskLoaded description: " + task.getDescription());
        taskTitle.setValue(task.getTitle());
        taskDescription.setValue(task.getDescription());
        taskDeadlineDate.setValue(task.getDeadline());
        Log.d(TAG, "after title: " + taskTitle.getValue());
        Log.d(TAG, "after desc: " + taskDescription.getValue());
    }

    public void updateTask() {
        //Task task = getEditedTask();
        editableTask.setTitle(taskTitle.getValue());
        editableTask.setDescription(taskDescription.getValue());
        editableTask.setDeadline(taskDeadlineDate.getValue());
        Log.d(TAG, "updateTask title: " + editableTask.getTitle());
        Log.d(TAG, "updateTask desc: " + editableTask.getDescription());
        repository.updateTask(editableTask);
    }

    /*public void setEditedTask(Task task) {
        editedTask.set(task);
    }

    public Task getEditedTask() {
        return editedTask.get();
    }*/

}
