package com.murat.todolist.databinding;

import android.widget.CheckBox;

import androidx.databinding.BindingAdapter;

import com.murat.todolist.data.entity.Status;

public class CheckBoxBindingAdapter {

    @BindingAdapter("android:setStatusCheckBox")
    public static void setStatusCheckBox(CheckBox checkBox, Status status) {
        if (status == Status.COMPLETED && !checkBox.isChecked()) {
            checkBox.setChecked(true);
        } else if (status == Status.ACTIVE && checkBox.isChecked()) {
            checkBox.setChecked(false);
        } else if (status == Status.EXPIRED) {
            checkBox.setEnabled(false);
        }
    }
}
