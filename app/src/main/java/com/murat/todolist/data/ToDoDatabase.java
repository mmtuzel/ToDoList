package com.murat.todolist.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.murat.todolist.data.dao.TaskDao;
import com.murat.todolist.data.entity.Task;
import com.murat.todolist.data.entity.converter.DateConverter;
import com.murat.todolist.data.entity.converter.StatusConverter;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, StatusConverter.class})
public abstract class ToDoDatabase extends RoomDatabase {

    private static ToDoDatabase INSTANCE;

    public abstract TaskDao taskDao();

    public static ToDoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ToDoDatabase.class,
                            "todo.db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
