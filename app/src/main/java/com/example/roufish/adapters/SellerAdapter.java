package com.example.roufish.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.R;
import com.example.roufish.items.ListSeller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    private List<ListSeller> itemList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public SellerAdapter(Context context, List<ListSeller> itemList,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListSeller item = itemList.get(position);
        holder.textProductName.setText(item.getProductName());
        holder.textPrice.setText(String.valueOf("Rp." + item.getProductPrice()));;
        holder.textDescription.setText(item.getProductDescription());
        String loggedInSellerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("SellerAdapter", "Logged-in Seller ID: " + loggedInSellerId);
        Log.d("SellerAdapter", "Product Seller ID: " + item.getSellerId());
        if (item.getSellerId().equals(loggedInSellerId)) {
            Picasso.get().load(item.getProductImage()).into(holder.foto, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("PicassoSuccess", "Image loaded successfully");
            }
            @Override
            public void onError(Exception e) {
                Log.e("PicassoError", "Error loading image: " + e.getMessage(), e);
            }
        });
        } else {
            holder.foto.setVisibility(View.GONE);
            Log.d("ImageViewVisibility", "ImageView hidden for non-logged-in seller's product");

        }
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName;
        TextView textPrice;
        TextView textDescription;
        ImageView foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.productNameTextView);
            textPrice = itemView.findViewById(R.id.productPriceTextView);
            foto = itemView.findViewById(R.id.productImageView);
            textDescription = itemView.findViewById(R.id.productDescription);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ListSeller item);
    }

}
