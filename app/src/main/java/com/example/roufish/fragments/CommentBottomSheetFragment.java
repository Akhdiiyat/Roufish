package com.example.roufish.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roufish.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentBottomSheetFragment extends BottomSheetDialogFragment {

    private EditText editTextComment;
    private Button buttonSubmitComment;
    private FirebaseFirestore firestore;
    private String forumId;

    public CommentBottomSheetFragment() {
        // Required empty public constructor
    }

    public CommentBottomSheetFragment(String forumId) {
        this.forumId = forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_comment, container, false);
        firestore = FirebaseFirestore.getInstance();
        editTextComment = view.findViewById(R.id.input_komen);
        buttonSubmitComment = view.findViewById(R.id.post_komen);

        buttonSubmitComment.setOnClickListener(v -> {
            // Implement your logic for saving comments to Firestore or other actions here
            String commentText = editTextComment.getText().toString().trim();
            if (!commentText.isEmpty()) {
                // Add your logic to save the comment
                saveCommentToFirestore(commentText);
                dismiss(); // Close the bottom sheet after submitting the comment
            }
        });

        return view;
    }

    private void saveCommentToFirestore(String commentText) {
        // Create a map to represent the comment data
        if (forumId != null && !forumId.isEmpty()) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();

                // Create a map to represent the comment data
                Map<String, Object> commentData = new HashMap<>();
                commentData.put("commentText", commentText);
                commentData.put("forumId", forumId);
                commentData.put("userId", userId); // Tambahkan userId
                commentData.put("timestamp", new Date());

                // Access the "comments" collection in Firestore and add the comment data
                firestore.collection("comments")
                        .add(commentData)
                        .addOnSuccessListener(documentReference -> {
                            // Handle success (comment added successfully)
                            // You can add additional logic here if needed
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure (comment addition failed)
                            // You can add additional error handling logic here if needed
                        });
            } else {
                // Tangani kasus ketika currentUser null
                Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Tangani kasus ketika forumId null atau kosong
            Toast.makeText(getContext(), "ForumId is null or empty.", Toast.LENGTH_SHORT).show();
        }
    }

}

