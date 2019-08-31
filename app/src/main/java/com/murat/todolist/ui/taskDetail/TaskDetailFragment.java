package com.murat.todolist.ui.taskDetail;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.murat.todolist.R;
import com.murat.todolist.databinding.FragmentTaskDetailBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    private static final String TAG = "TaskDetailFragment";
    private static final String ARG_TASK_ID = "taskId";

    private FragmentTaskDetailBinding binding;
    private TaskDetailViewModel taskDetailViewModel;

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

        taskDetailViewModel = ViewModelProviders.of(
                this,
                new TaskDetailViewModelFactory(
                        getActivity().getApplication(),
                        getArguments().getInt(ARG_TASK_ID)
                )
        ).get(TaskDetailViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(taskDetailViewModel);
        subscribeToViewModel(taskDetailViewModel);

        binding.ivDeadlineDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        binding.ivShare.setOnClickListener(v -> shareTaskViaEmail());
    }

    private void subscribeToViewModel(TaskDetailViewModel viewModel) {
        viewModel.getTask().observe(this, task -> {
            viewModel.onTaskLoaded(task);
        });
    }

    private void shareTaskViaEmail() {
        String title = taskDetailViewModel.taskTitle.getValue();
        String description = taskDetailViewModel.taskDescription.getValue();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(intent);
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
            Date date = new Date(calendar.getTimeInMillis());
            taskDetailViewModel.taskDeadlineDate.setValue(date);
        }
    };
}
