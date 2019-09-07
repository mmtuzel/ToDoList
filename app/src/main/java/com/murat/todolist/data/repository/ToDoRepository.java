package com.murat.todolist.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.murat.todolist.data.ToDoDatabase;
import com.murat.todolist.data.dao.ToDoDao;
import com.murat.todolist.data.entity.ToDo;

import java.util.List;

import io.reactivex.Completable;

public class ToDoRepository {
    private ToDoDao toDoDao;

    public ToDoRepository(Application application) {
        ToDoDatabase database = ToDoDatabase.getDatabase(application);
        toDoDao = database.toDoDao();
    }

    public LiveData<List<ToDo>> getToDoList(String userId) {
        return toDoDao.getToDos(userId);
    }

    public long insertToDo(ToDo toDo) {
        return toDoDao.insertToDo(toDo);
    }

    public Completable deleteToDo(ToDo toDo) {
        return toDoDao.deleteToDo(toDo);
    }
}
