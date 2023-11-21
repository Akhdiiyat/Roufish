package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roufish.activities.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile extends AppCompatActivity {

    //FirebaseAuth auth;
    Button logout;

    FirebaseFirestore firestore;

    FirebaseAuth mAuth;

    TextView profileUsername, profileEmail, profileName, profilePassword, titleName, titleUsername;
    TextView profileNoHP;
    DatabaseReference reference;
    FirebaseDatabase database;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.btn_logout);

        //profilePassword = (TextView) findViewById(R.id.profilePassword);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profileUsername = (TextView) findViewById(R.id.profileUsername);
        profileName = (TextView) findViewById(R.id.profileName);
        titleName =  (TextView) findViewById(R.id.titleName);
        titleUsername = (TextView) findViewById(R.id.titleUsername);
        profileNoHP = (TextView) findViewById(R.id.profileNoHP);


        mAuth =FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();


        //FloatingActionButton roufish = findViewById(R.id.roufish);

        showUserData();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        /*roufish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roufishintent = new Intent(profile.this, ProductActivity.class);
                startActivity(roufishintent);
            }
        });*/



    }
    /*public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }*/
    public void showUserData() {

        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                profileUsername.setText(value.getString("username"));
                profileEmail.setText(value.getString("email"));
                profileNoHP.setText(value.getString("noHP"));
            }
        });
    }



}





