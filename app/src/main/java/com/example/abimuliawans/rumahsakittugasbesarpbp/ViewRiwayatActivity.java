package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class ViewRiwayatActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userID;
    private ArrayAdapter<String> adapter;
    private List<String> riwayatList;
    private Button btnDelete;
    private Button btnUpdate;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_riwayat);

        listView = (ListView)findViewById(R.id.listRiwayat);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        userID= user.getUid();
        riwayatList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("PendaftaranDokter");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                riwayatList.clear();

                    String dokter= dataSnapshot.child(userID).child("dokter").getValue(String.class);
                    String tanggal= dataSnapshot.child(userID).child("tanggalPendaftaran").getValue(String.class);

                    riwayatList.add("Tanggal   :" +tanggal);
                    riwayatList.add("Dokter    :" +dokter);


                adapter = new ArrayAdapter<>(ViewRiwayatActivity.this,android.R.layout.simple_list_item_1,riwayatList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRiwayatActivity.this,PendaftaranActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAntrian();
            }
        });

    }

    private void deleteAntrian() {
        DatabaseReference delAntrian = FirebaseDatabase.getInstance().getReference("PendaftaranDokter").child(userID);

        delAntrian.removeValue();
        Toast.makeText(ViewRiwayatActivity.this,"Antrian Terhapus", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ViewRiwayatActivity.this,MenuUtamaActivity.class);
        startActivity(intent);
    }
}
