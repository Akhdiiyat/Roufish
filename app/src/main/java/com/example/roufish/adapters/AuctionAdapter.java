package com.example.roufish.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.items.ListLelang;
import com.example.roufish.R;
import com.example.roufish.PageLelang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.ViewHolder> {


    private ArrayList<ListLelang> productList;

    private static OnProductClickListener onProductClickListener;

    /*public AuctionAdapter(ArrayList<ListLelang> productList) {
        this.productList = productList;
    }*/

    public AuctionAdapter(ArrayList<ListLelang> productList) {
        this.productList = productList;
    }
    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
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
        // Load the image from Firebase Storage URL
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(product.getImageUrl())
                    .into(holder.productAuctionImageView);
        } else {
           holder.productAuctionImageView.setImageResource(R.drawable.logo);
        }

        // Menetapkan teks pada TextView berdasarkan data produk
        holder.productAuctionNameTextView.setText(product.getItemName());
        holder.productStartingPriceTextView.setText("Rp." + String.valueOf(product.getStartingPrice()));
        holder.productDescriptionTextView.setText(product.getItemDescription());

        // Menambahkan onClickListener untuk setiap item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengirimkan data produk ke PageLelang atau aktivitas lain
                Intent intent = new Intent(v.getContext(), PageLelang.class);
                intent.putExtra("product_data", String.valueOf(product));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productAuctionImageView;
        TextView productAuctionNameTextView;
        TextView productStartingPriceTextView;
        TextView productDescriptionTextView;
        TextView productWeightTextView; // TextView baru untuk berat
        TextView productIncrementTextView; // TextView baru untuk kelipatan

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productAuctionImageView = itemView.findViewById(R.id.productAuctionImageView);
            productAuctionNameTextView = itemView.findViewById(R.id.productAuctionNameTextView);
            productStartingPriceTextView = itemView.findViewById(R.id.productStartingPriceTextView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            productAuctionImageView.setOnClickListener(this);
            //onProductClickListener = listener;
            /*productWeightTextView = itemView.findViewById(R.id.productWeightTextView); // ID baru untuk berat
            productIncrementTextView = itemView.findViewById(R.id.productIncrementTextView); // ID baru untuk kelipatan*/
        }
        @Override
        public void onClick(View v) {
            if(onProductClickListener != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onProductClickListener.onProductClick(position);
                }
            }
        }
    }
    public interface OnProductClickListener {
        void onProductClick(int position);
    }
}
