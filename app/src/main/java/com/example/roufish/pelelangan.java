package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pelelangan extends AppCompatActivity {
    private TextView Text_Waktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelelangan);

        Text_Waktu = findViewById(R.id.Text_Waktu);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                @SuppressLint("SimpleDateFormat")
                DateFormat waktuformat = new SimpleDateFormat("HH:mm:ss");

                Text_Waktu.setText(waktuformat.format(new Date()));

                handler.postDelayed(this,1000);
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(pelelangan.this, halamanProduct.class);
                startActivity(backToMainintent);
            }
        });
    }
}