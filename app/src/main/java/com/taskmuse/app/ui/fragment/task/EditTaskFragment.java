package com.taskmuse.app.ui.fragment.task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;

import java.util.Arrays;
import java.util.List;

public class EditTaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "TaskId1";

    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance(String taskId) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the taskId from arguments
        String taskId = getArguments().getString(ARG_TASK_ID);

        // References to UI elements
        Spinner projectNameSpinner = view.findViewById(R.id.projectNameSpinner);
        Spinner prioritySpinner = view.findViewById(R.id.prioritySpinner);
        Spinner taskAssigneeSpinner = view.findViewById(R.id.taskAssigneeSpinner);
        TextInputEditText taskNameInput = view.findViewById(R.id.taskNameInput);
        TextInputEditText descriptionInput = view.findViewById(R.id.descriptionInput);
        FloatingActionButton deleteTaskFAB = view.findViewById(R.id.deleteTaskButton);

        // Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore path for the task
        String taskPath = "Tasks/" + taskId;
        Log.d("String PAth", "Data:" + taskPath);

        // Fetch data from Firestore
        db.document(taskPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve data from Firestore
                        String projectName = document.getString("projectName");
                        String priority = document.getString("priority");
                        String taskAssignee = document.getString("assignee");
                        String taskName = document.getString("taskName");
                        String description = document.getString("description");

                        Log.d("EditTaskFragment", "ProjectName: " + projectName);
                        Log.d("EditTaskFragment", "Priority: " + priority);
                        Log.d("EditTaskFragment", "TaskAssignee: " + taskAssignee);


                        // Prepopulate UI elements
                        List<String> projectNamesList = Arrays.asList("Project A", "Project B", "Project C");
                        List<String> priorityList = Arrays.asList("High", "Highest", "Medium", "Low", "Lowest");
                        List<String> assigneeList = Arrays.asList("Kajal", "Agata", "User 3");

                        // Create ArrayAdapter
                        ArrayAdapter<String> projectNameAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, projectNamesList);
                        projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        projectNameSpinner.setAdapter(projectNameAdapter);

                        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, priorityList);
                        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        prioritySpinner.setAdapter(priorityAdapter);

                        ArrayAdapter<String> assigneeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, assigneeList);
                        assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taskAssigneeSpinner.setAdapter(assigneeAdapter);

                        // Now, set the selected values
                        if (projectNameAdapter != null) {
                            int projectPosition = projectNameAdapter.getPosition(projectName);
                            projectNameSpinner.setSelection(projectPosition);
                        }

                        if (priorityAdapter != null) {
                            int priorityPosition = priorityAdapter.getPosition(priority);
                            prioritySpinner.setSelection(priorityPosition);
                        }

                        if (assigneeAdapter != null) {
                            int assigneePosition = assigneeAdapter.getPosition(taskAssignee);
                            taskAssigneeSpinner.setSelection(assigneePosition);
                        }


                        taskNameInput.setText(taskName);
                        descriptionInput.setText(description);
                    } else {
                        // Handle the case where the document does not exist
                        // This might happen if the task ID is invalid
                        Log.d("EditTaskFragment", "Document does not exist");
                        showErrorDialog("Task not found");
                    }
                } else {
                    // Handle failures while fetching data from Firestore
                    Log.d("EditTaskFragment", "Failed to fetch task data", task.getException());
                    showErrorDialog("Failed to fetch task data");
                }
            }
        });

        deleteTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EditTaskFragment", "clicked delete button");
                showDeleteDialog();
            }
        });

    }

    private void showDeleteDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Delete task");
        builder.setMessage("Are you sure you want to delete task?");
        MainActivity mainActivity = (MainActivity) getActivity();
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("EditTaskFragment", "clicked yes");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("EditTaskFragment", "clicked no");
            }
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Helper method to show an error dialog
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}