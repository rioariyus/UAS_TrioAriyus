package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ViewDataTransaksiActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userID;
    private ArrayAdapter<String> adapter;
    private List<String> transaksiList;
    private Button btnDelete;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_transaksi);

        listView = (ListView)findViewById(R.id.listViewTransaksi);
        btnDelete = (Button) findViewById(R.id.dltTransaksi);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        userID= user.getUid();
        transaksiList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String total= dataSnapshot.child(userID).child("Total").getValue(String.class);

                transaksiList.add("Total Pembelian  : Rp." +total);

                adapter = new ArrayAdapter<>(ViewDataTransaksiActivity.this,android.R.layout.simple_list_item_1,transaksiList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrasnsaksi();
            }
        });
    }

    private void deleteTrasnsaksi() {
        DatabaseReference delTransaksi = FirebaseDatabase.getInstance().getReference("Transaksi").child(userID);

        delTransaksi.removeValue();

        Toast.makeText(ViewDataTransaksiActivity.this,"Data Transaksi Terhapus", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ViewDataTransaksiActivity.this,MenuUtamaActivity.class);
        startActivity(intent);
    }
}
