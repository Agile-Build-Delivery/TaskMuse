package com.taskmuse.app.ui.activity.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;
import com.taskmuse.app.ui.fragment.authentication.SignupFragment;
import com.taskmuse.app.ui.fragment.authentication.Login_Fragment;

import android.os.Bundle;

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

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, SignupFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // Name can be null
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, Login_Fragment.class, null)
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
}