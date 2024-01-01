package com.example.roufish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.roufish.items.ListProduct;
import com.example.roufish.R;
import com.example.roufish.profileBuyer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.roufish.adapters.ProductsAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;

    private ArrayList<ListProduct> products = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);


        firestore = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_beli);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProductsAdapter(products));
        productsAdapter = new ProductsAdapter(products);
        FloatingActionButton profile = findViewById(R.id.info_profile);
        FloatingActionButton nextActivity = findViewById(R.id.rou);
        recyclerView.setAdapter(productsAdapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        // Navigate to HomeActivity when Home is clicked
                        startActivity(new Intent(ProductActivity.this, MainPageBuyer.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(ProductActivity.this, ForumActivity.class));
                        return true;
                    }
                    // Add more conditions for other items if needed
                    return false;
                }
        );
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent(ProductActivity.this, profileBuyer.class);
                startActivity(profileintent);
            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity = new Intent(ProductActivity.this, AuctionActivity.class);
                startActivity(activity);
            }
        });

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("produkJual");


        query.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                return;
            }
            products.clear();
            for (DocumentSnapshot document : value.getDocuments()) {
                String documentId = document.getId();
                String name = document.getString("nama");
                String description = document.getString("deskripsi");
                //int startingPrice = document.getLong("harga").intValue();
                String Price = (String) document.get("harga");
                int sellPrice = Integer.parseInt(Price);
                // Construct image path using the document ID
                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                        .child("produkjual/" + document.getId() + ".jpg");

                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    ListProduct product = new ListProduct(name, description, sellPrice, uri.toString(),documentId);
                    products.add(product);
                    productsAdapter.notifyDataSetChanged();
                }).addOnFailureListener(exception -> {
                    // Handle failure (e.g., set a default image URL)
                    ListProduct product = new ListProduct(name, description, sellPrice, "Default_image",documentId);
                    products.add(product);
                });


            }
            runOnUiThread(() -> productsAdapter.notifyDataSetChanged());

        });

    }

    }
