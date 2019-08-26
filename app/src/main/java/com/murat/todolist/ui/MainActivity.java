package com.murat.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.murat.todolist.R;
import com.murat.todolist.databinding.ActivityMainBinding;
import com.murat.todolist.ui.addTask.AddTaskFragment;
import com.murat.todolist.ui.taskDetail.TaskDetailFragment;
import com.murat.todolist.ui.tasks.TasksFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        TasksFragment tasksFragment = new TasksFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), tasksFragment);
        transaction.commit();
    }

    public void navigateToAddTaskFragment() {
        AddTaskFragment addTaskFragment = new AddTaskFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), addTaskFragment)
                        .addToBackStack(null);
        transaction.commit();
    }

    public void navigateToTaskDetailFragment(int taskId) {
        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(taskId);

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), taskDetailFragment)
                        .addToBackStack(null);
        transaction.commit();
    }
}
