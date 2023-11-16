package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductActivity extends AppCompatActivity {

    private ArrayList<ListProduct> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        for (int i = 0; i < 100; i++) {
            products.add(new ListProduct("Mujair", 5000, R.drawable.custom_input));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProductsAdapter(products));
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ProductActivty);
            adapter = ProductsAdapter(products);
        }
        FloatingActionButton profile = findViewById(R.id.info_profile);
        CardView cardView = findViewById(R.id.info_produk);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent(product.this, profile.class);
                startActivity(profileintent);
            }
        });
    }
}