package com.murat.todolist.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.murat.todolist.data.ToDoDatabase;
import com.murat.todolist.data.dao.TaskDao;
import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;

import java.util.List;

import io.reactivex.Completable;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(Application application) {
        ToDoDatabase database = ToDoDatabase.getDatabase(application);
        taskDao = database.taskDao();
    }

    public LiveData<List<Task>> getTasks(int toDoId) {
        return taskDao.getTasks(toDoId);
    }

    public LiveData<List<Task>> getTasksByStatus(int toDoId, Status status) {
        return taskDao.getTasksByStatus(toDoId, status);
    }

    public LiveData<List<Task>> getTasksByNameASC(int toDoId) {
        return taskDao.getTasksByNameASC(toDoId);
    }

    public LiveData<List<Task>> getTasksByNameDESC(int toDoId) {
        return taskDao.getTasksByNameDESC(toDoId);
    }

    public LiveData<List<Task>> getTasksByCreateDateASC(int toDoId) {
        return taskDao.getTasksByCreateDateASC(toDoId);
    }

    public LiveData<List<Task>> getTasksByCreateDateDESC(int toDoId) {
        return taskDao.getTasksByCreateDateDESC(toDoId);
    }

    public LiveData<List<Task>> getTasksByDeadlineDateASC(int toDoId) {
        return taskDao.getTasksByDeadlineDateASC(toDoId);
    }

    public LiveData<List<Task>> getTasksByDeadlineDateDESC(int toDoId) {
        return taskDao.getTasksByDeadlineDateDESC(toDoId);
    }

    public LiveData<List<Task>> getTasksByStatusOrderASC(int toDoId) {
        return taskDao.getTasksByStatusOrderASC(toDoId);
    }

    public LiveData<List<Task>> getTasksByStatusOrderDESC(int toDoId) {
        return taskDao.getTasksByStatusOrderDESC(toDoId);
    }

    public Completable insertTask(Task task) {
        return taskDao.insertTask(task);
    }

    public Completable updateTask(Task task) {
        return taskDao.updateTask(task);
    }

    public Completable deleteTask(Task task) {
        return taskDao.deleteTask(task);
    }

    public LiveData<Task> getTask(int id) {
        return taskDao.getTask(id);
    }

    public Completable updateTaskStatus(int taskId, Status status) {
        return taskDao.updateTaskStatus(taskId, status);
    }

    public Completable deleteTasksByToDoId(int toDoId) {
        return taskDao.deleteTasksByToDoId(toDoId);
    }
}
