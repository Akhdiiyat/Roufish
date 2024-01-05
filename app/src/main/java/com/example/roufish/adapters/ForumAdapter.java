package com.example.roufish.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.items.ListForum;
import com.example.roufish.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<ListForum> forumList;
    private Context context;
    private FirebaseFirestore firestore;
    private Button comment;
    private OnCommentButtonClickListener commentButtonClickListener;
    public interface OnCommentButtonClickListener {
        void onCommentButtonClick(ListForum forum);
    }
    public void setOnCommentButtonClickListener(OnCommentButtonClickListener listener) {
        this.commentButtonClickListener = listener;
    }

    public ForumAdapter(Context context, List<ListForum> forumList) {
        this.context = context;
        this.forumList = forumList;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        ListForum forum = forumList.get(position);
        holder.bind(forum);
        holder.buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentButtonClickListener != null) {
                    commentButtonClickListener.onCommentButtonClick(forum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {
        private TextView forumTextTextView;
        private TextView usernameTextView;
        private TextView timestampTextView;
        private Button buttonComment;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            forumTextTextView = itemView.findViewById(R.id.text_comforum);
            usernameTextView = itemView.findViewById(R.id.text_userforum);
            timestampTextView = itemView.findViewById(R.id.text_waktuforum);
            buttonComment = itemView.findViewById(R.id.button_comment);
        }

        public void bind(ListForum forum) {
            forumTextTextView.setText(forum.getForumText());
            usernameTextView.setText(forum.getUsername());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            String formattedDate = sdf.format(forum.getTimestamp());
            timestampTextView.setText(formattedDate);
            fetchUsername(forum.getUserId(), usernameTextView);
        }
        private void fetchUsername(String userId, TextView usernameTextView) {
            Log.d("ForumAdapter", "Fetching username for userId: " + userId);

            if (userId != null && !userId.isEmpty()) {
                firestore.collection("users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String username = documentSnapshot.getString("username");
                                if (username != null) {
                                    usernameTextView.setText(username);
                                } else {
                                    Log.e("ForumAdapter", "Username is null for userId: " + userId);
                                }
                            } else {
                                Log.e("ForumAdapter", "Document does not exist for userId: " + userId);
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Handle the error
                            Log.e("ForumAdapter", "Error fetching username: " + e.getMessage());
                        });
            } else {
                Log.e("ForumAdapter", "Invalid userId: " + userId);
            }
        }
    }
}
