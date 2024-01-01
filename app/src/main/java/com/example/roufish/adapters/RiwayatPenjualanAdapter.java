package com.example.roufish.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.R;
import com.example.roufish.items.ListProduct;
import com.example.roufish.items.listRiwayatPenjualan;

import java.util.ArrayList;

public class RiwayatPenjualanAdapter extends RecyclerView.Adapter<RiwayatPenjualanAdapter.ViewHolder> {
    private ArrayList<listRiwayatPenjualan> riwayatPenjualan;

    private ProductsAdapter.OnItemClickListener listener;

    public RiwayatPenjualanAdapter(ArrayList<listRiwayatPenjualan> riwayatPenjualan) {
        this.riwayatPenjualan = riwayatPenjualan;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RiwayatPenjualanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_pelelangan, parent, false);
        return new RiwayatPenjualanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPenjualanAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
