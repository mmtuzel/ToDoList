package com.murat.todolist.ui.toDoList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.murat.todolist.data.entity.ToDo;

public class ToDoDiffUtil extends DiffUtil.ItemCallback<ToDo> {
    @Override
    public boolean areItemsTheSame(@NonNull ToDo oldItem, @NonNull ToDo newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ToDo oldItem, @NonNull ToDo newItem) {
        return oldItem.equals(newItem);
    }
}
