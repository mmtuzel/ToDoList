package com.murat.todolist.ui.login;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.murat.todolist.databinding.FragmentLoginBinding;
import com.murat.todolist.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnGoSignUp.setOnClickListener(v -> navigateToRegistration());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        observeLoginActionStatus(viewModel);
    }

    private void observeLoginActionStatus(LoginViewModel viewModel) {
        viewModel.getLoginActionStatus().observe(getViewLifecycleOwner(), loginActionStatus -> {
            switch (loginActionStatus) {
                case LOGIN_CLICK: {
                    hideKeyboard();
                    viewModel.checkInputsAreValid();
                    break;
                }
                case INPUT_VALIDATION_SUCCESS: {
                    viewModel.checkUserCredentials();
                    break;
                }
                case INPUT_VALIDATION_FAIL: {
                    showSnackbar("Please provide proper user information.");
                    break;
                }
                case LOGIN_SUCCESS: {
                    navigateToTask();
                    break;
                }
                case LOGIN_FAIL: {
                    showSnackbar("There is no such user.");
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

    private void navigateToRegistration() {
        ((MainActivity) getActivity()).navigateToRegistrationFragment();
    }

    private void navigateToTask() {
        ((MainActivity) getActivity()).navigateToTaskFragment();
    }
}
