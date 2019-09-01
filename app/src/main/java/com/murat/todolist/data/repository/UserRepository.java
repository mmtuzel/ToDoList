package com.murat.todolist.data.repository;

import android.app.Application;

import com.murat.todolist.data.ToDoDatabase;
import com.murat.todolist.data.dao.UserDao;
import com.murat.todolist.data.entity.User;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        ToDoDatabase database = ToDoDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public Completable createUser(User user) {
        return userDao.insertUser(user);
    }

    public Single<User> getUser(String email) {
        return userDao.checkUserAvailable(email);
    }

    public Single<User> checkUserCredential(String email, String password) {
        return userDao.checkUserCredential(email, password);
    }
}
