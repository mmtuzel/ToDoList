package com.murat.todolist.databinding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.murat.todolist.data.model.Status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextBindingAdapter {

    @BindingAdapter("android:setDate")
    public static void setDate(TextView textView, Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            textView.setText(dateFormat.format(date));
        }
    }

    @BindingAdapter("android:taskStatus")
    public static void setTaskStatus(TextView textView, Status status) {
        textView.setText(status.name().toUpperCase());
    }
}
