package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout;

    FirebaseUser user;

    TextView nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btn_logout);
        user = auth.getCurrentUser();
        nama = findViewById(R.id.nama);
        //EditText editTextNama = findViewById(R.id.input_nama);
        FloatingActionButton roufish = findViewById(R.id.roufish);

        if (user == null){
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();
        }else {
            nama.setText(user.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });

        roufish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roufishintent = new Intent( profile.this, product.class);
                startActivity(roufishintent);
            }
        });
    }
}