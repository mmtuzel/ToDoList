package com.murat.todolist.ui.toDoList;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.murat.todolist.BR;
import com.murat.todolist.R;
import com.murat.todolist.data.entity.ToDo;
import com.murat.todolist.databinding.FragmentToDoListBinding;
import com.murat.todolist.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListFragment extends Fragment {
    private FragmentToDoListBinding binding;
    private ToDoListAdapter listAdapter;
    private ToDoListViewModel viewModel;

    public ToDoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_do_list, container, false);

        listAdapter = new ToDoListAdapter(new ToDoDiffUtil(), clickCallback);
        binding.rvToDo.setAdapter(listAdapter);
        binding.fabAddToDo.setOnClickListener(v -> navigateToAddToDoFragment());

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ToDoListViewModel.class);
        observeToDos(viewModel);
    }

    private void observeToDos(ToDoListViewModel viewModel) {
        viewModel.getToDoList().observe(getViewLifecycleOwner(),
                toDos -> listAdapter.submitList(toDos));
    }

    private void navigateToAddToDoFragment() {
        ((MainActivity) getActivity()).navigateToAddToDoFragment();
    }

    private void navigateToTasksFragment(int toDoId) {
        ((MainActivity) getActivity()).navigateToTasksFragment(toDoId);
    }

    private ToDoClickCallback clickCallback = new ToDoClickCallback() {
        @Override
        public void onToDoClick(ToDo toDo) {
            navigateToTasksFragment(toDo.getId());
        }

        @Override
        public void onToDoDelete(ToDo toDo) {
            viewModel.deleteToDo(toDo);
        }
    };
}
