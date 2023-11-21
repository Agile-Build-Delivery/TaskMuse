package com.taskmuse.app.ui.fragment.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.fragment.task.EditTaskFragment;
import com.taskmuse.app.utils.Task;
import com.taskmuse.app.utils.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends Fragment {
    private static final String TODO_COLLECTION = "todo";
    private static final String IN_PROGRESS_COLLECTION = "inProgress";
    private static final String DONE_COLLECTION = "Done";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        AppBarLayout appBar = (AppBarLayout) view.findViewById(R.id.appbar);

        // Initialize Firebase (consider moving this to the Application class)
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerViewToDo = view.findViewById(R.id.recyclerViewToDo);
        RecyclerView recyclerViewInProgress = view.findViewById(R.id.recyclerViewInProgress);
        RecyclerView recyclerViewDone = view.findViewById(R.id.recyclerViewDone);

        // Create new LayoutManagers for each RecyclerView
        LinearLayoutManager layoutManagerToDo = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerInProgress = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerDone = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        // Set layout manager
        recyclerViewToDo.setLayoutManager(layoutManagerToDo);
        recyclerViewInProgress.setLayoutManager(layoutManagerInProgress);
        recyclerViewDone.setLayoutManager(layoutManagerDone);

        // Auto populate the the to do container with the tasks in the list
        db.collection(TODO_COLLECTION)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        Log.e("Firestore", "Error fetching tasks: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching tasks", Toast.LENGTH_SHORT).show();
                        showErrorDialog("Error fetching tasks");
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        List<Task> tasks = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            Task taskItem = document.toObject(Task.class);
                            tasks.add(taskItem);
                        }

                        // Update the RecyclerView adapter with the new tasks
                        TaskAdapter adapterToDo = new TaskAdapter(tasks, this::openTaskDetailsFragment);
                        recyclerViewToDo.setAdapter(adapterToDo);
                    } else {
                        // Handle the case where there are no tasks
                        // You might want to clear the RecyclerView or show a message to the user
                    }
                });

        // Auto populate the the in progress container with the tasks in the list
        db.collection(IN_PROGRESS_COLLECTION)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        Log.e("Firestore", "Error fetching tasks: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching tasks", Toast.LENGTH_SHORT).show();
                        showErrorDialog("Error fetching tasks");
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        List<Task> tasks = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            Task taskItem = document.toObject(Task.class);
                            tasks.add(taskItem);
                        }

                        // Update the RecyclerView adapter with the new tasks
                        TaskAdapter adapterInProgress = new TaskAdapter(tasks, this::openTaskDetailsFragment);
                        recyclerViewInProgress.setAdapter(adapterInProgress);
                    } else {
                        // Handle the case where there are no tasks
                        // You might want to clear the RecyclerView or show a message to the user
                    }
                });

        // Auto populate the the done container with the tasks in the list
        db.collection(DONE_COLLECTION)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        Log.e("Firestore", "Error fetching tasks: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching tasks", Toast.LENGTH_SHORT).show();
                        showErrorDialog("Error fetching tasks");
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        List<Task> tasks = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            Task taskItem = document.toObject(Task.class);
                            tasks.add(taskItem);
                        }
                        // Update the RecyclerView adapter with the new tasks
                        TaskAdapter adapterDone = new TaskAdapter(tasks, this::openTaskDetailsFragment);
                        recyclerViewDone.setAdapter(adapterDone);
                    } else {
                        // Handle the case where there are no tasks
                        // You might want to clear the RecyclerView or show a message to the user
                    }
                });
        return view;
    }

    private void openTaskDetailsFragment(Task task, String documentId) {
        EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(documentId);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, editTaskFragment)
                .addToBackStack(null)
                .commit();
    }

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