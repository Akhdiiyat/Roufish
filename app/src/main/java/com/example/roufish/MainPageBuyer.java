package com.example.roufish;

import static com.example.roufish.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.roufish.activities.AuctionActivity;
import com.example.roufish.activities.ProductActivity;
import com.example.roufish.adapters.HomepageAdapter;
import com.example.roufish.adapters.ProductsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainPageBuyer extends AppCompatActivity {

    FloatingActionButton beli,lelang,pesanan,cart, profile;
    RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    SearchView searchView;
    private ArrayList<ListProduct> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_page_buyer);
        for (int i = 0; i < 100; i++) {
            products.add(new ListProduct("Mujair", 5000, "https://placehold.co/600x400"));
        }
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HomepageAdapter(products));
        beli = findViewById(id.beli);
        firestore = FirebaseFirestore.getInstance();
        getDataFromFirestore();
        searchView = findViewById(id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beliIntent = new Intent(MainPageBuyer.this, ProductActivity.class);
                startActivity(beliIntent);
            }
        });
        lelang = findViewById(id.lelang);
        lelang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent lelangIntent = new Intent(MainPageBuyer.this, AuctionActivity.class);
                startActivity(lelangIntent);
            }
        });
        pesanan = findViewById(id.pesanan); // belum ada XML
        cart = findViewById(id.cart); // belum ada XML
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MainPageBuyer.this, Keranjang.class);
                startActivity(cartIntent);
            }
        });
        profile = findViewById(id.info_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent =  new Intent(MainPageBuyer.this, profileBuyer.class);
                startActivity(profileIntent);
            }
        });
    }
    private void filterProducts(String query) {
        ArrayList<ListProduct> filteredProducts = new ArrayList<>();

        for (ListProduct product : products) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        recyclerView.setAdapter(new HomepageAdapter(filteredProducts));
    }
    private void getDataFromFirestore() {
        firestore.collection("products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        products.clear();
                        for (ListProduct product : queryDocumentSnapshots.toObjects(ListProduct.class)) {
                            products.add(product);
                        }
                        RecyclerView recyclerView = findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(new HomepageAdapter(products));
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainPageBuyer.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                });

    }
}