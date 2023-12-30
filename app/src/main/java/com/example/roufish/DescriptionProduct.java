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
import com.squareup.picasso.Picasso;

public class DescriptionProduct extends AppCompatActivity {
    TextView nama, berat, deskripsi, harga;
    ImageView foto;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_product);
        firestore = FirebaseFirestore.getInstance();
        Button pilih = findViewById(R.id.btn_lanjutkan);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(DescriptionProduct.this, Keranjang.class);
                Toast.makeText(DescriptionProduct.this, "Produk Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                startActivity(cartIntent);
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
        foto = findViewById(R.id.Gambar_ikan);
        firestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            ListProduct product = intent.getParcelableExtra("product");
            nama.setText(product.getName());
            //berat.setText(product.getWeight());  // Assuming you have a getWeight() method in ListProduct
            deskripsi.setText(product.getDeskripsi());
            harga.setText(String.valueOf(product.getPrice()));
            loadProductImage(product.getImageResId());

        } else {
            Toast.makeText(this, "No product information available", Toast.LENGTH_SHORT).show();
        }
        getDataFromFirestore();

    }
    private void getDataFromFirestore() {
        firestore.collection("produkJual")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("nama");

                        if (name != null && name.equals(nama.getText().toString())) {
                            String description = document.getString("deskripsi");
                            String weight = document.getString("berat");
                            String Price = (String) document.get("harga");
                            int sellPrice = Integer.parseInt(Price);

                            // Update the text fields for the selected product
                            berat.setText(weight);
                            deskripsi.setText(description);
                            harga.setText(String.valueOf(Price));

                            break;
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DescriptionProduct.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadProductImage(String imageUrl) {
        // Load the image using Picasso into the ImageView
        Picasso.get().load(imageUrl).into(foto);
    }
}