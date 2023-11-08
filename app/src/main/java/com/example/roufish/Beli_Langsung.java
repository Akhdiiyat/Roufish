package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Beli_Langsung extends AppCompatActivity {

    TextView textzero;
    private int nilaipesanan = 0;

    public void tambahpesanan(int i) {
        nilaipesanan += i;
        nilaipesanan = Math.max(0,nilaipesanan);
        textzero.setText(String.valueOf(nilaipesanan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_langsung);

        textzero = findViewById(R.id.btn_zero);
        Button plus = findViewById(R.id.btn_plus);
        Button minus = findViewById(R.id.btn_minus);

        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(Beli_Langsung.this, halamanProduct.class);
                startActivity(backToMainintent);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tambahpesanan(1);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tambahpesanan(-1);
            }
        });


    }



}