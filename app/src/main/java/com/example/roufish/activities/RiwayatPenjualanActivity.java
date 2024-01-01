package com.example.roufish.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.R;
import com.example.roufish.adapters.RiwayatPenjualanAdapter;
import com.example.roufish.items.listRiwayatPenjualan;

import java.util.ArrayList;

public class RiwayatPenjualanActivity extends AppCompatActivity {
    private ArrayList<listRiwayatPenjualan> riwayatPenjualan = new ArrayList<>();
    private RiwayatPenjualanAdapter riwayatPenjualanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_penjualan);

        RecyclerView recyclerView = findViewById(R.id.recyclerPesananPenjualan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RiwayatPenjualanAdapter(riwayatPenjualan));
        RiwayatPenjualanAdapter RiwayatPenjualanAdapter = new RiwayatPenjualanAdapter(riwayatPenjualan);
    }
}
