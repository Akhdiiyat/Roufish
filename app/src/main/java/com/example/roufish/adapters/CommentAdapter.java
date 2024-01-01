    package com.example.roufish.adapters;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.roufish.Comment;
    import com.example.roufish.R;
    import com.google.firebase.firestore.DocumentChange;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.text.SimpleDateFormat;
    import java.util.List;
    import java.util.Locale;

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        private List<Comment> comments;
        private Context context;
        private FirebaseFirestore firestore;
        public CommentAdapter(Context context, List<Comment> comments) {
            this.context = context;
            this.comments = comments;
        }
        private void loadComments(String forumId) {
            firestore.collection("comments")
                    .whereEqualTo("forumId", forumId)
                    .addSnapshotListener((value, error) -> {
                        if (value != null && !value.isEmpty()) {
                            for (DocumentChange documentChange : value.getDocumentChanges()) {
                                switch (documentChange.getType()) {
                                    // ...
                                }
                            }
                        }
                    });
        }
        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Comment comment = comments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder {
            private TextView commentTextTextView;
            private TextView usernameTextView;
            private TextView timestampTextView;

            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                commentTextTextView = itemView.findViewById(R.id.text_comment);
                usernameTextView = itemView.findViewById(R.id.text_usercomment);
                timestampTextView = itemView.findViewById(R.id.text_waktucomment);
            }

            public void bind(Comment comment) {
                commentTextTextView.setText(comment.getCommentText());
                usernameTextView.setText(comment.getUsername());

                if (comment.getTimestamp() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    String formattedDate = sdf.format(comment.getTimestamp());
                    timestampTextView.setText(formattedDate);
                } else {
                    timestampTextView.setText("Timestamp not available");
                }
            }
        }
    }
