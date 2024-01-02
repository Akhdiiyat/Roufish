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

import com.example.roufish.activities.MainPageSeller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProdukJual extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText namaProduk, deskripsiProduk, beratProduk, hargaProduk;
    FloatingActionButton backToMainSeller;
    AppCompatButton inputFoto, uploadProduk;
    FirebaseFirestore firestore;
    String sellerId;
    FirebaseStorage storage;
    StorageReference storageRef;

    Uri imageUri;
    String produkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_jual);

        namaProduk = findViewById(R.id.inputNamaProduk);
        beratProduk = findViewById(R.id.inputBeratProduk);
        deskripsiProduk = findViewById(R.id.inputDeskripsiProduk);
        hargaProduk = findViewById(R.id.inputHargaProduk);

        backToMainSeller = findViewById(R.id.backToMainSeller);

        inputFoto = findViewById(R.id.inputFotoJual);
        uploadProduk = findViewById(R.id.uploadProdukJual);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Intent intent = getIntent();
        sellerId = intent.getStringExtra("sellerId");

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
            // You can display a preview of the photo if desired
        }
    }

    private void uploadData() {
        // Retrieve data from input fields
        String nama = namaProduk.getText().toString();
        String berat = beratProduk.getText().toString();
        String deskripsi = deskripsiProduk.getText().toString();
        String harga = hargaProduk.getText().toString();

        // Create a unique product ID
        DocumentReference produkRef = firestore.collection("produkJual").document();
        produkId = produkRef.getId(); // Save product ID

        // Prepare data to be saved
        Map<String, Object> produk = new HashMap<>();
        produk.put("id", produkId);
        produk.put("nama", nama);
        produk.put("harga", harga);
        produk.put("deskripsi", deskripsi);
        produk.put("berat", berat);

        if (imageUri == null) {
            // If no photo is selected, save the product data directly
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
            return;  // Exit the method if there is no image
        }

        // If a photo is selected, upload the photo to Firebase Storage
        StorageReference fileReference = storageRef.child("produkjual/" + produkId + ".jpg");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Handle successful upload
                        // Retrieve sellerId based on the documentId from the "seller" collection
                        firestore.collection("Seller")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                            // Here, document.getId() will give you the document ID of each seller
                                            // You can use this ID as needed
                                            String sellerId = userId;
                                            String sellerIdFromFirestore = document.getId();
                                            if (sellerIdFromFirestore.equals(sellerId)) {
                                                // Match found, now you can associate this sellerId with your product
                                                produk.put("sellerId", sellerId);

                                                // Set product data to Firestore
                                                firestore.collection("produkJual")
                                                        .document(produkId) // Use produkId as the document ID
                                                        .set(produk)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                // Successfully added the product
                                                                Toast.makeText(ProdukJual.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // Handle failure
                                                                Toast.makeText(ProdukJual.this, "Error menambahkan produk", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                return;  // Exit the loop once the match is found
                                            }
                                        }

                                        // If no match is found
                                        Toast.makeText(ProdukJual.this, "Error: Seller not found", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure to retrieve seller documents
                                        Toast.makeText(ProdukJual.this, "Error retrieving seller documents", Toast.LENGTH_SHORT).show();
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
    }
}
