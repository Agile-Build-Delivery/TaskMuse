package com.taskmuse.app.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.taskmuse.app.R;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Task task, String documentId);
    }

    public TaskAdapter(List<Task> taskList, OnItemClickListener onItemClickListener) {
        this.taskList = taskList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Bind data to the ViewHolder
        holder.taskNameTextView.setText("Task Name: " + task.getTaskName());
        holder.assigneeTextView.setText("Assignee: " + task.getAssignee());
        holder.priorityTextView.setText("Priority: " + task.getPriority());

        // Set click listener for the item
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                // Pass the task and document ID to the callback
                onItemClickListener.onItemClick(task, getDocumentId(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        TextView assigneeTextView;
        TextView priorityTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views in the ViewHolder
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            assigneeTextView = itemView.findViewById(R.id.assigneeTextView);
            priorityTextView = itemView.findViewById(R.id.priorityTextView);
        }
    }
    // Method to get the document ID at a specific position
    private String getDocumentId(int position) {
        // Replace this with your logic to get the document ID
        return "DocumentIdPlaceholder";
    }
}
