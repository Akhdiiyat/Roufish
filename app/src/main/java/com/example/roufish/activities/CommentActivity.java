package com.example.roufish.activities;

import static com.google.api.ChangeType.ADDED;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.Comment;
import com.example.roufish.R;
import com.example.roufish.adapters.CommentAdapter;
import com.example.roufish.fragments.CommentBottomSheetFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private TextView forumTextTextView;
    private TextView usernameTextView;
    private TextView timestampTextView;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private FirebaseUser firebaseUser;
    private String forumId;

    FloatingActionButton back_forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comment);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_comment);
        // Initialize UI components
        forumTextTextView = findViewById(R.id.text_comforum);
        usernameTextView = findViewById(R.id.text_userforum);
        timestampTextView = findViewById(R.id.text_waktuforum);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);
        back_forum = findViewById(R.id.back_forum);

        // Retrieve data from Intent extras
        forumId = getIntent().getStringExtra("forumId");
        String forumId = getIntent().getStringExtra("forumId");
        loadComments(forumId);
        // Fetch forum details from Firestore based on the forumId
        fetchForumDetails(forumId);
        Button comment = findViewById(R.id.button_comment2);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentBottomSheet();
            }
        });

        back_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backForumIntent = new Intent(CommentActivity.this, ForumActivity.class);
                startActivity(backForumIntent);
            }
        });
    }
    private void showCommentBottomSheet() {
        CommentBottomSheetFragment bottomSheetFragment = new CommentBottomSheetFragment(forumId);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    private void fetchForumDetails(String forumId) {
        if (forumId != null && !forumId.isEmpty()) {
            firestore.collection("forums")
                    .document(forumId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Retrieve forum details from Firestore
                            String userId = documentSnapshot.getString("userId");
                            String forumText = documentSnapshot.getString("forumText");
                            String username = documentSnapshot.getString("username");
                            long timestamp = documentSnapshot.getDate("timestamp").getTime();
                            if (userId != null && !userId.isEmpty()) {
                                // Fetch username from "users" collection using userId
                                fetchUsername(userId);
                            }
                            // Update UI with forum details
                            updateUI(forumText, username, timestamp);
                            Log.d("CommentActivity", "Forum Text: " + forumText);
                            Log.d("CommentActivity", "Username: " + username);
                            Log.d("CommentActivity", "Timestamp: " + timestamp);
                        } else {
                            Log.e("CommentActivity", "Document does not exist for forumId: " + forumId);

                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                        Log.e("CommentActivity", "Error fetching forum details: " + e.getMessage());
                    });
        } else {
            Log.e("CommentActivity", "Invalid forumId: " + forumId);
        }
    }

    private void updateUI(String forumText, String username, long timestamp) {
        // Update UI components with forum details
        forumTextTextView.setText(forumText);
        usernameTextView.setText(username);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(timestamp));
        timestampTextView.setText(formattedDate);
    }
    private void fetchUsername(String userId) {
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        if (username != null) {
                            // Update UI with username
                            updateUsername(username);
                            Log.d("CommentActivity", "Username: " + username);
                        } else {
                            Log.e("CommentActivity", "Username is null for userId: " + userId);
                        }
                    } else {
                        Log.e("CommentActivity", "Document does not exist for userId: " + userId);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CommentActivity", "Error fetching username: " + e.getMessage());
                });
    }

    private void updateUsername(String username) {
        // Update the UI component with the fetched username
        usernameTextView.setText(username);
    }
    private void loadComments(String forumId) {
        firestore.collection("comments")
                .whereEqualTo("forumId", forumId)
                .addSnapshotListener((value, error) -> {
                    if (value != null && !value.isEmpty()) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            switch (documentChange.getType()) {
                                case ADDED:
                                    // Comment added, update the list
                                    Comment comment = documentChange.getDocument().toObject(Comment.class);
                                    if (comment != null) {
                                        fetchUsernameAndTimestamp(comment);
                                        commentList.add(comment);
                                        commentAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                // Handle other cases if needed
                            }
                        }
                    }
                });
    }
    private void fetchUsernameAndTimestamp(Comment comment) {
        String userId = comment.getUserId();

        if (userId != null) {
            firestore.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            comment.setUsername(username);

                            // Fetch timestamp from the comment document in Firestore
                            Date timestamp = comment.getTimestamp(); // Assuming the timestamp is stored directly in the comment document
                            if (timestamp != null) {
                                // Notify the adapter after fetching and setting username and timestamp
                                commentAdapter.notifyDataSetChanged();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("CommentActivity", "Error fetching username and timestamp: " + e.getMessage());
                    });
        } else {
            Log.e("CommentActivity", "User ID is null when fetching username and timestamp");
        }
    }
}
