package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDataPribadi extends AppCompatActivity {

    private static final String TAG = "ViewDataPribadi";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userID;
    private ArrayAdapter<String> adapter;
    private List<String> userList;

    private ListView listView;
    private TextView textNama;
    private Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_pribadi);

        listView = (ListView)findViewById(R.id.listViewData);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        userID= user.getUid();
        userList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("UserData");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                String user_nama= dataSnapshot.child(userID).child("dataNama").getValue(String.class);
                String user_alamat = dataSnapshot.child(userID).child("dataAlamat").getValue(String.class);
                String user_berat = dataSnapshot.child(userID).child("dataBeratBadan").getValue(String.class);
                String user_tinggi = dataSnapshot.child(userID).child("dataTinggiBadan").getValue(String.class);
                String user_email = dataSnapshot.child(userID).child("dataEmail").getValue(String.class);
                String user_golonganDarah = dataSnapshot.child(userID).child("dataGolonganDarah").getValue(String.class);
                String user_tanggal = dataSnapshot.child(userID).child("dataTanggalLahir").getValue(String.class);
                String user_jenisKelamin = dataSnapshot.child(userID).child("dataJenisKelamin").getValue(String.class);

                userList.add("Nama           :" +user_nama);
                userList.add("Alamat         :" +user_alamat);
                userList.add("Berat Badan    :" +user_berat);
                userList.add("Tinggi Badan   :" +user_tinggi);
                userList.add("Email          :" +user_email);
                userList.add("Golongan Darah :" +user_golonganDarah);
                userList.add("Tanggal Lahir  :" +user_tanggal);
                userList.add("Jenis Kelamin  :" +user_jenisKelamin);

                adapter = new ArrayAdapter<>(ViewDataPribadi.this,android.R.layout.simple_list_item_1,userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDataPribadi.this,UpdateDataPribadiActivity.class);
                startActivity(intent);
            }
        });

    }


}
