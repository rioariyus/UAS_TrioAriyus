package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Pembayaran extends AppCompatActivity {
    private TextView text;
    private Button bayar;
    private Integer total;
    private FirebaseAuth firebaseAuth;
    private String total1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        bayar = findViewById(R.id.btnBayar);
        text = findViewById(R.id.textView21);
        Intent intent = getIntent();
        total = intent.getIntExtra("total",0);
        total1 = Integer.toString(total);
        text.setText(total1);

        firebaseAuth = FirebaseAuth.getInstance();

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaksi();
                Intent intent = new Intent(Pembayaran.this, MenuUtamaActivity.class);
                int total1 =0;
                intent.putExtra("total", total1);
                startActivity(intent);

            }
        });
    }

    private void addTransaksi() {
        String biaya = total1;
        String userID = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference ref_pendaftaran = FirebaseDatabase.getInstance().getReference("Transaksi").child(userID);
        ref_pendaftaran.child("Total").setValue(biaya);

    }

}
