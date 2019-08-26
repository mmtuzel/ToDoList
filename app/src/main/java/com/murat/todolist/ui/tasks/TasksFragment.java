package com.murat.todolist.ui.tasks;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.murat.todolist.ui.MainActivity;
import com.murat.todolist.R;
import com.murat.todolist.data.model.Task;
import com.murat.todolist.databinding.FragmentTasksBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {
    private static final String TAG = "TasksFragment";

    private FragmentTasksBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);

        taskAdapter = new TaskAdapter(clickCallback);
        binding.rvTasks.setAdapter(taskAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        subscribeTasks(taskViewModel.getTasks());

        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigateToAddTaskFragment();
            }
        });
    }

    private void subscribeTasks(LiveData<List<Task>> liveData) {
        liveData.observe(this, tasks -> {
            Log.d(TAG, "tasks size: " + tasks.size());
            taskAdapter.setTasks(tasks);
        });
    }

    private TaskClickCallback clickCallback = new TaskClickCallback() {
        @Override
        public void onTaskClick(Task task) {
            ((MainActivity) getActivity()).navigateToTaskDetailFragment(task.getId());
        }

        @Override
        public void onTaskDeleteClick(Task task) {
            taskViewModel.deleteTask(task);
        }

        @Override
        public void onTaskCompleteClick(Task task) {
            taskViewModel.completeTask(task);
        }
    };
}
