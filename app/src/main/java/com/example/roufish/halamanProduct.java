package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class halamanProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_product);

        RadioButton lelang = findViewById(R.id.lelang);

        lelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lelangIntent = new Intent(halamanProduct.this, pelelangan.class);

                startActivity(lelangIntent);

            }
        });
    }
}