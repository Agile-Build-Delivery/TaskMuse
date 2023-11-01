package com.taskmuse.app.ui.activity.SignupActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.activity.MainActivity.MainActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import com.taskmuse.app.utils.ValidationUtils;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private TextView loginLink;

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        // Initialize views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String fullName = fullNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if the input fields are not empty
                if (ValidationUtils.isValidFullName(fullName) && ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password)) {

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                //Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();

                                FirebaseUser currentUser = auth.getCurrentUser();

                                currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignupActivity.this, "Verification Email Has been Sent", Toast.LENGTH_SHORT).show();
                                        // Show a success dialog
                                        showSuccessDialog();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent. Reason: " + e.getMessage());
                                        // Show an error dialog
                                        showErrorDialog("Failed to send verification email.");
                                    }
                                });

                            } else {
                                Toast.makeText(SignupActivity.this, "SignUp Failed, Reason: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    // Show an error message to the user if any input field is empty or doesn't follow the criteria
                    String errorMessage = "Invalid input: Some fields are empty or don't follow the criteria.";

                    // Check for specific validation issues and add them to the error message
                    if (!ValidationUtils.isValidFullName(fullName)) {
                        errorMessage += "\n\nFull Name must consist of at least two words (first and last name).";
                    }

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
    }

    public void onLoginLinkClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional, to finish the current activity
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Up Successful");
        builder.setMessage("A verification email has been sent. Please check your email and click the verification link.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect to the main activity
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish(); // Optional, to finish the current activity
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(this)
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