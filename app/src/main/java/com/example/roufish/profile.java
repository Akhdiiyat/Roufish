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

    TextView profileUsername,profileEmail,profilenoHP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btn_logout);
        //user = auth.getCurrentUser();
        profileEmail = findViewById(R.id.input_email);
        profileUsername = findViewById(R.id.nama);
        profilenoHP = findViewById(R.id.input_NoHP);
        //EditText editTextNama = findViewById(R.id.input_nama);
        FloatingActionButton roufish = findViewById(R.id.roufish);



        /*if (user == null){
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();
        }else {
            email.setText(user.getEmail());
        }*/

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

        showUserData();
    }

    public void showUserData(){
        Intent intent = getIntent();

        String namaUser = intent.getStringExtra("username");
        String emailUser = intent.getStringExtra("email");
        String noHPUser = intent.getStringExtra("noHP");

        profileUsername.setText(namaUser);
        profileEmail.setText(emailUser);
        profilenoHP.setText(noHPUser);


    }
}