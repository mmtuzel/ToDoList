package com.murat.todolist.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.murat.todolist.R;
import com.murat.todolist.data.model.Status;

public class ImageBindingAdapter {

    @BindingAdapter("android:taskStatusImage")
    public static void setTaskStatusImage(ImageView imageView, Status status) {
        if (status == Status.ACTIVE) {
            imageView.setImageResource(R.drawable.ic_status_active);
        } else if (status == Status.COMPLETED) {
            imageView.setImageResource(R.drawable.ic_status_complete);
        } else if (status == Status.EXPIRED) {
            imageView.setImageResource(R.drawable.ic_status_expire);
        } else {
            imageView.setImageResource(R.drawable.ic_status_unkown);
        }
    }
}
