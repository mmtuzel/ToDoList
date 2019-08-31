package com.murat.todolist.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.murat.todolist.data.ToDoDatabase;
import com.murat.todolist.data.dao.TaskDao;
import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;

import java.util.List;

public class TaskRepository {
    private static final String TAG = "TaskRepository";

    private TaskDao taskDao;

    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        ToDoDatabase database = ToDoDatabase.getDatabase(application);
        taskDao = database.taskDao();
        tasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public LiveData<List<Task>> getTasksByStatus(Status status) {
        return taskDao.getTasksByStatus(status);
    }

    public LiveData<List<Task>> getTasksByNameASC() {
        return taskDao.getTasksByNameASC();
    }

    public LiveData<List<Task>> getTasksByNameDESC() {
        return taskDao.getTasksByNameDESC();
    }

    public LiveData<List<Task>> getTaskByCreateDateASC() {
        return taskDao.getTasksByCreateDateASC();
    }

    public LiveData<List<Task>> getTaskByCreateDateDESC() {
        return taskDao.getTasksByCreateDateDESC();
    }

    public LiveData<List<Task>> getTaskByDeadlineDateASC() {
        return taskDao.getTasksByDeadlineDateASC();
    }

    public LiveData<List<Task>> getTaskByDeadlineDateDESC() {
        return taskDao.getTasksByDeadlineDateDESC();
    }

    public LiveData<List<Task>> getTasksByStatusOrderASC() {
        return taskDao.getTasksByStatusOrderASC();
    }

    public LiveData<List<Task>> getTasksByStatusOrderDESC() {
        return taskDao.getTasksByStatusOrderDESC();
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

    public void updateTaskStatus(Task task, Status status) {
        new UpdateTodoTaskStatus(taskDao, status).execute(task);
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

    private static class UpdateTodoTaskStatus extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;
        private com.murat.todolist.data.entity.Status status;

        UpdateTodoTaskStatus(TaskDao taskDao, com.murat.todolist.data.entity.Status status) {
            this.taskDao = taskDao;
            this.status = status;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.updateTaskStatus(tasks[0].getId(), status);
            return null;
        }
    }

}
