package com.example.roufish;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.activities.AuctionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PageLelang extends AppCompatActivity {
    private TextView Text_Waktu;
    private String namaUser;
    EditText detailLelang;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    // Membuat ID produk unik
    DocumentReference produkRef = firestore.collection("produkLelang").document();
    String produkId ; // Simpan ID produk
    String startingPrice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_lelang);
        Text_Waktu = findViewById(R.id.Text_Waktu);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_url");
        String itemName = intent.getStringExtra("item_name");
        startingPrice = String.valueOf(intent.getIntExtra("starting_price",0));
        //String itemDescription = intent.getStringExtra("item_description");
        produkId = intent.getStringExtra("document_id");

        ImageView imageView = findViewById(R.id.Gambar_ikan);
        TextView name = findViewById(R.id.Text_Nama_Produk);
        detailLelang = findViewById(R.id.detailLelang);
        name.setText(itemName);
        Picasso.get().load(imageUrl).into(imageView);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                DateFormat waktuformat = new SimpleDateFormat("HH:mm:ss");
                Text_Waktu.setText(waktuformat.format(new Date()));
                handler.postDelayed(this, 1000);
            }
        });
        DocumentReference produkRef = firestore.collection("produkLelang").document(produkId);

        produkRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    // Ambil harga terbaru dari snapshot
                    String hargaBaru = snapshot.getString("harga");

                    // Update tampilan dengan harga baru
                    updateDetailLelang(namaUser, hargaBaru);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
        Button masukkanHarga = findViewById(R.id.btn_masukkan_harga);
        masukkanHarga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(PageLelang.this, AuctionActivity.class);
                startActivity(backToMainintent);
            }
        });
        showUserData();
        //updateDetailLelang(namaUser, startingPrice);

    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_pelelangan);
        Button lanjutkan = bottomSheetDialog.findViewById(R.id.lanjut);
        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextHarga = bottomSheetDialog.findViewById(R.id.hargaLelang);
                String harga = editTextHarga.getText().toString();

                // Update harga di Firebase
                updateHargaDiFirebase(harga);

                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }



    private void updateHargaDiFirebase(String harga) {

        if (produkId == null) {
            Toast.makeText(this, "Produk ID tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference produkRef = firestore.collection("produkLelang").document(produkId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("harga", harga);

        // Update the document in Firestore
        produkRef.update(updates)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(PageLelang.this, "Harga berhasil diperbarui", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(PageLelang.this, "Gagal memperbarui harga: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    private void updateDetailLelang(String namaUser, String harga) {

        String detail = namaUser +" " + "dengan harga"+ " " +"Rp."+ harga;
        detailLelang.setText(detail);
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
                    namaUser = value.getString("username"); // Memperbarui variabel kelas namaUser
                    // Memperbarui detail lelang dengan nama pengguna dan harga default 0
                    updateDetailLelang(namaUser, startingPrice);
                }

            }
        });
    }

}


