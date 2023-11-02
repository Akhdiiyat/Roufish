package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class lupapwd_verif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FloatingActionButton backLupaCode = findViewById(R.id.backToMainREG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd_verif);
        backLupaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent codeIntent = new Intent(lupapwd_verif.this, lupapwd_code.class);
                startActivity(codeIntent);
            }
        });
    }
}