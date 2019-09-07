package com.murat.todolist.ui.tasks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.murat.todolist.data.entity.Status;
import com.murat.todolist.data.entity.Task;
import com.murat.todolist.data.repository.TaskRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TaskViewModel extends AndroidViewModel {
    private static final String TAG = "TaskViewModel";

    private TaskRepository repository;
    private MutableLiveData<TaskFilterType> taskFilterType = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private int toDoId;

    public TaskViewModel(@NonNull Application application) {
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

    public void deleteTask(Task task) {
        disposable.add(
                repository.deleteTask(task)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> Log.d(TAG, "deleteTask success"),
                        throwable -> Log.d(TAG, "deleteTask fail")
                )
        );
    }

    public void completeTask(int taskId) {
        disposable.add(
                repository.updateTaskStatus(taskId, Status.COMPLETED)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> Log.d(TAG, "complete task success"),
                        throwable -> Log.d(TAG, "complete task error")
                )
        );
    }

    public void activateTask(int taskId) {
        disposable.add(
                repository.updateTaskStatus(taskId, Status.ACTIVE)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                () -> Log.d(TAG, "active task success"),
                                throwable -> Log.d(TAG, "active task error")
                        )
        );
    }

    public LiveData<List<Task>> getTasks() {
        return Transformations.switchMap(taskFilterType, input -> {
            if (input.equals(TaskFilterType.All_TASK)) {
                return repository.getTasks(toDoId);
            } else if (input.equals(TaskFilterType.ACTIVE_TASKS)) {
                return repository.getTasksByStatus(toDoId, Status.ACTIVE);
            } else if (input.equals(TaskFilterType.COMPLETED_TASKS)) {
                return repository.getTasksByStatus(toDoId, Status.COMPLETED);
            } else if (input.equals(TaskFilterType.EXPIRED_TASKS)) {
                return repository.getTasksByStatus(toDoId, Status.EXPIRED);
            } else if (input.equals(TaskFilterType.ORDER_BY_NAME_ASC_TASKS)) {
                return repository.getTasksByNameASC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_NAME_DESC_TASKS)) {
                return repository.getTasksByNameDESC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_CREATE_DATE_ASC_TASKS)) {
                return repository.getTasksByCreateDateASC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_CREATE_DATE_DESC_TASKS)) {
                return repository.getTasksByCreateDateDESC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_DEADLINE_DATE_ASC_TASKS)) {
                return  repository.getTasksByDeadlineDateASC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_DEADLINE_DATE_DESC_TASKS)) {
                return  repository.getTasksByDeadlineDateDESC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_STATUS_ASC_TASKS)) {
                return  repository.getTasksByStatusOrderASC(toDoId);
            } else if (input.equals(TaskFilterType.ORDER_BY_STATUS_DESC_TASKS)) {
                return  repository.getTasksByStatusOrderDESC(toDoId);
            }
            return null;
        });
    }

    public void setTaskFilterType(TaskFilterType type) {
        taskFilterType.setValue(type);
    }
}
