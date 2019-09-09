package com.murat.todolist.ui.addTask;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.google.android.material.snackbar.Snackbar;
import com.murat.todolist.R;
import com.murat.todolist.databinding.FragmentAddTaskBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {
    private static final String ARG_TO_DO_ID = "toDoId";

    private FragmentAddTaskBinding binding;
    private AddTaskViewModel addTaskViewModel;

    public static AddTaskFragment newInstance(int toDoId) {

        Bundle args = new Bundle();
        args.putInt(ARG_TO_DO_ID, toDoId);

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addTaskViewModel = ViewModelProviders.of(this).get(AddTaskViewModel.class);
        addTaskViewModel.setToDoId(getArguments().getInt(ARG_TO_DO_ID));
        binding.setLifecycleOwner(this);
        binding.setViewModel(addTaskViewModel);
        observeAddTaskActionState(addTaskViewModel);

        binding.ivTaskDeadline.setOnClickListener(v -> buildDatePicker());
    }

    private void observeAddTaskActionState(AddTaskViewModel viewModel) {
        viewModel.getAddTaskActionState().observe(getViewLifecycleOwner(), addTaskActionState -> {
            switch (addTaskActionState) {
                case SAVE_TASK_CLICK: {
                    hideKeyboard();
                    viewModel.checkTaskInputs();
                    //viewModel.insertTask();
                    break;
                }
                case INPUT_VALIDATION_SUCCESS: {
                    //showSnackbar("Task input validation success.");
                    viewModel.insertTask();
                    break;
                }
                case INPUT_VALIDATION_FAIL: {
                    showSnackbar("Task input validation fail.");
                    break;
                }
                case TASK_CREATION_SUCCESS: {
                    showSnackbar("Task creation success.");
                    navigateToTasksFragment(getArguments().getInt(ARG_TO_DO_ID));
                    break;
                }
                case TASK_CREATION_FAIL: {
                    showSnackbar("Task creation fail.");
                    break;
                }
            }
        });
    }

    private void buildDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showSnackbar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getActivity().findViewById(android.R.id.content).getWindowToken(), 0
        );
    }

    private void navigateToTasksFragment(int toDoId) {
        Bundle bundle = new Bundle();
        bundle.putInt("toDoId", toDoId);
        NavHostFragment.findNavController(this).navigate(
                R.id.action_addTaskFragment_to_tasksFragment,
                bundle
        );
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
            Date date = new Date(calendar.getTimeInMillis());
            addTaskViewModel.getTaskDeadlineDate().set(date);
        }
    };
}
