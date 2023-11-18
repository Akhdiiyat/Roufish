package com.example.roufish.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.ListLelang;
import com.example.roufish.R;
import com.example.roufish.halaman_pelelangan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.ViewHolder> {

    private ArrayList<ListLelang> productList;

    public AuctionAdapter(ArrayList<ListLelang> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListLelang product = productList.get(position);
        Picasso.get().load(product.getImageUrl()).into(holder.productAuctionImageView);
        holder.productAuctionNameTextView.setText(product.getItemName());
        holder.productStartingPriceTextView.setText(String.valueOf(product.getStartingPrice()));
        holder.productDescriptionTextView.setText(product.getItemDescription());
        holder.productAuctionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle image click here, for example, open a new activity with the image
                Intent intent = new Intent(v.getContext(), halaman_pelelangan.class);
                intent.putExtra("image_url", product.getImageUrl()); // Pass the image URL to the next activity
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productAuctionImageView; // Changed to ImageView
        TextView productAuctionNameTextView;
        TextView productStartingPriceTextView;
        TextView productDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productAuctionNameTextView = itemView.findViewById(R.id.productAuctionNameTextView);
            productStartingPriceTextView = itemView.findViewById(R.id.productStartingPriceTextView);
            productAuctionImageView = itemView.findViewById(R.id.productAuctionImageView); // Corrected initialization
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
        }
    }
}
