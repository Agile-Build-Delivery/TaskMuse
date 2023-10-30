package com.taskmuse.app.ui.activity.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmuse.app.R;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Firebase Auth Reference variable
    protected DatabaseReference mDatabase; // Firebase Database Reference variable
    FirebaseFirestore db; // Firestore Database Reference variable
    private FirebaseAnalytics mFirebaseAnalytics;// Firebase Analytics Reference variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // Firebase Auth initialized to get instance
        db = FirebaseFirestore.getInstance(); // Access a Cloud Firestore instance from your Activity
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); // // Obtain the FirebaseAnalytics instance.
    }
}