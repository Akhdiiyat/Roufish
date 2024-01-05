package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roufish.activities.ForumActivity;
import com.example.roufish.items.ListForum;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddForum extends AppCompatActivity {

    private EditText inputForum;
    private Button postButton;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    FloatingActionButton backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        inputForum = findViewById(R.id.input_forum);
        postButton = findViewById(R.id.post_forum);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postForum();
            }
        });
    }
    private void postForum() {
        String forumText = inputForum.getText().toString().trim();

        if (!forumText.isEmpty()) {
            FirebaseUser currentUser = auth.getCurrentUser();

            if (currentUser != null) {
                String userId = currentUser.getUid();
                String username = currentUser.getDisplayName();
                Date timestamp = new Date();
                fetchUsername(userId, forumText);
                if (username == null) {
                    Toast.makeText(this, "Username is not available. Please set your username in the app.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String forumId = firestore.collection("forums").document().getId();
                ListForum forum = new ListForum(forumText, userId, username, timestamp, forumId);

                firestore.collection("forums" )
                        .add(forum)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(AddForum.this, "Post berhasil!", Toast.LENGTH_SHORT).show();
                            inputForum.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddForum.this, "Gagal memposting data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        } else {
            Toast.makeText(this, "Isi forum tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchUsername(String userId, String forumText) {
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");

                        if (username != null) {
                            Date timestamp = new Date();
                            String forumId = firestore.collection("forums").document().getId();
                            ListForum forum = new ListForum(forumText, userId, username, timestamp, forumId);

                            saveForumToFirestore(forum);
                        } else {
                            Toast.makeText(this, "Username not found for the user ID: " + userId, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "User document not found for the user ID: " + userId, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void saveForumToFirestore(ListForum forum) {
        firestore.collection("forums")
                .add(forum)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddForum.this, "Post berhasil!", Toast.LENGTH_SHORT).show();
                    inputForum.setText(""); // Clear input after successful posting
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddForum.this, "Gagal memposting data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
