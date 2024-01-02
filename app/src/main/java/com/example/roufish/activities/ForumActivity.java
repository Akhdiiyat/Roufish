package com.example.roufish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.AddForum;
import com.example.roufish.R;
import com.example.roufish.adapters.ForumAdapter;
import com.example.roufish.items.ListForum;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore; // Import the necessary class
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity implements ForumAdapter.OnCommentButtonClickListener{

    private FloatingActionButton back, tambah;
    private List<ListForum> forumList;
    private ForumAdapter forumAdapter;
    private FirebaseFirestore firestore; // Declare the FirebaseFirestore object
    private Button comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // Initialize the FirebaseFirestore object
        firestore = FirebaseFirestore.getInstance();

        back = findViewById(R.id.back_forum);
        tambah = findViewById(R.id.buatForum);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ForumActivity.this, MainPageBuyer.class);
                startActivity(backIntent);
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambahIntent = new Intent(ForumActivity.this, AddForum.class);
                startActivity(tambahIntent);
            }
        });

        // Initialize forum data
        forumList = new ArrayList<>();

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_forum);

        // Set layout manager and adapter for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize and set adapter to RecyclerView
        forumAdapter = new ForumAdapter(this, forumList);
        forumAdapter.setOnCommentButtonClickListener(this);
        recyclerView.setAdapter(forumAdapter);

        // Load forums from Firestore
        loadForums();
    }

    private void loadForums() {
        firestore.collection("forums")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        forumList.clear(); // Clear existing data
                        for (DocumentSnapshot document : task.getResult()) {
                            ListForum forum = document.toObject(ListForum.class);
                            if (forum != null) {
                                forum.setForumId(document.getId());
                                forumList.add(forum);
                            }
                        }
                        forumAdapter.notifyDataSetChanged(); // Notify adapter of data changes
                    } else {
                        // Handle the error
                        Toast.makeText(ForumActivity.this, "Failed to load forums: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void fetchAndSetUsername(ListForum forum) {
        firestore.collection("users")
                .document(forum.getUserId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        if (username != null) {
                            forum.setUsername(username);
                            forumAdapter.notifyDataSetChanged(); // Notify adapter of data changes
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Log.e("ForumActivity", "Error fetching username: " + e.getMessage());
                });
    }
    public void onCommentButtonClick(ListForum forum) {
        // Implement the action to be taken when the comment button is clicked
        // For example, open CommentActivity with the selected forum
        Intent commentIntent = new Intent(ForumActivity.this, CommentActivity.class);
        commentIntent.putExtra("forumId", forum.getForumId());
        startActivity(commentIntent);
    }


}
