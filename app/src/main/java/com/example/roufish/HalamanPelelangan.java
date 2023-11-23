package com.example.roufish;

import com.example.roufish.bottomSheet.bottomSheet_pelelangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HalamanPelelangan extends AppCompatActivity {
    private TextView Text_Waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_pelelangan);
        Text_Waktu = findViewById(R.id.Text_Waktu);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_url");
        String itemName = intent.getStringExtra("item_name");
        ImageView imageView = findViewById(R.id.Gambar_ikan);
        Picasso.get().load(imageUrl).into(imageView);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                DateFormat waktuformat = new SimpleDateFormat("HH:mm:ss");
                Text_Waktu.setText(waktuformat.format(new Date()));
                handler.postDelayed(this, 1000);
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(HalamanPelelangan.this, halamanProduct.class);
                startActivity(backToMainintent);
            }
        });
        /*
        FloatingActionButton showBottomSheetButton = findViewById(R.id.btn_masukkan_harga);
        showBottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet_pelelangan bottomSheet = new bottomSheet_pelelangan();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });
         */
    }
}
