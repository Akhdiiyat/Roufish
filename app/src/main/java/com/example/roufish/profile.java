package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    //FirebaseAuth auth;
    Button logout;

    //FirebaseUser user;

    TextView profilUsername,profilEmail,profilNoHP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = findViewById(R.id.btn_logout);
        profilEmail = findViewById(R.id.profilEmail);
        profilUsername = findViewById(R.id.profilNama);
        profilNoHP = findViewById(R.id.profilNoHP);
        showUserData();
        FloatingActionButton roufish = findViewById(R.id.roufish);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
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
    public void showUserData(){
        Intent intent = getIntent();

        String namaUser = intent.getStringExtra("username");
        String emailUser = intent.getStringExtra("email");
        String noHPUser = intent.getStringExtra("NoHP");

        profilUsername.setText(namaUser);
        profilEmail.setText(emailUser);
        profilNoHP.setText(noHPUser);
    }
}