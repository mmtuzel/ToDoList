package com.murat.todolist.ui.register;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.entity.User;
import com.murat.todolist.data.repository.UserRepository;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.InputValidation;
import com.murat.todolist.util.SharedPrefsHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegistrationViewModel extends AndroidViewModel {
    private UserRepository repository;
    private SharedPrefsHelper sharedPrefsHelper;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<RegistrationActionStatus> registrationActionStatus = new MutableLiveData<>();
    private ObservableField<String> userEmail = new ObservableField<>();
    private ObservableField<String> userPassword = new ObservableField<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        sharedPrefsHelper = new SharedPrefsHelper(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void signUpClick() {
        registrationActionStatus.setValue(RegistrationActionStatus.SIGN_UP_CLICK);
    }

    public void checkInputsAreValid() {
        if (InputValidation.isValidEmail(userEmail.get()) && InputValidation.isValidPassword(userPassword.get())) {
            registrationActionStatus.setValue(RegistrationActionStatus.INPUT_VALIDATION_SUCCESS);
        } else {
            registrationActionStatus.setValue(RegistrationActionStatus.INPUT_VALIDATION_FAIL);
        }
    }

    public void checkUserAvailable() {
        disposable.add(
                repository.getUser(userEmail.get())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        user -> registrationActionStatus.postValue(RegistrationActionStatus.USER_ALREADY_TAKEN),
                        throwable -> registrationActionStatus.postValue(RegistrationActionStatus.USER_CREATE)
                )
        );
    }

    public void createUser() {
        User user = new User();
        user.setEmail(userEmail.get());
        user.setPassword(userPassword.get());

        disposable.add(
                repository.createUser(user)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> registrationActionStatus.postValue(RegistrationActionStatus.USER_CREATION_SUCCESS),
                        throwable -> registrationActionStatus.postValue(RegistrationActionStatus.USER_CREATION_FAIL)
                )
        );
    }

    public void getUser() {
        disposable.add(
                repository.getUser(userEmail.get())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        user -> {
                            saveUserId(user.getId());
                            registrationActionStatus.postValue(RegistrationActionStatus.USER_ID_SAVE_SUCCESS);
                        },
                        throwable -> registrationActionStatus.postValue(RegistrationActionStatus.USER_ID_SAVE_FAIL)
                )
        );
    }

    public void saveUserId(String id) {
        sharedPrefsHelper.putPref(Constant.USER_ID, id);
    }

    public MutableLiveData<RegistrationActionStatus> getRegistrationActionStatus() {
        return registrationActionStatus;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserPassword() {
        return userPassword;
    }
}
