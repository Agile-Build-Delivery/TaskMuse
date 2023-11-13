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


public class ForgotPasswordHasAccessFragment extends Fragment {

    private FirebaseAuth auth;
    private EditText emailEditText, passwordEditText, repeatPasswordEditText;
    private static final String TAG = "Forgot_Password_Has_Access_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_has_access, container, false);

        auth = FirebaseAuth.getInstance();
        MainActivity mainActivity = (MainActivity) getActivity();

        // Initialize views
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditText);
        Button forgetpassButton = view.findViewById(R.id.resetPasswordBtn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        forgetpassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordRepeat = repeatPasswordEditText.getText().toString();

                // Check if the input fields are not empty
                if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password) && ValidationUtils.isValidPassword(passwordRepeat)) {
                    if (password.equals(passwordRepeat)) {
                        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Show a success dialog
                                    showSuccessDialog();
                                } else {
                                    Log.d(TAG, "onFailure: Reset Failed. Reason: " + task.getException().getMessage());
                                    // Show an error dialog
                                    showErrorDialog("Failed to send reset email.");
                                    Toast.makeText(requireContext(), "Reset Failed, Reason: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        showErrorDialog("Passwords do not match. Try again.");
                    }
                } else {
                    // Show an error message to the user if any input field is empty or doesn't follow the criteria
                    String errorMessage = "Invalid input: Some fields are empty or don't follow the criteria.";

                    if (email.isEmpty() || !ValidationUtils.isValidEmail(email)) {
                        errorMessage += "\nInvalid email address.";
                    }
                    if (password.isEmpty() || !ValidationUtils.isValidPassword(password)) {
                        errorMessage += "\nPassword must be at least 8 characters long.";
                    }
                    if (passwordRepeat.isEmpty() || !ValidationUtils.isValidPassword(passwordRepeat)) {
                        errorMessage += "\nPassword must be at least 8 characters long.";
                    }
                    showErrorDialog(errorMessage);
                }

            }
        });
        return view;
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Password Reset successfully");
        builder.setMessage("Please log in again!");
        MainActivity mainActivity = (MainActivity) getActivity();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert mainActivity != null;
                mainActivity.goToLoginFragment();
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