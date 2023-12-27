package com.example.roufish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roufish.activities.LoginBuyer;
import com.example.roufish.activities.ProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSeller extends AppCompatActivity {

    FloatingActionButton btnBack;
    CheckBox tampilPassword;

    EditText inputPasswordSeller, inputUsernameSeller;

    AppCompatButton loginSeller;

    FirebaseAuth mAuth;
    TextView lupaPassword;




    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);

        mAuth = FirebaseAuth.getInstance();

        btnBack = findViewById(R.id.tmbl_kembaliSeller);
        tampilPassword = findViewById(R.id.tampilkanPasswordSeller);
        inputUsernameSeller = findViewById(R.id.inputSellerId);
        inputPasswordSeller= findViewById(R.id.input_PasswordSeller);
        loginSeller = findViewById(R.id.btn_loginSeller);
        lupaPassword = findViewById(R.id.lupaPasswordSeller);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnBackIntent = new Intent(LoginSeller.this, LoginPage.class);
                startActivity(btnBackIntent);
            }
        });

        tampilPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    inputPasswordSeller.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inputPasswordSeller.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });



        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog =new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email To Received Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail =resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginSeller.this,"Reset Link Sent to Your Mail",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginSeller.this,"Eror ! Reset Link is Not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                        passwordResetDialog.create().show();

                        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
                });




            };
        });

        loginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = null;
                String password = null;

                email = inputUsernameSeller.getText().toString().trim();
                password = inputPasswordSeller.getText().toString().trim();
                //check empty
                if(TextUtils.isEmpty(email)){
                    inputUsernameSeller.setError("Masukkan Email");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    inputPasswordSeller.setError("Masukkan password");
                    return;
                }

                if(password.length() < 6) {
                    inputPasswordSeller.setError("password harus lebih dari 6 karakter");
                    return;
                }

                //sign in process

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginSeller.this,"Login berhasil",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainPageSeller.class));
                        }else {
                            Toast.makeText(LoginSeller.this,"Login Gagal" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Log.e("LoginError", "onComplete: " + task.getException().getMessage(), task.getException());

                        }
                    }
                });
            }


        });
    };

















            }