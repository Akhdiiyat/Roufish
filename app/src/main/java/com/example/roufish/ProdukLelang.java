package com.example.roufish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roufish.activities.MainPageSeller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProdukLelang extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    String produkId;
    String sellerId;

    EditText inputNamaProdukLelang, inputBeratProdukLelang,inputDeskripsiProdukLelang;
    EditText inputHargaProdukLelang, inputKelipatanLelang;
    Button inputFoto,uploadProduk;
    StorageReference storageReference;
    FloatingActionButton backToMainPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_lelang);

        inputNamaProdukLelang = findViewById(R.id.inputNamaProdukLelang);
        inputBeratProdukLelang = findViewById(R.id.inputBeratProdukLelang);
        inputDeskripsiProdukLelang= findViewById(R.id.inputDeskripsiProdukLelang);
        inputHargaProdukLelang= findViewById(R.id.inputHargaProdukLelang) ;
        inputKelipatanLelang= findViewById(R.id.inputKelipatanLelang);

        inputFoto = findViewById(R.id.inputFotoLelang);
        uploadProduk = findViewById(R.id.uploadProdukLelang);

        backToMainPage = findViewById(R.id.floatingActionButton2);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Intent intent = getIntent();
        sellerId = intent.getStringExtra("sellerId");
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

        backToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainPageIntent = new Intent(ProdukLelang.this, MainPageSeller.class);
                startActivity(backToMainPageIntent);
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
        String nama = inputNamaProdukLelang.getText().toString();
        String harga = inputHargaProdukLelang.getText().toString();
        String deskripsi = inputDeskripsiProdukLelang.getText().toString();
        String kelipatan = inputKelipatanLelang.getText().toString();
        String berat = inputBeratProdukLelang.getText().toString();

        // Membuat ID produk unik
        DocumentReference produkRef = firestore.collection("produkLelang").document();
        produkId = produkRef.getId(); // Simpan ID produk

        Map<String, Object> produk = new HashMap<>();
        produk.put("id", produkId);
        produk.put("nama", nama);
        produk.put("harga", harga);
        produk.put("deskripsi", deskripsi);
        produk.put("kelipatan", kelipatan);
        produk.put("berat", berat);
        produk.put("sellerId", sellerId);
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child("produklelang/" + produkId + ".jpg"); // Gunakan ID produk sebagai nama file
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
                                            Toast.makeText(ProdukLelang.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle kegagalan
                                            Toast.makeText(ProdukLelang.this, "Error menambahkan produk", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(ProdukLelang.this, "Error mengunggah foto", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Jika tidak ada foto yang dipilih, langsung simpan data produk
            produkRef.set(produk)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProdukLelang.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProdukLelang.this, "Error menambahkan produk", Toast.LENGTH_SHORT).show();
                        }
                    });
        };

    }
}