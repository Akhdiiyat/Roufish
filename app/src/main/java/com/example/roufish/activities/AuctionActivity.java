package com.example.roufish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.items.ListLelang;
import com.example.roufish.PageLelang;
import com.example.roufish.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.roufish.adapters.AuctionAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        auctionAdapter.setOnProductClickListener(new AuctionAdapter.OnProductClickListener(){
            @Override
            public void onProductClick(int position) {
                openPageLelang(productList.get(position));
            }
        });
        recyclerView.setAdapter(auctionAdapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        startActivity(new Intent(AuctionActivity.this, MainPageBuyer.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        startActivity(new Intent(AuctionActivity.this, forum.class));
                        return true;
                    }
                    return false;
                }
        );
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
                // Construct image path using the document ID
                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                        .child("produklelang/" + document.getId() + ".jpg");

                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    ListLelang lelang = new ListLelang(name, description, startingPrice, uri.toString());
                    productList.add(lelang);
                    auctionAdapter.notifyDataSetChanged();
                }).addOnFailureListener(exception -> {
                    // Handle failure (e.g., set a default image URL)
                    ListLelang lelang = new ListLelang(name, description, startingPrice, "default_image_url");
                    productList.add(lelang);
                });


            }
            runOnUiThread(() -> auctionAdapter.notifyDataSetChanged());

        });
    }
    private void openPageLelang(ListLelang product) {
        Intent intent = new Intent(AuctionActivity.this, PageLelang.class);
        intent.putExtra("image_url", product.getImageUrl());
        intent.putExtra("item_name", product.getItemName());
        intent.putExtra("starting_price", product.getStartingPrice());
        intent.putExtra("item_description", product.getItemDescription());
        startActivity(intent);
    }
}


