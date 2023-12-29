package com.example.roufish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.roufish.DescriptionProduct;
import com.example.roufish.ListProduct;
import com.example.roufish.MainPageBuyer;
import com.example.roufish.R;
import com.example.roufish.forum;
import com.example.roufish.profileBuyer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.roufish.adapters.ProductsAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;

    private ArrayList<ListProduct> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        for (int i = 0; i < 100; i++) {
            products.add(new ListProduct("Mujair", 5000, "https://placehold.co/600x400"));
        }
        firestore = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_beli);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProductsAdapter(products));
        getDataFromFirestore();
        FloatingActionButton profile = findViewById(R.id.info_profile);
        FloatingActionButton nextActivity = findViewById(R.id.rou);
        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        // Navigate to HomeActivity when Home is clicked
                        startActivity(new Intent(ProductActivity.this, MainPageBuyer.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(ProductActivity.this, forum.class));
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
    }
        public void onItemClick(int position) {
            ListProduct clickedProduct = products.get(position);

            Intent intent = new Intent(ProductActivity.this, DescriptionProduct.class);
            intent.putExtra("name", clickedProduct.getName());
            //intent.putExtra("description", clickedProduct.getDescription());
            //intent.putExtra("weight", clickedProduct.getWeight());
            intent.putExtra("price", clickedProduct.getPrice());
            // Add more information if needed
            startActivity(intent);
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
                        RecyclerView recyclerView = findViewById(R.id.recycler_view_beli);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(new ProductsAdapter(products));
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ProductActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                });

    }
}