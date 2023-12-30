package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Keranjang extends AppCompatActivity {
    private TextView nama, harga,alamat,nohp;
    private FloatingActionButton backTomain;
    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        nama = findViewById(R.id.produkNama);
        harga = findViewById(R.id.produkHarga);
        gambar = findViewById(R.id.fotoProduk_keranjang);
        alamat = findViewById(R.id.jln_keranjang);
        nohp = findViewById(R.id.nohp_keranjang);

        backTomain = findViewById(R.id.backToMain_keranjang);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productImageUrl = extras.getString("productImageUrl");

            nama.setText(productName);
            harga.setText(String.valueOf(productPrice));
            Picasso.get().load(productImageUrl).into(gambar);
        }

        showUserData();
    }

    public void showUserData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();


        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    alamat.setText(value.getString("alamat"));
                    nohp.setText(value.getString("noHP"));
                }

            }
        });
    }
}
