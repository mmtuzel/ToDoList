package com.murat.todolist.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.murat.todolist.data.TaskDao;
import com.murat.todolist.data.TaskDatabase;
import com.murat.todolist.data.model.Task;

import java.util.List;

public class TaskRepository {
    private static final String TAG = "TaskRepository";

    private TaskDao taskDao;

    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getDatabase(application);
        taskDao = database.taskDao();
        tasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void insertTask(Task task) {
        new InsertTodoTask(taskDao).execute(task);
    }

    public void updateTask(Task task) {
        new UpdateTodoTask(taskDao).execute(task);
    }

    public void deleteTask(Task task) {
        new DeleteTodoTask(taskDao).execute(task);
    }

    public LiveData<Task> getTask(int id) {
        return taskDao.getTask(id);
    }

    private static class InsertTodoTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        InsertTodoTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insertTask(tasks[0]);
            return null;
        }
    }

    private static class UpdateTodoTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        UpdateTodoTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.updateTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteTodoTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        DeleteTodoTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }

}