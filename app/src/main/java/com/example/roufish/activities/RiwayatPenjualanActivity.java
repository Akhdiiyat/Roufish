package com.example.roufish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.PesananSaya;
import com.example.roufish.R;
import com.example.roufish.adapters.RiwayatPenjualanAdapter;
import com.example.roufish.items.ListForum;
import com.example.roufish.items.ListProduct;
import com.example.roufish.items.listRiwayatPenjualan;
import com.example.roufish.profileBuyer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiwayatPenjualanActivity extends AppCompatActivity {

    private RiwayatPenjualanAdapter riwayatPenjualanAdapter;
    private FirebaseFirestore firestore;
    CollectionReference purchaseHistoryRef;

    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_penjualan);

        firestore = FirebaseFirestore.getInstance();
        purchaseHistoryRef = firestore.collection("riwayat_pembelian");

        back = findViewById(R.id.floatingActionButton_pesanan);

        RecyclerView recyclerView = findViewById(R.id.recyclerPesananPenjualan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<listRiwayatPenjualan> riwayatPenjualan = new ArrayList<>();
        riwayatPenjualanAdapter = new RiwayatPenjualanAdapter(riwayatPenjualan);
        recyclerView.setAdapter(riwayatPenjualanAdapter);

        //RiwayatPenjualanAdapter RiwayatPenjualanAdapter = new RiwayatPenjualanAdapter(riwayatPenjualan);
        loadRiwayatBeli();

        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_nav_form);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        // Navigate to HomeActivity when Home is clicked
                        startActivity(new Intent(RiwayatPenjualanActivity.this, MainPageBuyer.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(RiwayatPenjualanActivity.this, ForumActivity.class));
                        return true;
                    }
                    else if (item.getItemId() == R.id.profile) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(RiwayatPenjualanActivity.this, profileBuyer.class));
                        return true;
                    }
                    // Add more conditions for other items if needed
                    return false;
                }
        );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RiwayatPenjualanActivity.this, PesananSaya.class));
            }
        });


    }

    private void loadRiwayatBeli() {

        firestore.collection("riwayat_pembelian")
                .get()
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.firestore.QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<listRiwayatPenjualan> riwayatPenjualanList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                String idPenjualan = document.getId();
                                String productName = document.getString("productName");
                                String productPrice = document.getString("productPrice");
                                String imageUrl = document.getString("documentId");

                                listRiwayatPenjualan riwayatPenjualan = new listRiwayatPenjualan(idPenjualan, productName, productPrice, imageUrl);
                                riwayatPenjualanList.add(riwayatPenjualan);
                            }

                            // Update data in adapter and RecyclerView
                            riwayatPenjualanAdapter.setRiwayatPenjualanList(riwayatPenjualanList);
                            riwayatPenjualanAdapter.notifyDataSetChanged();
                        } else {
                            // Handle error
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                            Toast.makeText(RiwayatPenjualanActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

        /*firestore.collection("riwayat_pembelian")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<listRiwayatPenjualan> riwayatPenjualan = new ArrayList<>();

                        for (DocumentSnapshot document : task.getResult()) {
                            String idPenjualan = document.getId();
                            String productName = document.getString("productName"); // Change "productName" to the actual field name in your Firestore document
                            String productPrice = document.getString("productPrice");
                            //int sellPrice = Integer.parseInt(productPrice);


                            *//*StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                                    .child("produkjual/" + id + ".jpg");

                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                listRiwayatPenjualan riwayatJual = new listRiwayatPenjualan(id, productName, productPrice,uri.toString());
                                riwayatPenjualan.add(riwayatJual);
                            }).addOnFailureListener(exception -> {
                                // Handle failure (e.g., set a default image URL)
                                listRiwayatPenjualan riwayatJual = new listRiwayatPenjualan(id, productName, productPrice,"default_Image");
                                riwayatPenjualan.add(riwayatJual);
                            });*//*
                        }
                       riwayatPenjualanAdapter.notifyDataSetChanged(); // Notify adapter of data changes
                    } else {
                        // Handle the error
                        Toast.makeText(RiwayatPenjualanActivity.this, "Failed to load forums: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });*/



