package com.example.roufish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.ListLelang;
import com.example.roufish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.roufish.adapters.AuctionAdapter;

public class AuctionActivity extends AppCompatActivity {
    private ArrayList<ListLelang> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lelang);

        for (int i = 0; i < 30; i++) {
            productList.add(new ListLelang("Mujair",
                    "Ikan yang sangat manjur",
                    4000,
                    "https://placehold.co/600x400"));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_lelang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AuctionAdapter(productList));

        FloatingActionButton profile = findViewById(R.id.info_profile);
        FloatingActionButton nextActivity = findViewById(R.id.rou);
        //CardView cardView = findViewById(R.id.info_produk2);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent(AuctionActivity.this, com.example.roufish.profile.class);
                startActivity(profileintent);
            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent activity = new Intent(AuctionActivity.this, ProductActivity.class);
                startActivity(activity);
            }
        });
    }
}


