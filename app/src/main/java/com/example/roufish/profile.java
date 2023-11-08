package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText editTextNama = findViewById(R.id.input_nama);
        FloatingActionButton roufish = findViewById(R.id.roufish);

        roufish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roufishintent = new Intent( profile.this, product.class);
                startActivity(roufishintent);
            }
        });
    }
}