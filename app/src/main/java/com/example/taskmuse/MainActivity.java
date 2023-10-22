package com.example.taskmuse;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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