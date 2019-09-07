package com.murat.todolist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface TaskDao {

    @Insert
    Completable insertTask(Task task);

    @Update
    Completable updateTask(Task task);

    @Delete
    Completable deleteTask(Task task);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId")
    LiveData<List<Task>> getTasks(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId AND status = :status")
    LiveData<List<Task>> getTasksByStatus(int toDoId, Status status);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY title ASC")
    LiveData<List<Task>> getTasksByNameASC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY title DESC")
    LiveData<List<Task>> getTasksByNameDESC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY createdAt ASC")
    LiveData<List<Task>> getTasksByCreateDateASC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY createdAt DESC")
    LiveData<List<Task>> getTasksByCreateDateDESC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY deadline ASC")
    LiveData<List<Task>> getTasksByDeadlineDateASC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY deadline DESC")
    LiveData<List<Task>> getTasksByDeadlineDateDESC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY status ASC")
    LiveData<List<Task>> getTasksByStatusOrderASC(int toDoId);

    @Query("SELECT * FROM tasks WHERE toDoId = :toDoId ORDER BY status DESC")
    LiveData<List<Task>> getTasksByStatusOrderDESC(int toDoId);

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<Task> getTask(int id);

    @Query("DELETE FROM tasks WHERE toDoId = :toDoId")
    Completable deleteTasksByToDoId(int toDoId);

    @Query("UPDATE tasks SET status = :status WHERE id = :id")
    Completable updateTaskStatus(int id, Status status);

}
