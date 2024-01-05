package com.example.roufish;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.roufish.activities.LoginBuyer;
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

public class RegisterSeller extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseFirestore firestore;
    EditText inputPassword, inputAlamat, inputNoHP ;
    EditText inputSellerId;
    EditText inputEmail;
    Button btnDaftar ;
    FloatingActionButton backTomain ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_seller);

        inputSellerId = findViewById(R.id.inputSellerId);
        inputPassword = findViewById(R.id.inputSellerPassword);
        inputEmail = findViewById(R.id.inputSellerEmail);
        inputAlamat = findViewById(R.id.inputSellerAlamat);
        inputNoHP = findViewById(R.id.inputSellerNoHP);
        btnDaftar = (Button) findViewById(R.id.btnDaftarSeller);
        backTomain = findViewById(R.id.backToMain);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        backTomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMain = new Intent(RegisterSeller.this, RegisterPage.class);
                startActivity(backToMain);
            }
        });

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

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                String password;
                String username;
                String alamat;
                String noHp;

                username = inputSellerId.getText().toString();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                alamat = inputAlamat.getText().toString();
                noHp = inputNoHP.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Masukkan Email");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Masukkan password");
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    inputSellerId.setError("Masukkan username");
                    return;
                }
                if (password.length() < 6) {
                    inputPassword.setError("password harus lebih dari 6 karakter");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    private String userID;

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterSeller.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("Seller").document(userID);
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSeller.this);
                            builder.setTitle("Pendaftaran Berhasil");
                            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Intent registerIntent = new Intent(RegisterSeller.this, LoginSeller.class);

                                    startActivity(registerIntent);
                                }
                            }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).create().show();
                        } else {
                            Toast.makeText(RegisterSeller.this, "Pendaftaran Gagal" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}