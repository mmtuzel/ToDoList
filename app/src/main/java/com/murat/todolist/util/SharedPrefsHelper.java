package com.murat.todolist.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    private static final String APP_PREFS = "todo_prefs";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getPref(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void deletePrefs() {
        editor.clear().apply();
    }
}
