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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        textView.setText(dateFormat.format(date));
    }

    @BindingAdapter("android:taskStatus")
    public static void setTaskStatus(TextView textView, Status status) {
        /*if (status == Status.ACTIVE) {
            textView.setText(status.name().toUpperCase());
        } else if (status == Status.COMPLETED) {
            textView.setText(status.name().toUpperCase());
        } else if (status == Status.EXPIRED) {
            textView.setText(status.name().toUpperCase());
        } else {
            textView.setText(status.name().toUpperCase());
        }*/
        textView.setText(status.name().toUpperCase());
    }
}
