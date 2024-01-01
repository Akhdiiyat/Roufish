package com.example.roufish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.activities.MainPageBuyer;
import com.example.roufish.activities.RiwayatPenjualanActivity;
import com.example.roufish.items.ListProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class pembayaran extends AppCompatActivity {
    TextView namaPemesan, harga,hargaTotal;
    FloatingActionButton back;
    Button bayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        back = findViewById(R.id.backToMain);
        bayar = findViewById(R.id.btn_bayar);
        namaPemesan = findViewById(R.id.namaPemesan);
        harga = findViewById(R.id.hargaProduk);
        hargaTotal = findViewById(R.id.Text_harga_barang);
        Intent intent = getIntent();

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bayarIntent = new Intent(pembayaran.this, RiwayatPenjualanActivity.class);
                if (intent != null) {

                    if (intent.hasExtra("productPrice")) {
                        String productPrice = intent.getStringExtra("productPrice");
                        bayarIntent.putExtra("productPrice", productPrice);
                    }
                    if (intent.hasExtra("productName")) {
                        String productName = intent.getStringExtra("productName");
                        bayarIntent.putExtra("productName", productName);
                    }
                    if (intent.hasExtra("productPrice")) {
                        String productPrice = intent.getStringExtra("productPrice");
                        bayarIntent.putExtra("productPrice", productPrice);
                    }
                    if (intent.hasExtra("image_url")) {
                        String imageUrl = intent.getStringExtra("image_url");
                        bayarIntent.putExtra("productImageUrl", imageUrl);

                    }
                    if (intent.hasExtra("documentId")) {
                        String documentId = intent.getStringExtra("documentId");
                        bayarIntent.putExtra("documentId", documentId);
                       //Toast.makeText(pembayaran.this, "id ada", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(pembayaran.this, "id tidak ada", Toast.LENGTH_SHORT).show();
                    }

                }
                Toast.makeText(pembayaran.this, "berhasil", Toast.LENGTH_SHORT).show();
                startActivity(bayarIntent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(pembayaran.this, MainPageBuyer.class);
                startActivity(backIntent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productPrice = extras.getString("productPrice");
            harga.setText("Rp."+ String.valueOf(productPrice));
            hargaTotal.setText("Rp."+ String.valueOf(productPrice));
        }
        showUserData();

    }

    public void showUserData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore =FirebaseFirestore.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    namaPemesan.setText(value.getString("username"));
                }

            }
        });
    }
}