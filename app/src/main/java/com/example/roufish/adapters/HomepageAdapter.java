package com.example.roufish.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.DescriptionProduct;
import com.example.roufish.ListProduct;
import com.example.roufish.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomepageAdapter extends RecyclerView.Adapter<com.example.roufish.adapters.HomepageAdapter.ViewHolder> {

        private ArrayList<ListProduct> products;

        public HomepageAdapter(ArrayList<ListProduct> productList) {
            this.products = productList;
        }

        @NonNull
        @Override
        public com.example.roufish.adapters.HomepageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
            return new com.example.roufish.adapters.HomepageAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.roufish.adapters.HomepageAdapter.ViewHolder holder, int position) {
            ListProduct product = products.get(position);
            Picasso.get().load(product.getImageResId()).into(holder.productImageView);
            holder.productNameTextView.setText(product.getName());
            holder.productPriceTextView.setText(String.valueOf(product.getPrice()));
            holder.productImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle image click here, for example, open a new activity with the image
                    Intent intent = new Intent(v.getContext(), DescriptionProduct.class);
                    intent.putExtra("image_url", product.getImageResId()); // Pass the image URL to the next activity
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView productImageView; // Changed to ImageView
            TextView productNameTextView;
            TextView productPriceTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productNameTextView = itemView.findViewById(R.id.productNameTextView);
                productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
                productImageView = itemView.findViewById(R.id.productImageView); // Corrected initialization
            }

        }
    }

