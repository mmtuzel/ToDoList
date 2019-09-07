package com.murat.todolist.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "todos")
public class ToDo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return id == toDo.id &&
                userId.equals(toDo.userId) &&
                name.equals(toDo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name);
    }
}
