package com.taskmuse.app.ui.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.taskmuse.app.R;
import com.taskmuse.app.utils.Task;
import com.taskmuse.app.utils.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        AppBarLayout appBar = (AppBarLayout) view.findViewById(R.id.appbar);

        RecyclerView recyclerViewToDo = view.findViewById(R.id.recyclerViewToDo);


        // Create new LayoutManagers for each RecyclerView
        LinearLayoutManager layoutManagerToDo = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewToDo.setLayoutManager(layoutManagerToDo);


        //TODO: This code below is used to create dummy data so that the Task Containers can be tested
        List<Task> dummyTasksToDo = generateDummyTasks("To Do", 20);


        //TODO: This code below is used to view dummy data so that the Task Containers can be tested
        TaskAdapter adapterToDo = new TaskAdapter(dummyTasksToDo);


        //TODO: Please use adapter like below to manage actual data being fed into the containers
//        TaskAdapter adapterToDo = new TaskAdapter(getToDoData());


        recyclerViewToDo.setAdapter(adapterToDo);

        return view;
    }

    //TODO: Method to create dummy data which is called in the onCreateView method
    private List<Task> generateDummyTasks(String status, int count) {
        List<Task> dummyTasks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dummyTasks.add(new Task("Task " + (i + 1) + " - " + status));
        }
        return dummyTasks;
    }
}