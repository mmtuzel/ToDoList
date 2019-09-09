package com.murat.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.murat.todolist.R;
import com.murat.todolist.ToDoApplication;
import com.murat.todolist.databinding.ActivityMainBinding;
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
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
            NavHostFragment.findNavController(navHostFragment).navigate(R.id.action_registrationFragment_to_toDoListFragment);
        }
    }
}
