package com.example.gradplanproject;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

public class getDataFromFirebase {
        public void getData2() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("semesters").document("2019;SP");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        System.out.println("yay " + document);
                    } else {
                        Log.d(TAG, "No such document");
                        System.out.println("nay " + document);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    System.out.println("nothing worked =\\");
                }
            }
        });
    }



}
