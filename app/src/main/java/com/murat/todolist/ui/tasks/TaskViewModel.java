package com.murat.todolist.ui.tasks;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.murat.todolist.data.model.Status;
import com.murat.todolist.data.model.Task;
import com.murat.todolist.data.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> tasks;
    private MutableLiveData<TaskFilterType> taskFilterType = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = Transformations.switchMap(taskFilterType, input -> {
            if (input.equals(TaskFilterType.All_TASK)) {
                return repository.getTasks();
            } else if (input.equals(TaskFilterType.ACTIVE_TASKS)) {
                return repository.getTasksByStatus(Status.ACTIVE);
            } else if (input.equals(TaskFilterType.COMPLETED_TASKS)) {
                return repository.getTasksByStatus(Status.COMPLETED);
            } else if (input.equals(TaskFilterType.EXPIRED_TASKS)) {
                return repository.getTasksByStatus(Status.EXPIRED);
            } else if (input.equals(TaskFilterType.ORDER_BY_NAME_ASC_TASKS)) {
                return repository.getTasksByNameASC();
            } else if (input.equals(TaskFilterType.ORDER_BY_NAME_DESC_TASKS)) {
                return repository.getTasksByNameDESC();
            } else if (input.equals(TaskFilterType.ORDER_BY_CREATE_DATE_ASC_TASKS)) {
                return repository.getTaskByCreateDateASC();
            } else if (input.equals(TaskFilterType.ORDER_BY_CREATE_DATE_DESC_TASKS)) {
                return repository.getTaskByCreateDateDESC();
            } else if (input.equals(TaskFilterType.ORDER_BY_DEADLINE_DATE_ASC_TASKS)) {
                return  repository.getTaskByDeadlineDateASC();
            } else if (input.equals(TaskFilterType.ORDER_BY_DEADLINE_DATE_DESC_TASKS)) {
                return  repository.getTaskByDeadlineDateDESC();
            } else if (input.equals(TaskFilterType.ORDER_BY_STATUS_ASC_TASKS)) {
                return  repository.getTasksByStatusOrderASC();
            } else if (input.equals(TaskFilterType.ORDER_BY_STATUS_DESC_TASKS)) {
                return  repository.getTasksByStatusOrderDESC();
            }
            return null;
        });
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public void updateTaskStatus(Task task) {
        /*if (task.getStatus() == Status.ACTIVE) {
            task.setStatus(Status.COMPLETED);
        } else if (task.getStatus() == Status.COMPLETED) {
            task.setStatus(Status.ACTIVE);
        }*/
        repository.updateTask(task);
        Log.d("TaskViewModel", "task updated");
    }

    public void completeTask(Task task) {
        //task.setStatus(Status.COMPLETED);
        repository.updateTaskStatus(task, Status.COMPLETED);
    }

    public void activateTask(Task task) {
        //task.setStatus(Status.ACTIVE);
        repository.updateTaskStatus(task, Status.ACTIVE);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void setTaskFilterType(TaskFilterType type) {
        taskFilterType.setValue(type);
    }
}
