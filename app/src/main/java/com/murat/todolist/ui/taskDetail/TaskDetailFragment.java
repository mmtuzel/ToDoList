package com.murat.todolist.ui.taskDetail;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.murat.todolist.R;
import com.murat.todolist.data.model.Task;
import com.murat.todolist.databinding.FragmentTaskDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    private static final String TAG = "TaskDetailFragment";
    private static final String ARG_TASK_ID = "taskId";

    private FragmentTaskDetailBinding binding;

    public static TaskDetailFragment newInstance(int taskId) {

        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TaskDetailViewModel taskDetailViewModel = ViewModelProviders.of(
                this,
                new TaskDetailViewModelFactory(
                        getActivity().getApplication(),
                        getArguments().getInt(ARG_TASK_ID)
                )
        ).get(TaskDetailViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(taskDetailViewModel);
        subscribeToViewModel(taskDetailViewModel);
    }

    private void subscribeToViewModel(TaskDetailViewModel viewModel) {
        viewModel.getTask().observe(this, task -> {
            Log.d(TAG, "edit title: " + task.getTitle());
            Log.d(TAG, "edit description: " + task.getDescription());
            viewModel.onTaskLoaded(task);
            //viewModel.setEditedTask(task);
        });
    }
}
