package com.example.roufish;

import static com.example.roufish.R.layout.activity_bottom_sheet_beli_langsung;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Beli_Langsung extends AppCompatActivity {
    TextView BelumAdaPilihan;
    TextView textzero;
    private int nilaipesanan = 0;
    public void tambahpesanan(int i) {
        nilaipesanan += i;
        nilaipesanan = Math.max(0,nilaipesanan);
        textzero.setText(String.valueOf(nilaipesanan));
    }
    private void showBottomSheetDialog(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(activity_bottom_sheet_beli_langsung);
        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup);
        Button btnLanjutkan = bottomSheetDialog.findViewById(R.id.btn_lanjutkan);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle the selected radio button
                if (checkedId == R.id.Ambilditempat) {
                    // Update your TextView in the main activity
                    BelumAdaPilihan.setText("Ambil di tempat");
                } else if (checkedId == R.id.Diantarkerumah) {
                    // Update your TextView in the main activity
                    BelumAdaPilihan.setText("Diantar ke rumah");
                }
            }
        });
        bottomSheetDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_langsung);

        textzero = findViewById(R.id.btn_zero);
        Button plus = findViewById(R.id.btn_plus);
        Button minus = findViewById(R.id.btn_minus);



        FloatingActionButton backToMain = findViewById(R.id.backToMain);



        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(Beli_Langsung.this, DescriptionProduct.class);
                startActivity(backToMainintent);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tambahpesanan(1);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tambahpesanan(-1);
            }
        });
    }
}