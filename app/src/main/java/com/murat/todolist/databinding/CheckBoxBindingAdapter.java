package com.murat.todolist.databinding;

import android.widget.CheckBox;

import androidx.databinding.BindingAdapter;

import com.murat.todolist.data.model.Status;

public class CheckBoxBindingAdapter {

    @BindingAdapter("android:setStatusCheckBox")
    public static void setStatusCheckBox(CheckBox checkBox, Status status) {
        if (status == Status.COMPLETED) {
            checkBox.setChecked(true);
        }
    }
}
