package com.example.roufish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class register extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        FloatingActionButton backToMain = findViewById(R.id.backToMainREG);

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, MainActivity.class);

                startActivity(registerIntent);
            }
        });

        // Tambahkan kode lain yang diperlukan untuk halaman pendaftaran di sini
    }
}
