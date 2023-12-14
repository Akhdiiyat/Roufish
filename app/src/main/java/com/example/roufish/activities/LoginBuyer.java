package com.example.roufish.activities;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.roufish.Homepage;
import com.example.roufish.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginBuyer extends AppCompatActivity {

    FirebaseAuth mAuth;
    FloatingActionButton backToMain ;
    EditText editTextUsername ;
    EditText editTextpassword ;
    TextView lupaPassword;
    CheckBox checkPassword ;
    AppCompatButton loginBtn ;
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_buyer);
        mAuth = FirebaseAuth.getInstance();
        backToMain = findViewById(R.id.backToMain);
        editTextUsername = findViewById(R.id.input_Username);
        editTextpassword = findViewById(R.id.input_Password);
        checkPassword = (CheckBox) findViewById(R.id.tampilkanPassword);
        loginBtn = findViewById(R.id.btn_login);
        lupaPassword = findViewById(R.id.lupaPassword);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
            finish();
        }


        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editTextpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginBuyer.this, Homepage.class);
                startActivity(registerIntent);
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
                                Toast.makeText(LoginBuyer.this,"Reset Link Sent to Your Mail",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginBuyer.this,"Eror ! Reset Link is Not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });


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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = null;
                String password = null;

                email = editTextUsername.getText().toString().trim();
                password = editTextpassword.getText().toString().trim();
                //check empty
                if(TextUtils.isEmpty(email)){
                    editTextUsername.setError("Masukkan Email");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    editTextpassword.setError("Masukkan password");
                    return;
                }

                if(password.length() < 6) {
                    editTextpassword.setError("password harus lebih dari 6 karakter");
                    return;
                }

                //sign in process

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginBuyer.this,"Login berhasil",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        }else {
                            Toast.makeText(LoginBuyer.this,"Login Gagal" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Log.e("LoginError", "onComplete: " + task.getException().getMessage(), task.getException());

                        }
                    }
                    });
                }


            });
        };

    }
