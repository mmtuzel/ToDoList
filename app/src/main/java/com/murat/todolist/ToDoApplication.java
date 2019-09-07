package com.murat.todolist;

import android.app.Application;

import com.murat.todolist.util.SharedPrefsHelper;

public class ToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public SharedPrefsHelper getSharedPrefs() {
        return SharedPrefsHelper.getSharedPrefs(this);
    }
}
