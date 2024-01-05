package com.example.roufish;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PembayaranLelang extends AppCompatActivity {
    TextView penawaran,totalHarga,totkeseluruhan, namaPelelang;
    boolean radioGroupEnabled = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_lelang);
        RadioGroup radioGroup = findViewById(R.id.radioGroupPelelangan);

        penawaran = findViewById(R.id.Harga_penawaran);
        totalHarga = findViewById(R.id.total_penawaran);
        totkeseluruhan = findViewById(R.id.Text_total_harga_barang);
        namaPelelang = findViewById(R.id.namaPelelang);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroupEnabled) {
                    radioGroupEnabled = false;
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productPrice = extras.getString("harga");
            penawaran.setText(String.valueOf(productPrice));
            totalHarga.setText(String.valueOf(productPrice));
            totkeseluruhan.setText(String.valueOf(productPrice));
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
                    namaPelelang.setText(value.getString("username"));
                }
            }
        });
    }
}
