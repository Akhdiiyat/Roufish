package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Keranjang extends AppCompatActivity {
    private TextView nama, harga,alamat,nohp;
    private ImageView gambar;
    TextView BelumAdaPilihan;
    TextView jamKeranjang;
    TextView jam;
    private SparseArray<String> radioButtonTextMap = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        nama = findViewById(R.id.produkNama);
        harga = findViewById(R.id.produkHarga);
        gambar = findViewById(R.id.fotoProduk_keranjang);
        alamat = findViewById(R.id.jln_keranjang);
        nohp = findViewById(R.id.nohp_keranjang);
        BelumAdaPilihan = findViewById(R.id.BelumAdaPilihan);
        Button pesanproduk = findViewById(R.id.pesan_produk);
        FloatingActionButton backToMain = findViewById(R.id.backToMain_keranjang);
        jamKeranjang = findViewById(R.id.jam_keranjang);
        jam = findViewById(R.id.jam);

        pesanproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pesanprodukIntent = new Intent(Keranjang.this,pembayaran.class);
                startActivity(pesanprodukIntent);
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainIntent = new Intent(Keranjang.this, DescriptionProduct.class);
                startActivity(backToMainIntent);
            }
        });

        TextView ubah = findViewById(R.id.Text_UbahKeranjang);
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode showBottomSheetDialog()
                showBottomSheetDialog();
            }
        });



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productImageUrl = extras.getString("productImageUrl");

            nama.setText(productName);
            harga.setText(String.valueOf(productPrice));
            Picasso.get().load(productImageUrl).into(gambar);
        }
        showUserData();
    }
    private void showBottomSheetDialog() {
        // Create the bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        // Set the content view of the bottom sheet
        bottomSheetDialog.setContentView(R.layout.activity_page_keranjang);

        // Find the RadioGroup and Button in the bottom sheet layout
        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup);
        Button btnLanjutkan = bottomSheetDialog.findViewById(R.id.btn_lanjutkan);

        // Set a listener for radio button changes
        radioButtonTextMap.put(R.id.Ambilditempat, "Ambil di tempat");
        radioButtonTextMap.put(R.id.Diantarkerumah, "Diantar ke rumah");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle the selected radio button using the SparseArray
                BelumAdaPilihan.setText(radioButtonTextMap.get(checkedId, ""));
            }
        });

        // Set a listener for the "Save" button click
        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do any additional actions when the "Save" button is clicked
                bottomSheetDialog.dismiss(); // Dismiss the bottom sheet
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 2); // Menambahkan 2 jam pada waktu saat ini
        String currentTime = sdf.format(calendar.getTime());
        jam.setText(currentTime);

        // Show the bottom sheet dialog
        bottomSheetDialog.show();
    }
    public void showUserData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();


        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    alamat.setText(value.getString("alamat"));
                    nohp.setText(value.getString("noHP"));
                }

            }
        });
    }
}
