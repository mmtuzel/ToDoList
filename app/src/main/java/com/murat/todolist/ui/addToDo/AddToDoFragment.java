package com.murat.todolist.ui.addToDo;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.murat.todolist.R;
import com.murat.todolist.databinding.FragmentAddToDoBinding;
import com.murat.todolist.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddToDoFragment extends Fragment {
    private FragmentAddToDoBinding binding;

    public AddToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_to_do, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AddToDoViewModel viewModel = ViewModelProviders.of(this).get(AddToDoViewModel.class);
        binding.setViewModel(viewModel);
        observeAddTodoActionStatus(viewModel);
    }

    private void observeAddTodoActionStatus(AddToDoViewModel viewModel) {
        viewModel.getAddToDoActionStatus().observe(getViewLifecycleOwner(), addToDoActionStatus -> {
            switch (addToDoActionStatus) {
                case CLICK_NEXT: {
                    hideKeyboard();
                    viewModel.checkNameIsValid();
                    break;
                }
                case INPUT_VALIDATION_SUCCESS: {
                    viewModel.insertToDo();
                    break;
                }
                case INPUT_VALIDATION_FAIL: {
                    showSnackbar("Please provide proper ToDo name.");
                    break;
                }
                case TO_DO_CREATION_SUCCESS: {
                    showSnackbar("ToDo creation success");
                    navigateToAddTaskFragment(viewModel.getToDoId());
                    break;
                }
                case TO_DO_CREATION_FAIL: {
                    showSnackbar("ToDo couldn't created.");
                    break;
                }
                default: {
                    showSnackbar("Something unknown occurred");
                    break;
                }
            }
        });
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

    private void navigateToAddTaskFragment(int toDoId) {
        ((MainActivity) getActivity()).navigateToAddTaskFragment(toDoId);
    }
}
