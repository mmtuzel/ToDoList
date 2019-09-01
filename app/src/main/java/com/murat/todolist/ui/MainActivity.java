package com.murat.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.murat.todolist.R;
import com.murat.todolist.databinding.ActivityMainBinding;
import com.murat.todolist.ui.addTask.AddTaskFragment;
import com.murat.todolist.ui.login.LoginFragment;
import com.murat.todolist.ui.register.RegistrationFragment;
import com.murat.todolist.ui.taskDetail.TaskDetailFragment;
import com.murat.todolist.ui.tasks.TasksFragment;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.SharedPrefsHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(this);
        if (sharedPrefsHelper.getPref(Constant.USER_ID) != null) {
            navigateToTaskFragment();
        } else {
            navigateToRegistrationFragment();
        }
    }

    // TODO will fix fragment transaction with Navigation Component
    public void navigateToTaskFragment() {
        TasksFragment tasksFragment = new TasksFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), tasksFragment)
                        .addToBackStack(null);
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

    public void navigateToLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), loginFragment)
                        .addToBackStack(null);
        transaction.commit();

    }

    public void navigateToRegistrationFragment() {
        RegistrationFragment registrationFragment = new RegistrationFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), registrationFragment)
                        .addToBackStack(null);
        transaction.commit();

    }
}
