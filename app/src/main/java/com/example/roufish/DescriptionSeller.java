package com.example.roufish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DescriptionSeller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_description);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String sellerId = intent.getStringExtra("sellerId");
            String productId = intent.getStringExtra("id");
            String productName = intent.getStringExtra("productName");

            int productPrice = intent.getIntExtra("productPrice", 0);
            String productImage = intent.getStringExtra("productImage");
            //String sellerId = intent.getStringExtra("sellerId")
            // Use the data as needed, e.g., set text views, load images, etc.
            TextView productNameTextView = findViewById(R.id.Text_Nama_Produk_Seller);
            TextView productPriceTextView = findViewById(R.id.text_price_Seller);
            ImageView productImageView = findViewById(R.id.Gambar_ikan_Seller);

            productNameTextView.setText(productName);
            productPriceTextView.setText(String.valueOf("Rp." + productPrice));
            Button btnDeleteProduct = findViewById(R.id.btn_delete);
            btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the delete button click

                    deleteProductFromFirestore(productId);

                    Toast.makeText(DescriptionSeller.this, "Delete Produk Berhasil", Toast.LENGTH_SHORT).show();

                    // Finish the activity after deletion
                    finish();

                }
            });
            Picasso.get().load(productImage).into(productImageView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("PicassoSuccess", "Image loaded successfully");
                }

                @Override
                public void onError(Exception e) {
                    Log.e("PicassoError", "Error loading image: " + e.getMessage(), e);
                }
            });
        }

    }

    public void deleteProduct(View view) {
        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String sellerId = intent.getStringExtra("sellerId");
            String productId = intent.getStringExtra("id"); // You should pass productId from the previous activity

            deleteProductFromFirestore(productId);

            // Finish the activity after deletion
            finish();
        }
    }
    private void deleteProductFromFirestore(String productId) {

        Log.d("FirestoreDelete", "Deleting product with ID: " + productId);
        // Example code (Make sure to replace it with your actual implementation):
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("produkJual")
                .document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreSuccess", "Product deleted successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error deleting product: " + e.getMessage(), e);
                });
    }
}
