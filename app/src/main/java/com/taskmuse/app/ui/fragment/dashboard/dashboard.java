package com.taskmuse.app.ui.fragment.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.fragment.task.EditTaskFragment;
import com.taskmuse.app.model.Task;
import com.taskmuse.app.utils.TaskAdapter;
import com.taskmuse.app.utils.firebaseDatabaseUtils;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends Fragment {

    private static final String STATUS_TODO = "ToDo";
    private static final String STATUS_IN_PROGRESS = "InProgress";
    private static final String STATUS_DONE = "Done";

    private RecyclerView recyclerViewToDo;
    private RecyclerView recyclerViewInProgress;
    private RecyclerView recyclerViewDone;
    private EditText searchEditText;

    private List<Task> allTasks = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // Initialize views
        initViews(view);

        // Setup RecyclerViews
        setupRecyclerViews();

        // Fetch and populate tasks for each status
        fetchAndPopulateTasks(STATUS_TODO, recyclerViewToDo, "");
        fetchAndPopulateTasks(STATUS_IN_PROGRESS, recyclerViewInProgress, "");
        fetchAndPopulateTasks(STATUS_DONE, recyclerViewDone, "");

        searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter tasks based on the search query
                filterTasks(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    // Initialize RecyclerViews
    private void initViews(View view) {
        recyclerViewToDo = view.findViewById(R.id.recyclerViewToDo);
        recyclerViewInProgress = view.findViewById(R.id.recyclerViewInProgress);
        recyclerViewDone = view.findViewById(R.id.recyclerViewDone);
    }

    // Setup RecyclerViews with LinearLayoutManager
    private void setupRecyclerViews() {
        setupRecyclerView(recyclerViewToDo);
        setupRecyclerView(recyclerViewInProgress);
        setupRecyclerView(recyclerViewDone);
    }

    // Setup a RecyclerView with LinearLayoutManager
    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    // Fetch and populate tasks based on the specified status
    private void fetchAndPopulateTasks(String status, RecyclerView recyclerView, String query) {
        FirebaseFirestore firestore = firebaseDatabaseUtils.getFirestoreInstance();
        com.google.firebase.firestore.Query baseQuery = firestore.collection("Tasks")
                .whereEqualTo("status", status);

        baseQuery.addSnapshotListener((value, error) -> {
            if (error != null) {
                handleFetchError(error.getMessage());
                return;
            }
            if (value != null && !value.isEmpty()) {
                allTasks = extractTasksFromSnapshot(value);
                List<Task> filteredTasks = filterTasksByName(allTasks, query.toLowerCase());
                updateRecyclerView(recyclerView, filteredTasks);
            } else {
                Log.d("DashboardFragment", "No tasks found");
                updateRecyclerView(recyclerView, new ArrayList<>()); // Clear the RecyclerView
            }
        });
    }
    // Method to filter tasks based on the search query
    private void filterTasks(String query) {
        fetchAndPopulateTasks(STATUS_TODO, recyclerViewToDo, query);
        fetchAndPopulateTasks(STATUS_IN_PROGRESS, recyclerViewInProgress, query);
        fetchAndPopulateTasks(STATUS_DONE, recyclerViewDone, query);
    }

    private List<Task> filterTasksByName(List<Task> tasks, String query) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            String taskName = task.getTaskName().toLowerCase();
            // Check for various conditions for a match, including partial word matches
            if (taskName.contains(query) || taskName.startsWith(query) || taskName.endsWith(query)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
    // Extract tasks from FireStore QuerySnapshot
    private List<Task> extractTasksFromSnapshot(QuerySnapshot snapshot) {
        List<Task> tasks = new ArrayList<>();
        for (QueryDocumentSnapshot document : snapshot) {
            Task taskItem = document.toObject(Task.class);
            taskItem.setId(document.getId());
            tasks.add(taskItem);
        }
        return tasks;
    }

    // Update the RecyclerView with the given list of tasks
    private void updateRecyclerView(RecyclerView recyclerView, List<Task> tasks) {
        Log.d("DashboardFragment", "Updating RecyclerView with tasks. Number of tasks: " + tasks.size());
        TaskAdapter adapter = new TaskAdapter(tasks, this::openTaskDetailsFragment);
        recyclerView.setAdapter(adapter);
    }

    // Open the EditTaskFragment for the selected task
    private void openTaskDetailsFragment(Task task, String documentId) {
        EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(documentId);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, editTaskFragment)
                .addToBackStack(null)
                .commit();
    }

    // Handle errors during data fetch and show an error dialog
    private void handleFetchError(String errorMessage) {
        Log.e("FireStore", "Error fetching tasks: " + errorMessage);
        Toast.makeText(requireContext(), "Error fetching tasks", Toast.LENGTH_SHORT).show();
        showErrorDialog("Error fetching tasks");
    }

    // Show a dialog with the specified error message
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}