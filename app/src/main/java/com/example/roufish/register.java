package com.example.roufish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class register extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        EditText inputPassword = findViewById(R.id.input_Password);

        AppCompatButton btnDaftar = findViewById(R.id.btn_daftar) ;



        FloatingActionButton backToMain = findViewById(R.id.backToMainREG);

        Button daftar = findViewById(R.id.btn_daftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, login.class);

                startActivity(registerIntent);
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(register.this, MainActivity.class);

                startActivity(registerIntent);
            }
        });


        //hide password yang diinputkan
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            boolean passwordView = false;
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                final int Right = 2;

                if(motionEvent.getAction()== motionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=inputPassword.getRight()-inputPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=inputPassword.getSelectionEnd();
                        if(passwordView){
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibilityoff_icon,0);
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordView=false;
                        }else{
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordView=true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        //pop up akun berhasil di daftarkan

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setTitle("Pendaftaran Berhasil");
                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent registerIntent = new Intent(register.this, login.class);

                        startActivity(registerIntent);
                    }
                }).setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
            }
        });
    }
}
