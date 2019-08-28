package com.murat.todolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.murat.todolist.data.model.Status;
import com.murat.todolist.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM tasks WHERE status = :status")
    LiveData<List<Task>> getTasksByStatus(Status status);

    @Query("SELECT * FROM tasks ORDER BY title ASC")
    LiveData<List<Task>> getTasksByNameASC();

    @Query("SELECT * FROM tasks ORDER BY title DESC")
    LiveData<List<Task>> getTasksByNameDESC();

    @Query("SELECT * FROM tasks ORDER BY createdAt ASC")
    LiveData<List<Task>> getTasksByCreateDateASC();

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    LiveData<List<Task>> getTasksByCreateDateDESC();

    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    LiveData<List<Task>> getTasksByDeadlineDateASC();

    @Query("SELECT * FROM tasks ORDER BY deadline DESC")
    LiveData<List<Task>> getTasksByDeadlineDateDESC();

    @Query("SELECT * FROM tasks ORDER BY status ASC")
    LiveData<List<Task>> getTasksByStatusOrderASC();

    @Query("SELECT * FROM tasks ORDER BY status DESC")
    LiveData<List<Task>> getTasksByStatusOrderDESC();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<Task> getTask(int id);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteTaskWithId(int id);

}
