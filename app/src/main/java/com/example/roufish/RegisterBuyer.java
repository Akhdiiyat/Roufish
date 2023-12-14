package com.example.roufish;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.roufish.activities.ProductActivity;
import com.example.roufish.activities.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterBuyer extends Activity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText inputPassword, inputAlamat, inputNoHP ;
    EditText inputUsername;
    EditText inputEmail;
    Button btnDaftar ;
    FloatingActionButton backToMain ;
    //Button daftar ;
    DatabaseReference reference;

    FirebaseDatabase database;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_buyer);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.input_Email);
        inputAlamat = findViewById(R.id.input_Alamat);
        inputNoHP = findViewById(R.id.input_NoHP);
        btnDaftar = (Button) findViewById(R.id.btn_daftar);
        backToMain = findViewById(R.id.backToMainREG);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //daftar = findViewById(R.id.btn_daftar);


        //reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");




       /* daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, login.class);

                startActivity(registerIntent);
            }
        });*/
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
            finish();
        }
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterBuyer.this, Homepage.class);

                startActivity(registerIntent);
            }
        });


        //hide password yang diinputkan
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            boolean passwordView = false;

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                final int Right = 2;

                if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= inputPassword.getRight() - inputPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = inputPassword.getSelectionEnd();
                        if (passwordView) {
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibilityoff_icon, 0);
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordView = false;
                        } else {
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_icon, 0);
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordView = true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        //pendaftaran akun
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                String password;
                String username;
                String alamat;
                String noHp;

                username = inputUsername.getText().toString();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                alamat = inputAlamat.getText().toString();
                noHp = inputNoHP.getText().toString();
                //check empty
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Masukkan Email");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Masukkan password");
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    inputUsername.setError("Masukkan username");
                    return;
                }

                if (password.length() < 6) {
                    inputPassword.setError("password harus lebih dari 6 karakter");
                    return;
                }

                    //masukkan data ke firebase

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterBuyer.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("username", username);
                                user.put("email", email);
                                user.put("alamat", alamat);
                                user.put("noHP", noHp);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess : user profile created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure " + e.toString());
                                    }
                                });

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterBuyer.this);
                                builder.setTitle("Pendaftaran Berhasil");
                                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent registerIntent = new Intent(RegisterBuyer.this, login.class);

                                        startActivity(registerIntent);
                                    }
                                }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).create().show();
                            } else {
                                Toast.makeText(RegisterBuyer.this, "Pendaftaran Gagal" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }
            });

            ;

        };
    }