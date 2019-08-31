package com.murat.todolist.ui.tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.murat.todolist.data.entity.Task;

public class TaskDiffUtil extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        /*return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getCreatedAt().equals(newItem.getCreatedAt()) &&
                oldItem.getDeadline().equals(newItem.getDeadline()) &&
                oldItem.getStatus().equals(newItem.getStatus());*/
        return oldItem.equals(newItem);
    }
}
