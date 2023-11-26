package com.taskmuse.app.ui.fragment.task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;
import com.taskmuse.app.utils.firebaseDatabaseUtils;

import java.util.Arrays;
import java.util.List;

public class EditTaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "TaskId2";

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
        Spinner statusSpinner = view.findViewById(R.id.statusSpinner);
        Spinner taskAssigneeSpinner = view.findViewById(R.id.taskAssigneeSpinner);
        TextInputEditText taskNameInput = view.findViewById(R.id.taskNameInput);
        TextInputEditText descriptionInput = view.findViewById(R.id.descriptionInput);
        FloatingActionButton deleteTaskFAB = view.findViewById(R.id.deleteTaskButton);

        // Firestore path for the task
        String taskPath = "Tasks/" + taskId;
        Log.d("String PAth", "Data:" + taskPath);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Fetch data from Firestore
            firebaseDatabaseUtils.getFirestoreInstance().document(taskPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve data from Firestore
                            String projectName = document.getString("projectName");
                            String status = document.getString("status");
                            String priority = document.getString("priority");
                            String taskAssignee = document.getString("assignee");
                            String taskName = document.getString("taskName");
                            String description = document.getString("description");

                            Log.d("EditTaskFragment", "ProjectName: " + projectName);
                            Log.d("EditTaskFragment", "Status: " + status);
                            Log.d("EditTaskFragment", "Priority: " + priority);
                            Log.d("EditTaskFragment", "TaskAssignee: " + taskAssignee);


                            // Prepopulate UI elements
                            List<String> projectNamesList = Arrays.asList("Project A", "Project B", "Project C, Project D");
                            List<String> statusList = Arrays.asList("ToDo", "InProgress", "Done");
                            List<String> priorityList = Arrays.asList("High", "Highest", "Medium", "Low", "Lowest");
                            List<String> assigneeList = Arrays.asList("Kajal", "Agata", "PSM", "Sandesh");

                            // Create ArrayAdapter
                            ArrayAdapter<String> projectNameAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, projectNamesList);
                            projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            projectNameSpinner.setAdapter(projectNameAdapter);


                            ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statusList);
                            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusSpinner.setAdapter(statusAdapter);

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

                            if (statusAdapter != null) {
                                int statusPosition = statusAdapter.getPosition(status);
                                statusSpinner.setSelection(statusPosition);
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

                            // Set OnClickListener for the "Edit Task" button
                            Button editTaskButton = view.findViewById(R.id.editTaskBtn);
                            editTaskButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Retrieve values from UI elements
                                    String taskName = taskNameInput.getText().toString();
                                    String description = descriptionInput.getText().toString();

                                    // Perform the logic to update the task in Firestore
                                    updateTaskInFirestore(taskId, projectNameSpinner, taskName, statusSpinner, prioritySpinner, descriptionInput, taskAssigneeSpinner);
                                }
                            });
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
        } else {
            Log.d("EditTaskFragment: ","User Not Found/User Logged out");
        }


        deleteTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EditTaskFragment", "clicked delete button");
                showDeleteDialog(taskPath);
            }
        });
    }

    // Method to update the task in Firestore
    // Update the task in Firestore
    private void updateTaskInFirestore(String taskId, Spinner projectNameSpinner, String taskName, Spinner statusSpinner, Spinner prioritySpinner, TextInputEditText descriptionInput, Spinner taskAssigneeSpinner) {
        // Retrieve values from UI elements
        String projectName = projectNameSpinner.getSelectedItem().toString();
        String taskNameValue = taskName;
        String status = statusSpinner.getSelectedItem().toString();
        String priority = prioritySpinner.getSelectedItem().toString();
        String description = descriptionInput.getText().toString();
        String taskAssignee = taskAssigneeSpinner.getSelectedItem().toString();

        // Update the task in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String taskPath = "Tasks/" + taskId;
        firebaseDatabaseUtils.getFirestoreInstance().document(taskPath).update(
                "projectName", projectName,
                "taskName", taskNameValue,
                "status", status,
                "priority", priority,
                "description", description,
                "assignee", taskAssignee
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Show a success message or handle success accordingly
                    // You can display a Toast, Snackbar, or any other UI element
                    showSuccessDialog("Task updated successfully");
                    showToast("Task updated successfully");
                } else {
                    // Handle errors that might occur during the update process
                    showErrorDialog("Failed to update task");
                }
            }
        });
    }


    // Helper method to show dialog
    private void showDialog(String title, String message, int icon) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(icon)
                    .show();
        }else {
            Log.d("EditTaskFragment: ","USER NOT FOUND/USER LOGGED_OUT");
        }
    }

    private void showDeleteDialog(String taskPath) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Delete task");
        builder.setMessage("Are you sure you want to delete task?");
        MainActivity mainActivity = (MainActivity) getActivity();
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("EditTaskFragment", "clicked yes");
                deleteTaskFromFirestore(taskPath);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Log.d("EditTaskFragment", "clicked no");
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTaskFromFirestore(String taskPath) {
        if (taskPath == null) {
            showErrorDialog("Task not Found");
        } else {
            firebaseDatabaseUtils.getFirestoreInstance().document(taskPath).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("EditTaskFragment", "Task deleted successfully");
                                requireActivity().onBackPressed();
                            } else {
                                Log.d("EditTaskFragment", "Error deleting task", task.getException());
                                showErrorDialog("Error deleting task");
                            }
                        }
                    });
        }
    }

    // Usage
    private void showErrorDialog(String message) {
        showDialog("Error", message, android.R.drawable.ic_dialog_alert);
    }

    private void showSuccessDialog(String message) {
        showDialog("Success", message, android.R.drawable.ic_dialog_info);
    }

    private void showToast(String message) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("EditTaskFragment: ","USER NOT FOUND/USER LOGGED OUT");
        }
    }

}