package com.example.roufish.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.R;
import com.example.roufish.items.ListForum;
import com.example.roufish.items.ListProduct;
import com.example.roufish.items.listRiwayatPenjualan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RiwayatPenjualanAdapter extends RecyclerView.Adapter<RiwayatPenjualanAdapter.ViewHolder> {
    private ArrayList<listRiwayatPenjualan> riwayatPenjualanList;
    private Context context;

    public RiwayatPenjualanAdapter(ArrayList<listRiwayatPenjualan> riwayatPenjualanList) {
        this.riwayatPenjualanList = riwayatPenjualanList;
    }

    public void setRiwayatPenjualanList(ArrayList<listRiwayatPenjualan> riwayatPenjualanList) {
        this.riwayatPenjualanList = riwayatPenjualanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penjualan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        listRiwayatPenjualan riwayat = riwayatPenjualanList.get(position);

        StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                .child("produkjual/" + riwayat.getImageResId() + ".jpg");

        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri.toString()).into(holder.productImageView);
        }).addOnFailureListener(exception -> {

        });
        //Picasso.get().load(String.valueOf(imageRef)).into(holder.productImageView);
        long timeStampLong = riwayat.getTimeStamp(); // Ambil timestamp sebagai Long
        String timeStampString = convertTimestampToDateString(timeStampLong);
        holder.timeStampView.setText(timeStampString);
        holder.productNameTextView.setText(riwayat.getName());
        holder.productPriceTextView.setText("Rp." + riwayat.getPrice() + "/Kg");
    }

    @Override
    public int getItemCount() {
        return riwayatPenjualanList.size();
    }
    private String convertTimestampToDateString(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView timeStampView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productPenjualanName);
            productPriceTextView = itemView.findViewById(R.id.HargaPesanan);
            productImageView = itemView.findViewById(R.id.productPenjualanImageView);
            timeStampView = itemView.findViewById(R.id.timeStampPembelian);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


   /* private ArrayList<listRiwayatPenjualan> riwayatPenjualan;
    private Context context;
    private FirebaseFirestore firestore;

    private ProductsAdapter.OnItemClickListener listener;

    public RiwayatPenjualanAdapter(ArrayList<listRiwayatPenjualan> riwayatPenjualan) {
        this.riwayatPenjualan = riwayatPenjualan;
        this.context = context;
        this.firestore = FirebaseFirestore.getInstance();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penjualan, parent, false);
        return new RiwayatPenjualanAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RiwayatPenjualanAdapter.ViewHolder holder, int position) {
        listRiwayatPenjualan riwayat = riwayatPenjualan.get(position);
        if (riwayat.getImageResId() != null && !riwayat.getImageResId().isEmpty()) {
            Picasso.get()
                    .load(riwayat.getImageResId())
                    .into(holder.productImageView);
        } else {
            // Set a default image in case URL is null or empty
            holder.productImageView.setImageResource(R.drawable.logo);
        }
        holder.productNameTextView.setText(riwayat.getName());
        holder.productPriceTextView.setText("Rp." + riwayat.getPrice() + "/Kg");


    }

    @Override
    public int getItemCount() {
        return riwayatPenjualan.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView; // Changed to ImageView
        TextView productNameTextView;
        TextView productPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productPenjualanName);
            productPriceTextView = itemView.findViewById(R.id.HargaPesanan);
            productImageView = itemView.findViewById(R.id.productAuctionImageView); // Corrected initialization

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }

            });

        }
        public void bind(listRiwayatPenjualan riwayatPenjualan){
            productNameTextView.setText(riwayatPenjualan.getName());
            productPriceTextView.setText(String.valueOf(riwayatPenjualan.getPrice()));
            Picasso.get().load(riwayatPenjualan.getImageResId()).into(productImageView);
        }
    }*/
}
