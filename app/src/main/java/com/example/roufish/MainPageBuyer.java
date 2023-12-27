package com.example.roufish;

import static com.example.roufish.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.roufish.activities.AuctionActivity;
import com.example.roufish.activities.ProductActivity;
import com.example.roufish.adapters.HomepageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainPageBuyer extends AppCompatActivity {

    FloatingActionButton beli,lelang,pesanan,cart, profile;
    RecyclerView recyclerView;
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


        profile = findViewById(id.info_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent =  new Intent(MainPageBuyer.this, profileBuyer.class);
                startActivity(profileIntent);
            }
        });

    }
}