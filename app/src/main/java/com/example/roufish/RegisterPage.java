package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegisterPage extends AppCompatActivity {
    Button buttonPembeli,buttonPenjual;
    FloatingActionButton back;
    TextView backlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        buttonPembeli = findViewById(R.id.buttonRegistPembeli);
        buttonPenjual = findViewById(R.id.buttonRegistPenjual);
        back = findViewById(R.id.btn_back);
        backlogin = findViewById(R.id.sudah_punya_akun);
        buttonPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pembeliIntent = new Intent(RegisterPage.this, RegisterBuyer.class);
                startActivity(pembeliIntent);
            }
        });
        buttonPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent penjualIntent = new Intent(RegisterPage.this, RegisterSeller.class);
                startActivity(penjualIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent backIntent = new Intent(RegisterPage.this, Homepage.class);
                startActivity(backIntent);
            }
        });
        backlogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent logIntent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(logIntent);
            }
        });

    }
}