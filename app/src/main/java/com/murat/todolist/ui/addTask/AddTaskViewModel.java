package com.murat.todolist.ui.addTask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.murat.todolist.data.repository.TaskRepository;
import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;
import com.murat.todolist.util.InputValidation;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int toDoId;

    private MutableLiveData<AddTaskActionState> addTaskActionState = new MutableLiveData<>();
    private ObservableField<String> taskTitle = new ObservableField<>();
    private ObservableField<String> taskDescription = new ObservableField<>();
    private ObservableField<Date> taskDeadlineDate = new ObservableField<>();

    public AddTaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public void clickSaveTask() {
        addTaskActionState.setValue(AddTaskActionState.SAVE_TASK_CLICK);
    }

    public void checkTaskInputs() {
        if (InputValidation.isValidText(taskTitle.get()) &&
                InputValidation.isValidText(taskDescription.get()) &&
                InputValidation.isValidDate(taskDeadlineDate.get())) {
            addTaskActionState.setValue(AddTaskActionState.INPUT_VALIDATION_SUCCESS);
        } else {
            addTaskActionState.setValue(AddTaskActionState.INPUT_VALIDATION_FAIL);
        }
    }

    public void insertTask() {
        String currentTaskTitle = taskTitle.get();
        String currentTaskDescription = taskDescription.get();
        Date deadline = taskDeadlineDate.get();
        Task task = new Task(currentTaskTitle, currentTaskDescription, new Date(), deadline, Status.ACTIVE);
        task.setToDoId(toDoId);

        disposable.add(
                repository.insertTask(task)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> addTaskActionState.postValue(AddTaskActionState.TASK_CREATION_SUCCESS),
                        throwable -> addTaskActionState.postValue(AddTaskActionState.TASK_CREATION_FAIL)
                )
        );
    }

    /*public MutableLiveData<Date> getTaskDeadlineDate() {
        return taskDeadlineDate;
    }*/

    public ObservableField<String> getTaskTitle() {
        return taskTitle;
    }

    public ObservableField<String> getTaskDescription() {
        return taskDescription;
    }

    public ObservableField<Date> getTaskDeadlineDate() {
        return taskDeadlineDate;
    }

    public MutableLiveData<AddTaskActionState> getAddTaskActionState() {
        return addTaskActionState;
    }
}
