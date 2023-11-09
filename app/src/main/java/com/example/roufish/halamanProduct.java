package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.roufish.databinding.ActivityHalamanProductBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class halamanProduct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_product);
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        RadioButton lelang = findViewById(R.id.lelang);
        RadioButton jual = findViewById(R.id.jual);
        Button lanjutkan = findViewById(R.id.btn_lanjutkan);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        lelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //belum di isi
            }
        });

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lelang.isChecked()){
                    Intent lelangIntent = new Intent(halamanProduct.this, pelelangan.class);
                    startActivity(lelangIntent);
                } else if (jual.isChecked()) {
                    Intent jualIntent = new Intent(halamanProduct.this, Beli_Langsung.class);
                    startActivity(jualIntent);
                }
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainIntent = new Intent(halamanProduct.this, product.class);
                startActivity(backToMainIntent);
            }
        });
    }
}