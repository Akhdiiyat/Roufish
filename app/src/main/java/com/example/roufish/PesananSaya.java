package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PesananSaya extends AppCompatActivity {
    Button btnLelang, btnJual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_saya);

        btnLelang = findViewById(R.id.button_penjualan);
        btnJual = findViewById(R.id.button_penawaran);

        btnJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Pesan_penjualan = new Intent(PesananSaya.this, PesananPenjualan.class);
                startActivity(Pesan_penjualan);
            }
        });

        btnLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Pesan_pelelangan = new Intent(PesananSaya.this, ProdukLelang.class);
                startActivity(Pesan_pelelangan);
            }
        });
    }

}