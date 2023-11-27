package com.taskmuse.app.ui.activity.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.fragment.authentication.ForgetPasswordNoAccessFragment;
import com.taskmuse.app.ui.fragment.authentication.ForgotPasswordHasAccessFragment;
import com.taskmuse.app.ui.fragment.authentication.SignupFragment;
import com.taskmuse.app.ui.fragment.authentication.Login_Fragment;
import com.taskmuse.app.ui.fragment.dashboard.dashboard;
import com.taskmuse.app.ui.fragment.task.AddTaskFragment;
import com.taskmuse.app.ui.fragment.get_started.GetStartedFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Firebase Auth Reference variable
    protected DatabaseReference mDatabase; // Firebase Database Reference variable
    FirebaseFirestore db; // Firestore Database Reference variable
    private FirebaseAnalytics mFirebaseAnalytics;// Firebase Analytics Reference variable
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // Firebase Auth initialized to get instance
        db = FirebaseFirestore.getInstance(); // Access a Cloud Firestore instance from your Activity
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); // // Obtain the FirebaseAnalytics instance.

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, dashboard.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // Name can be null
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, SignupFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // Name can be null
                    .commit();
        }
    }
    public void getRegistered(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SignupFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // Name can be null
                .commit();
    }
    public void alreadyUser(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Login_Fragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // Name can be null
                .commit();
    }
    public void goToLoginFragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Login_Fragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // Name can be null
                .commit();
    }
    public void goToForgotPassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, ForgotPasswordHasAccessFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // Name can be null
                    .commit();
        } else {
            // No user is signed in
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, ForgetPasswordNoAccessFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // Name can be null
                    .commit();
        }
    }
    public void redirectToDashboard(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, dashboard.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
    public void redirectToGetStarted(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, GetStartedFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
    //TODO: Replace dashboard class with add task class when implementing addTask page
    //For now will redirect to dashboard
    public void redirectToAddTask(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, dashboard.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void redirectToAddTask(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AddTaskFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
    public void showLogoutDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Sign Out User
                FirebaseAuth.getInstance().signOut();
                goToLoginFragment(); // Go to Login Page
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}