package com.taskmuse.app.ui.fragment.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.taskmuse.app.R;

public class EditTaskFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance(String param1, String param2) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_task, container, false);

        // Assuming you have an array of project names
        String[] projectNames = {"Project A", "Project B", "Project C"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, projectNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get the Spinner reference and set the adapter
        Spinner projectNameSpinner = rootView.findViewById(R.id.projectNameSpinner);
        projectNameSpinner.setAdapter(adapter);


        // Assuming you have an array of priority levels
        String[] priorities = {"Higher", "High", "Medium", "Low", "Lowest"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, priorities);

        // Specify the layout to use when the list of choices appears
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get the Spinner reference and set the adapter
        Spinner prioritySpinner = rootView.findViewById(R.id.prioritySpinner);
        prioritySpinner.setAdapter(priorityAdapter);

        // Assuming you have an array of task assignees
        String[] assignees = {"Assignee 1", "Assignee 2", "Assignee 3"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> assigneeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, assignees);

        // Specify the layout to use when the list of choices appears
        assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get the Spinner reference and set the adapter
        Spinner taskAssigneeSpinner = rootView.findViewById(R.id.taskAssigneeSpinner);
        taskAssigneeSpinner.setAdapter(assigneeAdapter);

        return rootView;
    }
}
