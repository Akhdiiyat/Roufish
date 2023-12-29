package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.adapters.ProductsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.roufish.activities.ProductActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DescriptionProduct extends AppCompatActivity {
    TextView nama, berat, deskripsi, harga;
    ImageView foto;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_product);
        firestore = FirebaseFirestore.getInstance();
        getDataFromFirestore();
        Button pilih = findViewById(R.id.btn_lanjutkan);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(DescriptionProduct.this, Keranjang.class);
                Toast.makeText(DescriptionProduct.this, "Produk Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainIntent = new Intent(DescriptionProduct.this, MainPageBuyer.class);
                startActivity(backToMainIntent);
            }
        });
        nama = findViewById(R.id.Text_Nama_Produk);
        berat = findViewById(R.id.text_berat);
        deskripsi = findViewById(R.id.text_desc);
        harga = findViewById(R.id.text_price);
        firestore = FirebaseFirestore.getInstance();
    }
    private void getDataFromFirestore() {
        firestore.collection("products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        // Extract data from each document
                        String name = document.getString("name");
                        String description = document.getString("description");
                        String weight = document.getString("weight");
                        double price = document.getDouble("price");

                        // Process or display the data as needed
                        // For example, you can create ListLelang objects and add them to a list
                        // productList.add(new ListLelang(name, description, price, imageUrl));

                        // Update UI elements with the retrieved data (example: display the first item)
                        nama.setText(name);
                        berat.setText(weight);
                        deskripsi.setText(description);
                        harga.setText(String.valueOf(price));

                        // Note: If you want to display a list of products, consider using a RecyclerView
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DescriptionProduct.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                });
    }
}