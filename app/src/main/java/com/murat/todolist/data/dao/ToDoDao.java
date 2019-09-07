package com.murat.todolist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.murat.todolist.data.entity.ToDo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ToDoDao {

    /*@Insert
    Completable insertToDo(ToDo toDo);*/
    @Insert
    long insertToDo(ToDo toDo);

    @Delete
    Completable deleteToDo(ToDo toDo);

    @Query("SELECT * FROM todos WHERE userId = :userId")
    LiveData<List<ToDo>> getToDos(String userId);

    @Query("SELECT * FROM todos WHERE id = :id")
    Single<ToDo> getToDo(int id);
}
