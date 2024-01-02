package com.example.roufish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.roufish.DescriptionSeller;
import com.example.roufish.ProdukJual;
import com.example.roufish.ProdukLelang;
import com.example.roufish.R;
import com.example.roufish.adapters.SellerAdapter;
import com.example.roufish.items.ListSeller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
public class MainPageSeller extends AppCompatActivity {
    Button tombolLelang, tombolJual;
    RecyclerView recyclerView;
    SellerAdapter sellerAdapter;
    List<ListSeller> productList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_seller);

        tombolLelang = findViewById(R.id.btnLelang);
        tombolJual = findViewById(R.id.btn_jual);
        recyclerView = findViewById(R.id.recycler_view_seller);

        productList = new ArrayList<>();
        sellerAdapter = new SellerAdapter(this, productList, new SellerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListSeller item) {
                // Handle item click, e.g., start DescriptionSeller activity
                Intent intent = new Intent(MainPageSeller.this, DescriptionSeller.class);
                intent.putExtra("productName", item.getProductName());
                intent.putExtra("productPrice", item.getProductPrice());
                intent.putExtra("productImage", item.getProductImage());
                intent.putExtra("sellerId", item.getSellerId());
                intent.putExtra("id", item.getProductId());
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sellerAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();

        if (intent.hasExtra("sellerId")) {
            String sellerId = intent.getStringExtra("sellerId");
            fetchProductsBySeller(sellerId);
        }

        tombolJual.setOnClickListener(view -> {
            Intent penjualan = new Intent(MainPageSeller.this, ProdukJual.class);
            startActivity(penjualan);
        });

        tombolLelang.setOnClickListener(view -> {
            Intent pelelangan = new Intent(MainPageSeller.this, ProdukLelang.class);
            startActivity(pelelangan);
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        // Navigate to HomeActivity when Home is clicked
                        startActivity(new Intent(MainPageSeller.this, MainPageSeller.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(MainPageSeller.this, ForumActivity.class));
                        return true;
                    }
                    // Add more conditions for other items if needed
                    return false;
                }
        );
    }

    private void fetchProductsBySeller(String sellerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sellerCollection = db.collection("Seller");
        CollectionReference produkJualCollection = db.collection("produkJual");

        // Fetch data from "Seller" collection based on document ID
        sellerCollection.document(sellerId).get().addOnSuccessListener(sellerDocument -> {
            if (sellerDocument.exists()) {
                String productSellerId = sellerDocument.getId();

                // Fetch data from "produkJual" collection based on "sellerId"
                produkJualCollection.whereEqualTo("sellerId", productSellerId)
                        .addSnapshotListener(this, (value, error) -> {
                            if (error != null) {
                                Log.e("FirestoreError", "Error getting documents: ", error);
                                return;
                            }

                            productList.clear();

                            for (DocumentSnapshot productDocument : value.getDocuments()) {
                                String productId = productDocument.getString("id");
                                String productName = productDocument.getString("nama");
                                String productImage = productDocument.getString("productImage");
                                String productPrice = productDocument.getString("harga");
                                int sellPrice = Integer.parseInt(productPrice);

                                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                                        .child("produkjual/" + productDocument.getId() + ".jpg");

                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    ListSeller product = new ListSeller(productName, sellPrice, uri.toString(), productSellerId,productId);
                                    productList.add(product);
                                    runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
                                }).addOnFailureListener(exception -> {
                                    ListSeller product = new ListSeller(productName, sellPrice, "Default_image", productSellerId,productId);
                                    productList.add(product);
                                    runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
                                });
                            }
                        });
            } else {
                Log.d("FirestoreError", "Seller document not found");
            }
        }).addOnFailureListener(e -> {
            Log.e("FirestoreError", "Error getting Seller document: ", e);
        });
    }

}



