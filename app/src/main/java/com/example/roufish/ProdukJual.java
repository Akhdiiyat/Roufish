package com.example.roufish;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProdukJual extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText namaProduk, deskripsiProduk, beratProduk,hargaProduk;
    FloatingActionButton backToMainSeller;
    AppCompatButton inputFoto,uploadProduk;
    FirebaseFirestore firestore;

    FirebaseStorage storage;
    StorageReference storageRef;

    Uri imageUri;
    String produkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_jual);

        namaProduk = findViewById(R.id.inputNamaProduk);
        beratProduk = findViewById(R.id.inputBeratProduk) ;
        deskripsiProduk = findViewById(R.id.inputDeskripsiProduk);
        hargaProduk = findViewById(R.id.inputHargaProduk);

        backToMainSeller = findViewById(R.id.backToMainSeller);

        inputFoto = findViewById(R.id.inputFotoJual);
        uploadProduk= findViewById(R.id.uploadProdukJual);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        backToMainSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMain = new Intent(ProdukJual.this, MainPageSeller.class);
                startActivity(backToMain);
            }
        });

        inputFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Anda bisa menampilkan preview foto jika diinginkan
        }
    }
    private void uploadData() {
        // Mengambil data dari input fields
        String nama = namaProduk.getText().toString();
        String berat =beratProduk.getText().toString();
        String deskripsi = deskripsiProduk.getText().toString();
        String harga = hargaProduk.getText().toString();


        // Membuat ID produk unik
        DocumentReference produkRef = firestore.collection("produkJual").document();
        produkId = produkRef.getId(); // Simpan ID produk

        // Menyiapkan data untuk disimpan
        Map<String, Object> produk = new HashMap<>();
        produk.put("id", produkId);
        produk.put("nama", nama);
        produk.put("harga", harga);
        produk.put("deskripsi", deskripsi);
        produk.put("berat", berat);

        if (imageUri != null) {
            StorageReference fileReference = storageRef.child("produkjual/" + produkId + ".jpg"); // Gunakan ID produk sebagai nama file
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handle successful upload
                            produkRef.set(produk)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Handle sukses
                                            Toast.makeText(ProdukJual.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle kegagalan
                                            Toast.makeText(ProdukJual.this, "Error menambahkan produk", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(ProdukJual.this, "Error mengunggah foto", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Jika tidak ada foto yang dipilih, langsung simpan data produk
            produkRef.set(produk)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProdukJual.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProdukJual.this, "Error menambahkan produk", Toast.LENGTH_SHORT).show();
                        }
                    });
        };

    }
}