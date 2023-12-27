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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AuctionActivity extends AppCompatActivity {
    private ArrayList<ListLelang> productList = new ArrayList<>();
    private AuctionAdapter auctionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lelang);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_lelang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AuctionAdapter(productList));
        auctionAdapter = new AuctionAdapter(productList);
        FloatingActionButton profile = findViewById(R.id.info_profile);
        FloatingActionButton nextActivity = findViewById(R.id.rou);
        recyclerView.setAdapter(auctionAdapter);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent(AuctionActivity.this, com.example.roufish.profileBuyer.class);
                startActivity(profileintent);
            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent activity = new Intent(AuctionActivity.this, ProductActivity.class);
                startActivity(activity);
            }
        });
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("produkLelang");

        query.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                return;
            }
            productList.clear();
            for (DocumentSnapshot document : value.getDocuments()) {
                String name = document.getString("nama");
                String description = document.getString("deskripsi");
                //int startingPrice = document.getLong("harga").intValue();
                String Price = (String) document.get("harga");
                int startingPrice = Integer.parseInt(Price);
                String imageUrl = document.getString("image_url");

                ListLelang lelang = new ListLelang(name, description, startingPrice, imageUrl);
                productList.add(lelang);
            }
            runOnUiThread(() -> {
                auctionAdapter.notifyDataSetChanged();
            });
        });
    }
}


