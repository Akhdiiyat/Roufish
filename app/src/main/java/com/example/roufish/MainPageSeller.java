package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageSeller extends AppCompatActivity {
    Button tombolLelang, tombolJual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_seller);

        tombolLelang = findViewById(R.id.btnLelang);
        tombolJual = findViewById(R.id.btn_jual);

        tombolJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent penjualan = new Intent(MainPageSeller.this, ProdukJual.class);
                startActivity(penjualan);
            }
        });

        tombolLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pelelangan = new Intent(MainPageSeller.this, ProdukLelang.class);
                startActivity(pelelangan);
            }
        });


    }
}