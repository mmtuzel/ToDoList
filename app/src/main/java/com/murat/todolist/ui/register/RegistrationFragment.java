package com.murat.todolist.ui.register;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.murat.todolist.R;
import com.murat.todolist.databinding.FragmentRegistrationBinding;
import com.murat.todolist.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    private static final String TAG = "RegistrationFragment";

    private FragmentRegistrationBinding binding;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        //binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnGoLogin.setOnClickListener(v -> navigateToLogin());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RegistrationViewModel viewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding.setViewModel(viewModel);
        observeRegistrationActionStatus(viewModel);
    }

    private void observeRegistrationActionStatus(RegistrationViewModel viewModel) {
        viewModel.getRegistrationActionStatus().observe(getViewLifecycleOwner(), registrationActionStatus -> {
            switch (registrationActionStatus) {
                case SIGN_UP_CLICK: {
                    hideKeyboard();
                    viewModel.checkInputsAreValid();
                    break;
                }
                case INPUT_VALIDATION_SUCCESS: {
                    viewModel.checkUserAvailable();
                    break;
                }
                case INPUT_VALIDATION_FAIL: {
                    showSnackbar("Please provide proper user information.");
                    break;
                }
                case USER_ALREADY_TAKEN: {
                    showSnackbar("Email has already taken.");
                    break;
                }
                case USER_CREATE: {
                    viewModel.createUser();
                    break;
                }
                case USER_CREATION_SUCCESS: {
                    viewModel.getUser();
                    break;
                }
                case USER_CREATION_FAIL: {
                    showSnackbar("User hasn't created.");
                    break;
                }
                case USER_ID_SAVE_SUCCESS: {
                    navigateToTask();
                    //showSnackbar("USER HAS CREATED");
                    break;
                }
                case USER_ID_SAVE_FAIL: {
                    showSnackbar("Saving user id has failed");
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

    private void navigateToLogin() {
        ((MainActivity) getActivity()).navigateToLoginFragment();
    }

    private void navigateToTask() {
        ((MainActivity) getActivity()).navigateToTaskFragment();
    }
}
