package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataPribadiActivity extends AppCompatActivity {

    private String email_data;
    private EditText nama;
    private EditText tanggallahir;
    private EditText alamat;
    private EditText tinggibadan;
    private EditText beratbadan;
    private Spinner golDarah;
    private Spinner jenisKelamin;
    private Button btnSimpan;
    private DataPribadi data;

    FirebaseDatabase database;
    DatabaseReference databaseDataPribadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pribadi);

        //email_data = getIntent().getStringExtra("email_id");
        nama = (EditText)findViewById(R.id.namaLengkap);
        tanggallahir = (EditText)findViewById(R.id.tanggalLahir);
        jenisKelamin = (Spinner)findViewById(R.id.jenisKelamin);
        alamat = (EditText)findViewById(R.id.alamat);
        tinggibadan = (EditText)findViewById(R.id.tinggiBadan);
        beratbadan = (EditText)findViewById(R.id.beratBadan);
        golDarah = (Spinner)findViewById(R.id.golonganDarah);
        btnSimpan = (Button)findViewById(R.id.btnSimpan);

        database = FirebaseDatabase.getInstance();
        databaseDataPribadi = database.getReference("dataPribadi");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importData();
            }
        });

    }

    private void importData() {
        Intent intent = new Intent(DataPribadiActivity.this,SignUpActivity.class);
        intent.putExtra("nama",nama.getText().toString());
        intent.putExtra("tanggalLahir",tanggallahir.getText().toString());
        intent.putExtra("jenisKelamin",jenisKelamin.getSelectedItem().toString());
        intent.putExtra("alamat",alamat.getText().toString());
        intent.putExtra("tinggiBadan",tinggibadan.getText().toString());
        intent.putExtra("beratBadan",beratbadan.getText().toString());
        intent.putExtra("golonganDarah",golDarah.getSelectedItem().toString());
        if (!TextUtils.isEmpty(nama.getText().toString())){
            if (!TextUtils.isEmpty(tanggallahir.getText().toString())){
                if (!TextUtils.isEmpty(alamat.getText().toString())){
                    if (!TextUtils.isEmpty(tinggibadan.getText().toString())){
                        if (!TextUtils.isEmpty(beratbadan.getText().toString())){
                            startActivity(intent);
                        }else{
                            Toast.makeText(this,"Masukan Berat Badan Anda", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"Masukan Tinggi Badan Anda", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"Masukan Alamat Anda", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Masukan Tanggal Lahir Anda", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Masukan Nama Anda", Toast.LENGTH_SHORT).show();
        }
    }


}
