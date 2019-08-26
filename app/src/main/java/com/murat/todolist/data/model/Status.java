package com.murat.todolist.data.model;

public enum Status {
    ACTIVE(0),
    COMPLETED(1),
    EXPIRED(2),
    UNKNOWN(3);

    private int type;

    Status(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
