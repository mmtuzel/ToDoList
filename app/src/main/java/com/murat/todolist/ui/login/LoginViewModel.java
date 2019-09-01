package com.murat.todolist.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.repository.UserRepository;
import com.murat.todolist.util.Constant;
import com.murat.todolist.util.InputValidation;
import com.murat.todolist.util.SharedPrefsHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private SharedPrefsHelper sharedPrefsHelper;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<LoginActionStatus> loginActionStatus = new MutableLiveData<>();
    private ObservableField<String> userEmail = new ObservableField<>();
    private ObservableField<String> userPassword = new ObservableField<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        sharedPrefsHelper = new SharedPrefsHelper(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void loginClick() {
        loginActionStatus.setValue(LoginActionStatus.LOGIN_CLICK);
    }

    public void checkInputsAreValid() {
        if (InputValidation.isValidEmail(userEmail.get()) && InputValidation.isValidPassword(userPassword.get())) {
            loginActionStatus.setValue(LoginActionStatus.INPUT_VALIDATION_SUCCESS);
        } else {
            loginActionStatus.setValue(LoginActionStatus.INPUT_VALIDATION_FAIL);
        }
    }

    public void checkUserCredentials() {
        disposable.add(
                repository.checkUserCredential(userEmail.get(), userPassword.get())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        user -> {
                            saveUserId(user.getId());
                            loginActionStatus.postValue(LoginActionStatus.LOGIN_SUCCESS);
                        },
                        throwable -> loginActionStatus.postValue(LoginActionStatus.LOGIN_FAIL)
                )
        );
    }

    public void saveUserId(String id) {
        sharedPrefsHelper.putPref(Constant.USER_ID, id);
    }

    public MutableLiveData<LoginActionStatus> getLoginActionStatus() {
        return loginActionStatus;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserPassword() {
        return userPassword;
    }
}
