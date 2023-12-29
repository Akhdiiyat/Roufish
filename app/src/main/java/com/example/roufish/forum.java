package com.example.roufish;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.adapters.ForumAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class forum extends AppCompatActivity {
    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        back = findViewById(R.id.back_forum);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(forum.this, MainPageBuyer.class);
                startActivity(backIntent);
            }
        });
        // Membuat data dummy untuk forum
        List<ListForum> forumItemList = new ArrayList<>();
        forumItemList.add(new ListForum("User1", "25 detik yang lalu", "Lorem ipsum dolor sit amet consectetur."));
        forumItemList.add(new ListForum("User2", "1 menit yang lalu", "Lorem ipsum dolor sit amet consectetur, adipisicing elit."));
        forumItemList.add(new ListForum("User2", "1 menit yang lalu", "Lorem ipsum dolor sit amet consectetur, adipisicing elit."));
        forumItemList.add(new ListForum("User2", "1 menit yang lalu", "Lorem ipsum dolor sit amet consectetur, adipisicing elit."));
        forumItemList.add(new ListForum("User2", "1 menit yang lalu", "Lorem ipsum dolor sit amet consectetur, adipisicing elit."));
        forumItemList.add(new ListForum("User2", "1 menit yang lalu", "Lorem ipsum dolor sit amet consectetur, adipisicing elit."));

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_forum);

        // Mengatur layout manager dan adapter untuk RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ForumAdapter forumAdapter = new ForumAdapter(this, forumItemList);
        recyclerView.setAdapter(forumAdapter);
    }
}