package com.murat.todolist.ui.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.todolist.R;
import com.murat.todolist.data.entity.Task;
import com.murat.todolist.databinding.ItemTaskBinding;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> implements Filterable {

    private List<Task> tasks;
    private TaskClickCallback clickCallback;

    protected TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, TaskClickCallback clickCallback) {
        super(diffCallback);
        this.clickCallback = clickCallback;
    }


    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_task, parent, false
        );
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.binding.setTask(getItem(position));
        holder.binding.setClickCallback(clickCallback);
    }

    @Override
    public Filter getFilter() {
        return taskFilterByName;
    }

    private Filter taskFilterByName = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filteredTasks = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredTasks.addAll(tasks);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task task : tasks) {
                    if (task.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredTasks.add(task);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredTasks;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            submitList((List) results.values);
        }
    };

    class TaskViewHolder extends RecyclerView.ViewHolder {
        public ItemTaskBinding binding;

        public TaskViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
