package com.murat.todolist.ui.toDoList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.todolist.R;
import com.murat.todolist.data.entity.ToDo;
import com.murat.todolist.databinding.ItemToDoBinding;

public class ToDoListAdapter extends ListAdapter<ToDo, ToDoListAdapter.ToDoViewHolder> {
    private ToDoClickCallback clickCallback;

    protected ToDoListAdapter(@NonNull DiffUtil.ItemCallback<ToDo> diffCallback, ToDoClickCallback clickCallback) {
        super(diffCallback);
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemToDoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_to_do, parent, false
        );
        return new ToDoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.binding.setToDo(getItem(position));
        holder.binding.setClickCallback(clickCallback);
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        public ItemToDoBinding binding;

        public ToDoViewHolder(@NonNull ItemToDoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
