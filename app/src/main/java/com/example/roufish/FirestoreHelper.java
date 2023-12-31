package com.example.roufish;
import com.example.roufish.items.ListForum;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreHelper {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference forumsCollection = firestore.collection("forums");

    // Add forum to Firestore
    public void addForum(ListForum forum) {
        forumsCollection.add(forum);
    }

    // Get all forums from Firestore
    public void getForums(FirestoreCallback<List<ListForum>> callback) {
        forumsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ListForum> forums = task.getResult().toObjects(ListForum.class);
                        callback.onCallback(forums);
                    } else {
                        // Handle errors
                        callback.onCallback(null);
                    }
                });
    }

    // Add comment to a specific forum post in Firestore
//    public void addComment(String forumId, Comment comment) {
//        DocumentReference forumRef = forumsCollection.document(forumId);
//        forumRef.collection("comments").add(comment);
//    }
//
//    // Get comments for a specific forum post from Firestore
//    public void getComments(String forumId, FirestoreCallback<List<Comment>> callback) {
//        DocumentReference forumRef = forumsCollection.document(forumId);
//        forumRef.collection("comments")
//                .orderBy("timestamp", Query.Direction.ASCENDING)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        List<Comment> comments = task.getResult().toObjects(Comment.class);
//                        callback.onCallback(comments);
//                    } else {
//                        // Handle errors
//                        callback.onCallback(null);
//                    }
//                });
//    }

    // Firestore callback interface to handle asynchronous calls
    public interface FirestoreCallback<T> {
        void onCallback(T data);
    }
}

