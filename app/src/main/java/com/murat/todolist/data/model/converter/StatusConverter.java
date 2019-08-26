package com.murat.todolist.data.model.converter;

import androidx.room.TypeConverter;

import com.murat.todolist.data.model.Status;

public class StatusConverter {

    @TypeConverter
    public static Status toStatus(int type) {
        if (type == Status.ACTIVE.getType()) {
            return Status.ACTIVE;
        } else if (type == Status.COMPLETED.getType()) {
            return Status.COMPLETED;
        } else if (type == Status.EXPIRED.getType()) {
            return Status.EXPIRED;
        } else {
            return Status.UNKNOWN;
        }
    }

    @TypeConverter
    public static int toType(Status status) {
        return status.getType();
    }
}
