package com.example.roufish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.activities.MainPageBuyer;
import com.example.roufish.activities.RiwayatPenjualanActivity;
import com.example.roufish.items.ListProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class pembayaran extends AppCompatActivity {
    TextView namaPemesan, harga,hargaTotal;
    FloatingActionButton back;
    String productPrice;
    String productName;
    String documentId;
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
        if (intent != null) {
            productPrice = intent.getStringExtra("productPrice");
            productName = intent.getStringExtra("productName");
            documentId = intent.getStringExtra("documentId");
        }

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productName != null && productPrice != null && documentId != null) {
                    saveToFirestore(productName, productPrice, documentId);
                    Intent bayarIntent = new Intent(pembayaran.this, RiwayatPenjualanActivity.class);
                    startActivity(bayarIntent);
                } else {
                    Toast.makeText(pembayaran.this, "Data produk tidak lengkap", Toast.LENGTH_SHORT).show();
                }
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
            String productImageUrl = extras.getString("productImageUrl");
            harga.setText("Rp."+ String.valueOf(productPrice));
            hargaTotal.setText("Rp."+ String.valueOf(productPrice));
        }
        showUserData();
    }

    private void saveToFirestore(String productName, String productPrice, String documentId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> purchase = new HashMap<>();
        purchase.put("productName", productName);
        purchase.put("productPrice", productPrice);
        purchase.put("documentId", documentId);

        long currentTimeMillis = (long) System.currentTimeMillis();
        String purchaseTime = getTimeString(currentTimeMillis); // Mengonversi waktu ke string

        purchase.put("timestamp", currentTimeMillis);

        db.collection("riwayat_pembelian").add(purchase)
                .addOnSuccessListener(documentReference -> Log.d("Firebase", "DocumentSnapshot written with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w("Firebase", "Error adding document", e));
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

    private String getTimeString(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date(milliseconds);
        return sdf.format(date);
    }
}