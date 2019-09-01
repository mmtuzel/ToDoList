package com.murat.todolist.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.murat.todolist.data.entity.User;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    Completable insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email")
    Single<User> checkUserAvailable(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    Single<User> checkUserCredential(String email, String password);
}
