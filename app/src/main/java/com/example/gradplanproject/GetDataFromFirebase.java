package com.example.gradplanproject;

import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

/**
 * This class is a test in which we check how android communicates with Firebase.
 * We have data in Firestore(one of Firebases' applications) and this class we try to get that data.
 */
public class GetDataFromFirebase {

    /**
     * This is our first try to test getting data from Firestore.
     * This function makes a call to Firestore and if that call was successful,
     * We log and print the information that it retrieved. If it fails,
     * it will be logged as well with the message of what went wrong.
     */
    public void getData() {
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

    /**
     * This function is pulling information from fire base in a form of a document
     * @param view This is just the normal required parameter to View the stuff
     *
     */
    public void callFirebaseDocument(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("semesters").document("2019;SP")
                .collection("sections").document("CS124-01");
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

    /**
     * This function is pulling information from fire base in a form of a collection
     * @param view
     *
     */
    public void callFirebaseCollection(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("semesters").document("2019;SP").collection("sections")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Task is successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            System.out.println("In the else");
                        }
                        System.out.println("outside");
                    }
                });
    }
}
