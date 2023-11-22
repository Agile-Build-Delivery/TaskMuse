package com.taskmuse.app.utils;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.FirebaseFirestore;

public class firebaseDatabaseUtils {
    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Private constructor to prevent instantiation
    private firebaseDatabaseUtils() {
    }
    public static FirebaseFirestore getFirestoreInstance() {

        return db;
    }
}
