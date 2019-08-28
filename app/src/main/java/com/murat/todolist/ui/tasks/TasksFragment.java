package com.murat.todolist.ui.tasks;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.murat.todolist.data.model.Status;
import com.murat.todolist.ui.MainActivity;
import com.murat.todolist.R;
import com.murat.todolist.data.model.Task;
import com.murat.todolist.databinding.FragmentTasksBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {
    private static final String TAG = "TasksFragment";

    private FragmentTasksBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);

        taskAdapter = new TaskAdapter(new TaskDiffUtil(), clickCallback);
        binding.rvTasks.setAdapter(taskAdapter);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tasks, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search: {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(searchListener);
                break;
            }
            case R.id.menu_filter: {
                showFilteringPopupMenu();
                break;
            }
            case R.id.menu_order: {
                showOrderPopupMenu();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.setTaskFilterType(TaskFilterType.All_TASK);
        subscribeTasks(taskViewModel.getTasks());

        binding.fabAddTask.setOnClickListener(v ->
                ((MainActivity) getActivity()).navigateToAddTaskFragment());
    }

    private void subscribeTasks(LiveData<List<Task>> liveData) {
        liveData.removeObservers(this); // workaround for improper navigation and fragment handling. will be fixed.
        liveData.observe(this, tasks -> {
            Log.d(TAG, "tasks size: " + tasks.size());
            taskAdapter.submitList(tasks);
            taskAdapter.setTasks(tasks);
        });
    }

    private void showFilteringPopupMenu() {
        View view = getActivity().findViewById(R.id.menu_filter);
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_filters, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.filter_active: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ACTIVE_TASKS);
                    break;
                }
                case R.id.filter_completed: {
                    taskViewModel.setTaskFilterType(TaskFilterType.COMPLETED_TASKS);
                    break;
                }
                case R.id.filter_expired: {
                    taskViewModel.setTaskFilterType(TaskFilterType.EXPIRED_TASKS);
                    break;
                }
                case R.id.filter_all: {
                    taskViewModel.setTaskFilterType(TaskFilterType.All_TASK);
                    break;
                }
            }
            return false;
        });
    }

    private void showOrderPopupMenu() {
        View view = getActivity().findViewById(R.id.menu_order);
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_orders, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.order_name_asc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_NAME_ASC_TASKS);
                    break;
                }
                case R.id.order_name_desc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_NAME_DESC_TASKS);
                    break;
                }
                case R.id.order_create_date_asc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_CREATE_DATE_ASC_TASKS);
                    break;
                }
                case R.id.order_create_date_desc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_CREATE_DATE_DESC_TASKS);
                    break;
                }
                case R.id.order_deadline_date_asc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_DEADLINE_DATE_ASC_TASKS);
                    break;
                }
                case R.id.order_deadline_date_desc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_DEADLINE_DATE_DESC_TASKS);
                    break;
                }
                case R.id.order_status_asc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_STATUS_ASC_TASKS);
                    break;
                }
                case R.id.order_status_desc: {
                    taskViewModel.setTaskFilterType(TaskFilterType.ORDER_BY_STATUS_DESC_TASKS);
                    break;
                }
            }
            return false;
        });
    }

    private TaskClickCallback clickCallback = new TaskClickCallback() {
        @Override
        public void onTaskClick(Task task) {
            ((MainActivity) getActivity()).navigateToTaskDetailFragment(task.getId());
        }

        @Override
        public void onTaskDeleteClick(Task task) {
            taskViewModel.deleteTask(task);
        }

        @Override
        public void onTaskStatusChangeClick(Task task) {
            if (task.getStatus() == Status.ACTIVE) {
                taskViewModel.completeTask(task);
            } else if (task.getStatus() == Status.COMPLETED) {
                taskViewModel.activateTask(task);
            }
        }
    };

    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            taskAdapter.getFilter().filter(newText);
            return false;
        }
    };
}
