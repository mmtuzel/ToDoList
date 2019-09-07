package com.murat.todolist.ui.addToDo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.entity.ToDo;
import com.murat.todolist.data.repository.ToDoRepository;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.InputValidation;
import com.murat.todolist.util.SharedPrefsHelper;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddToDoViewModel extends AndroidViewModel {
    private ToDoRepository repository;
    private String userId;
    private int toDoId;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<AddToDoActionStatus> addToDoActionStatus = new MutableLiveData<>();
    private ObservableField<String> toDoName = new ObservableField<>();

    public AddToDoViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoRepository(application);
        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(application.getApplicationContext());
        userId = sharedPrefsHelper.getPref(Constant.USER_ID);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void clickNext() {
        addToDoActionStatus.setValue(AddToDoActionStatus.CLICK_NEXT);
    }

    public void checkNameIsValid() {
        if (InputValidation.isValidText(toDoName.get())) {
            addToDoActionStatus.setValue(AddToDoActionStatus.INPUT_VALIDATION_SUCCESS);
        } else {
            addToDoActionStatus.setValue(AddToDoActionStatus.INPUT_VALIDATION_FAIL);
        }
    }

    public void insertToDo() {
        ToDo toDo = new ToDo();
        toDo.setUserId(userId);
        toDo.setName(toDoName.get());

        disposable.add(
                Single.fromCallable(() -> repository.insertToDo(toDo))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        aLong -> {
                            //toDoId = toDo.getId();
                            Log.d("AddToDoViewModel", "ID: " + aLong);
                            toDoId = aLong.intValue();
                            addToDoActionStatus.postValue(AddToDoActionStatus.TO_DO_CREATION_SUCCESS);
                        },
                        throwable -> addToDoActionStatus.postValue(AddToDoActionStatus.TO_DO_CREATION_FAIL)
                )
        );
    }

    public MutableLiveData<AddToDoActionStatus> getAddToDoActionStatus() {
        return addToDoActionStatus;
    }

    public ObservableField<String> getToDoName() {
        return toDoName;
    }

    public int getToDoId() {
        return toDoId;
    }
}
