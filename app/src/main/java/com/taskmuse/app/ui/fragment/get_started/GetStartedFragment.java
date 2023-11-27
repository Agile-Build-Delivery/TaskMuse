package com.taskmuse.app.ui.fragment.get_started;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taskmuse.app.R;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;

public class GetStartedFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_started, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        Button getStartedButton = view.findViewById(R.id.getStartedButton);

        getStartedButton.setOnClickListener(v -> {
            assert mainActivity != null;
            mainActivity.redirectToAddTask();
        });

        return view;
    }

}