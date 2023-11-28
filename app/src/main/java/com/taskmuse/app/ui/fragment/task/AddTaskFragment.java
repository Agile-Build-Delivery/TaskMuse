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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;
import com.taskmuse.app.model.Task;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;

import java.util.Arrays;
import java.util.List;

public class AddTaskFragment extends Fragment {

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // References to UI elements in the AddTaskFragment layout
        Spinner projectNameSpinner = view.findViewById(R.id.projectNameSpinner);
        Spinner prioritySpinner = view.findViewById(R.id.prioritySpinner);
        Spinner statusSpinner = view.findViewById(R.id.statusSpinner);
        Spinner taskAssigneeSpinner = view.findViewById(R.id.taskAssigneeSpinner);
        TextInputEditText taskNameInput = view.findViewById(R.id.taskNameInput);
        TextInputEditText descriptionInput = view.findViewById(R.id.descriptionInput);

        // Populate spinners with data
        List<String> projectNamesList = Arrays.asList("Project A", "Project B", "Project C", "Project D");
        List<String> statusList = Arrays.asList("ToDo", "InProgress", "Done");
        List<String> priorityList = Arrays.asList("High", "Highest", "Medium", "Low", "Lowest");
        List<String> assigneeList = Arrays.asList("Kajal", "Agata", "PSM", "Sandesh");

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

        // Set OnClickListener for the "Add Task" button
        Button addTaskButton = view.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from UI elements
                String id = "";
                String projectName = projectNameSpinner.getSelectedItem().toString();
                String taskName = taskNameInput.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();
                String priority = prioritySpinner.getSelectedItem().toString();
                String description = descriptionInput.getText().toString();
                String taskAssignee = taskAssigneeSpinner.getSelectedItem().toString();

                if(projectName.equals("") || taskName.equals("")){
                    String errorMessage = "Invalid input: Some fields are empty or don't follow the criteria.";
                    showErrorDialog(errorMessage);
                } else{
                    addTaskToFirestore(id, taskName, taskAssignee, description, priority, projectName, status);
                }
            }
        });
    }
    //String id, String taskName, String assignee, String description, String priority, String projectName, String status
    // Method to add a task to Firestore
    private void addTaskToFirestore(String id, String taskName, String assignee, String description, String priority, String projectName, String status) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Create a new task document in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference dbTask = db.collection("Tasks");
                    Task task = new Task(id,taskName, assignee, description, priority, projectName, status);
                    dbTask.add(task)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                showSuccessDialog("Task added successfully");
                                MainActivity mainActivity = (MainActivity) getActivity();
                                assert mainActivity != null;
                                mainActivity.redirectToDashboard();
                            }
                            else{
                                showErrorDialog("Failed to add task");
                            }
                        }
                    });
        } else {
            showErrorDialog("User not logged in");
        }
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
            Log.d("AddTaskFragment: ","USER NOT FOUND/USER LOGGED_OUT");
        }
    }

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
            showErrorDialog("User not logged in");
        }
    }
}
