package com.taskmuse.app.ui.fragment.authentication;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.taskmuse.app.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;
import com.taskmuse.app.utils.ValidationUtils;
import androidx.annotation.Nullable;

public class Login_Fragment extends Fragment {

    private FirebaseAuth auth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private static final String TAG = "Login_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        auth = FirebaseAuth.getInstance();
        MainActivity mainActivity = (MainActivity) getActivity();

        // Initialize views
        emailEditText = view.findViewById(R.id.input_email);
        passwordEditText = view.findViewById(R.id.input_password);
        Button loginButton = view.findViewById(R.id.LoginBtn);
        Button registerLink = view.findViewById(R.id.RegisterButtonLink);
        Button forgetpassButton = view.findViewById(R.id.ForgotPasswordLink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if the input fields are not empty
                if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password)) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = auth.getCurrentUser();
                                // Show a success dialog
                                showSuccessDialog();
                            } else {
                                Log.d(TAG, "onFailure: Login Failed. Reason: " + task.getException().getMessage());
                                // Show an error dialog
                                showErrorDialog("Failed to login user.");
                                Toast.makeText(requireContext(), "Login Failed, Reason: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Show an error message to the user if any input field is empty or doesn't follow the criteria
                    String errorMessage = "Invalid input: Some fields are empty or don't follow the criteria.";

                    if (email.isEmpty() || !ValidationUtils.isValidEmail(email)) {
                        errorMessage += "\nInvalid email address.";
                    }

                    if (password.isEmpty() || !ValidationUtils.isValidPassword(password)) {
                        errorMessage += "\nPassword must be at least 8 characters long.";
                    }
                    showErrorDialog(errorMessage);
                }

            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get started button logic to be here!
                assert mainActivity != null;
                mainActivity.getRegistered();
            }
        });

        forgetpassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert mainActivity != null;
                mainActivity.goToForgotPassword();
            }
        });

        return view;
}

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Login Successful");
        builder.setMessage("The login was successful");
        MainActivity mainActivity = (MainActivity) getActivity();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            //TODO: USE THIS CODE TO MOVE TO MAIN PAGE AFTER SUCCESSFUL LOGIN
                assert mainActivity != null;
                mainActivity.redirectToDashboard();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}