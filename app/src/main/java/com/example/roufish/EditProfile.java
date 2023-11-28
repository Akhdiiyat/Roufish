package com.example.roufish;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText editUsername, editEmail, editNoHp;
        ImageView editImage;
        Button saveEditProfile;
        FirebaseAuth mAuth;
        FirebaseFirestore firestore;
        FirebaseUser user;


        editUsername = findViewById(R.id.profileEditUsername);
        editEmail = findViewById(R.id.profileEditEmail);
        editNoHp = findViewById(R.id.profileNomorHP);
        editImage = findViewById(R.id.profileImageView);
        saveEditProfile = findViewById(R.id.saveEditProfile);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfile.this,"Image Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUsername.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty() ||editNoHp.getText().toString().isEmpty() ) {
                    Toast.makeText(EditProfile.this,"Masih Ada Data yang Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = editEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference documentReference = firestore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("username",editUsername.getText().toString() );
                        edited.put("noHP",editNoHp.getText().toString() );
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditProfile.this,"Profile Berhasil di Update", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), profile.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this,"Penggantian Email Berhasil", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

        Intent data = getIntent();
        String username = data.getStringExtra("username");
        String email = data.getStringExtra("email");
        String noHP = data.getStringExtra("noHP");

        editUsername.setText(username);
        editEmail.setText(email);
        editNoHp.setText(noHP);

        Log.d(TAG, "onCreate: " + username + " " + email + " " + noHP);

    }
}