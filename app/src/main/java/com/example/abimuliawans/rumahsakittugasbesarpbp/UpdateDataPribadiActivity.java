package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDataPribadiActivity extends AppCompatActivity {

    private String email_data;
    private EditText nama;
    private EditText tanggallahir;
    private EditText alamat;
    private EditText tinggibadan;
    private EditText beratbadan;
    private Spinner golDarah;
    private Spinner jenisKelamin;
    private Button btnUpdate;
    private DataPribadi data;
    private FirebaseAuth firebaseAuth;
    private String userID;

    FirebaseDatabase database;
    DatabaseReference databaseDataPribadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_pribadi);

        nama = (EditText)findViewById(R.id.namaLengkap);
        tanggallahir = (EditText)findViewById(R.id.tanggalLahir);
        jenisKelamin = (Spinner)findViewById(R.id.jenisKelamin);
        alamat = (EditText)findViewById(R.id.alamat);
        tinggibadan = (EditText)findViewById(R.id.tinggiBadan);
        beratbadan = (EditText)findViewById(R.id.beratBadan);
        golDarah = (Spinner)findViewById(R.id.golonganDarah);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        firebaseAuth = FirebaseAuth.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Intent intent = new Intent(UpdateDataPribadiActivity.this,MenuUtamaActivity.class);
                startActivity(intent);
            }
        });



    }

    private void updateData() {

        String mEmail = firebaseAuth.getCurrentUser().getEmail();
        String mNama = nama.getText().toString();
        String mTanggalLahir = tanggallahir.getText().toString();
        String mJenisKelamin = jenisKelamin.getSelectedItem().toString();
        String mAlamat = alamat.getText().toString();
        String mTinggiBadan = tinggibadan.getText().toString();
        String mBeratBadan = beratbadan.getText().toString();
        String mGolonganDarah = golDarah.getSelectedItem().toString();

        userID = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference ref_user = FirebaseDatabase.getInstance().getReference("UserData").child(userID);
        DataPribadi dataPribadi = new DataPribadi(mEmail,mNama,mTanggalLahir,mJenisKelamin,mAlamat,mTinggiBadan,mBeratBadan
                ,mGolonganDarah);
        ref_user.setValue(dataPribadi);

        Toast.makeText(UpdateDataPribadiActivity.this,"Data Telah di Update",Toast.LENGTH_SHORT).show();
    }
}
