package com.murat.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.murat.todolist.R;
import com.murat.todolist.ToDoApplication;
import com.murat.todolist.databinding.ActivityMainBinding;
import com.murat.todolist.ui.addTask.AddTaskFragment;
import com.murat.todolist.ui.addToDo.AddToDoFragment;
import com.murat.todolist.ui.login.LoginFragment;
import com.murat.todolist.ui.register.RegistrationFragment;
import com.murat.todolist.ui.taskDetail.TaskDetailFragment;
import com.murat.todolist.ui.tasks.TasksFragment;
import com.murat.todolist.ui.toDoList.ToDoListFragment;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.SharedPrefsHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPrefsHelper sharedPrefsHelper = ((ToDoApplication) getApplication()).getSharedPrefs();
        if (sharedPrefsHelper.getPref(Constant.USER_ID) != null) {
            navigateToToDoListFragment();
        } else {
            navigateToRegistrationFragment();
        }
    }

    // TODO will fix fragment transaction with Navigation Component
    public void navigateToTasksFragment(int toDoId) {
        TasksFragment tasksFragment = TasksFragment.newInstance(toDoId);

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), tasksFragment)
                        .addToBackStack(null);
        transaction.commit();
    }

    public void navigateToAddTaskFragment(int toDoId) {
        //AddTaskFragment addTaskFragment = new AddTaskFragment();
        AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(toDoId);

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

    public void navigateToToDoListFragment() {
        ToDoListFragment toDoListFragment = new ToDoListFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), toDoListFragment)
                        .addToBackStack(null);
        transaction.commit();
    }

    public void navigateToAddToDoFragment() {
        AddToDoFragment addToDoFragment = new AddToDoFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), addToDoFragment)
                        .addToBackStack(null);
        transaction.commit();
    }
}
