package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class pelelangan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelelangan);

        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(pelelangan.this, halamanProduct.class);
                startActivity(backToMainintent);
            }
        });
    }
}