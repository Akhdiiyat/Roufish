package com.example.roufish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.roufish.ProdukJual;
import com.example.roufish.ProdukLelang;
import com.example.roufish.R;
import com.example.roufish.adapters.SellerAdapter;
import com.example.roufish.items.ListSeller;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_seller);
        tombolLelang = findViewById(R.id.btnLelang);
        tombolJual = findViewById(R.id.btn_jual);
        recyclerView = findViewById(R.id.recycler_view_seller);
        productList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference produkJualCollection = db.collection("produkJual");
        Intent intent = getIntent();
        if (intent.hasExtra("sellerId")) {
            String sellerId = intent.getStringExtra("sellerId");
            fetchProductsBySeller(sellerId);
        }
        tombolJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent penjualan = new Intent(MainPageSeller.this, ProdukJual.class);
                startActivity(penjualan);
            }
        });

        tombolLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pelelangan = new Intent(MainPageSeller.this, ProdukLelang.class);
                startActivity(pelelangan);
            }
        });
        sellerAdapter = new SellerAdapter(this, productList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(sellerAdapter);
        recyclerView.setAdapter(sellerAdapter);
        produkJualCollection.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                // Handle error
                Log.e("FirestoreError", "Error getting documents: ", error);
                return;
            }

//            productList.clear();
//
//            for (DocumentSnapshot document : value.getDocuments()) {
//                String productName = document.getString("nama");
//                String productImage = document.getString("productImage");
//                String productPrice = (String) document.get("harga");
//                int sellPrice = Integer.parseInt(productPrice);
//
//                // Construct image path using the document ID
//                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
//                        .child("produkjual/" + document.getId() + ".jpg");
//
//                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                    ListSeller product = new ListSeller(productName, sellPrice, uri.toString());
//                    productList.add(product);
//                    runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
//                }).addOnFailureListener(exception -> {
//                    // Handle failure (e.g., set a default image URL)
//                    ListSeller product = new ListSeller(productName, sellPrice, "Default_image");
//                    productList.add(product);
//                    runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
//                });
//            }
//        });

            // Inisialisasi tombol dan atur listener seperti sebelumnya
            tombolLelang = findViewById(R.id.btnLelang);
            tombolJual = findViewById(R.id.btn_jual);
        });

    }


    private void fetchProductsBySeller(String sellerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference produkJualCollection = db.collection("produkJual");

        produkJualCollection.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                Log.e("FirestoreError", "Error getting documents: ", error);
                return;
            }

            productList.clear();

            for (DocumentSnapshot document : value.getDocuments()) {
                String documentSellerId = document.getString("sellerId");
                if (sellerId.equals(documentSellerId)) {
                    String productName = document.getString("nama");
                    String productImage = document.getString("productImage");
                    String productPrice = document.getString("harga");
                    int sellPrice = Integer.parseInt(productPrice);

                    // Construct image path using the document ID
                    StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                            .child("produkjual/" + document.getId() + ".jpg");

                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        ListSeller product = new ListSeller(productName, sellPrice, uri.toString(), documentSellerId);
                        productList.add(product);
                        runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
                    }).addOnFailureListener(exception -> {
                        // Handle failure (e.g., set a default image URL)
                        ListSeller product = new ListSeller(productName, sellPrice, "Default_image", documentSellerId);
                        productList.add(product);
                        runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());
                    });
                }
            }
        });
    }
}