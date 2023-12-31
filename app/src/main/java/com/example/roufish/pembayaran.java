package com.example.roufish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class pembayaran extends AppCompatActivity {
    TextView namaPemesan, harga,hargaTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        namaPemesan = findViewById(R.id.namaPemesan);
        harga = findViewById(R.id.hargaProduk);
        hargaTotal = findViewById(R.id.Text_harga_barang);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productPrice = extras.getString("productPrice");
            harga.setText(String.valueOf(productPrice));
            hargaTotal.setText(String.valueOf(productPrice));

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