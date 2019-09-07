package com.murat.todolist.ui.toDoList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.ToDoApplication;
import com.murat.todolist.data.entity.ToDo;
import com.murat.todolist.data.repository.TaskRepository;
import com.murat.todolist.data.repository.ToDoRepository;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.SharedPrefsHelper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ToDoListViewModel extends AndroidViewModel {
    private static final String TAG = "ToDoListViewModel";
    private ToDoRepository repository;
    private TaskRepository taskRepository;
    private String userId;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<ToDoListActionStatus> toDoListActionStatus = new MutableLiveData<>();

    public ToDoListViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoRepository(application);
        taskRepository = new TaskRepository(application);
        SharedPrefsHelper sharedPrefsHelper = ((ToDoApplication) application).getSharedPrefs();
        userId = sharedPrefsHelper.getPref(Constant.USER_ID);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public LiveData<List<ToDo>> getToDoList() {
        return repository.getToDoList(userId);
    }

    public void deleteToDo(ToDo toDo) {
        disposable.add(
                repository.deleteToDo(toDo)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> {
                            Log.d(TAG, "delete success");
                            //deleteToDoTasks(toDo.getId());
                        },
                        throwable -> Log.d(TAG, "delete fail")
                )
        );
    }

    public void deleteToDoTasks(int toDoId) {
        disposable.add(
                taskRepository.deleteTasksByToDoId(toDoId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> Log.d(TAG, "deleteToDoTasks success"),
                        throwable -> Log.d(TAG, "deleteToDoTasks fail")
                )
        );
    }
}
