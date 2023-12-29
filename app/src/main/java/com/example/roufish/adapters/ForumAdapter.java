package com.example.roufish.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.ListForum;
import com.example.roufish.R;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<ListForum> forumItemList;
    private Context context;

    public ForumAdapter(Context context, List<ListForum> forumItemList) {
        this.context = context;
        this.forumItemList = forumItemList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        ListForum forumItem = forumItemList.get(position);

        // Set the data to views in the ViewHolder
        holder.textUserForum.setText(forumItem.getUsername());
        holder.textWaktuForum.setText(forumItem.getTimestamp());
        holder.textComForum.setText(forumItem.getComment());
        // Add more bindings as needed
    }

    @Override
    public int getItemCount() {
        return forumItemList.size();
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView textUserForum;
        TextView textWaktuForum;
        TextView textComForum;
        Button buttonLikeForm;
        Button buttonComForm;
        ImageButton profileForm;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserForum = itemView.findViewById(R.id.text_userforum);
            textWaktuForum = itemView.findViewById(R.id.text_waktuforum);
            textComForum = itemView.findViewById(R.id.text_comforum);
            buttonLikeForm = itemView.findViewById(R.id.button_likeform);
            buttonComForm = itemView.findViewById(R.id.button_comform);
            profileForm = itemView.findViewById(R.id.profile_form);
        }
    }
}