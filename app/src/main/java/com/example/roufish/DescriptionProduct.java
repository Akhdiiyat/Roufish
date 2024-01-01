package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.activities.MainPageBuyer;
import com.example.roufish.items.ListProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DescriptionProduct extends AppCompatActivity {
    TextView nama, berat, deskripsi, harga;
    ImageView foto;
    private FirebaseFirestore firestore;
    //String produkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_product);
        firestore = FirebaseFirestore.getInstance();
        Button pilih = findViewById(R.id.btn_lanjutkan);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        Intent intent = getIntent();
        ListProduct product = intent.getParcelableExtra("product");
        //produkId = intent.getStringExtra("document_id");
        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cartIntent = new Intent(DescriptionProduct.this, Keranjang.class);

                if (intent != null) {
                    if (intent.hasExtra("productName")) {
                        String productName = intent.getStringExtra("productName");
                        cartIntent.putExtra("productName", productName);
                    }
                    if (intent.hasExtra("productPrice")) {
                        String productPrice = intent.getStringExtra("productPrice");
                        cartIntent.putExtra("productPrice", productPrice);
                    }
                    if (intent.hasExtra("image_url")) {
                        String imageUrl = intent.getStringExtra("image_url");
                        cartIntent.putExtra("productImageUrl", imageUrl);
                        //Toast.makeText(DescriptionProduct.this, "berhasil", Toast.LENGTH_SHORT).show();

                    }
                    if (intent.hasExtra("documentId")) {
                        String documentId = intent.getStringExtra("documentId");
                        cartIntent.putExtra("documentId", documentId);
                        //Toast.makeText(DescriptionProduct.this, "id ada" + documentId , Toast.LENGTH_SHORT).show();

                    }
                }

                //Toast.makeText(DescriptionProduct.this, "Produk Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
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
        //berat = findViewById(R.id.text_berat);
        deskripsi = findViewById(R.id.text_desc);
        harga = findViewById(R.id.text_price);
        foto = findViewById(R.id.Gambar_ikan);
        firestore = FirebaseFirestore.getInstance();

        if (intent != null) {
            if (intent.hasExtra("productName")) {
                String productName = intent.getStringExtra("productName");
                nama.setText(productName);
            }
            if (intent.hasExtra("productPrice")) {
                String productPrice = intent.getStringExtra("productPrice");
                harga.setText("Rp." + productPrice + "/KG");
            }
            if (intent.hasExtra("productDescription")) {
                String productDescription = intent.getStringExtra("productDescription");
                deskripsi.setText(productDescription);
            }
            if (intent.hasExtra("image_url")) {
                String imageUrl = intent.getStringExtra("image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(foto);
                }
            }
        }


    }

}