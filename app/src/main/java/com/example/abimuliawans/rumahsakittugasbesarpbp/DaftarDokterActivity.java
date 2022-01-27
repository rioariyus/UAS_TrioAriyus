package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DaftarDokterActivity extends AppCompatActivity {

    private TextView d1;
    private TextView d2;
    private TextView d3;
    private TextView d4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_dokter);

        d1= findViewById(R.id.dokter1);
        d2= findViewById(R.id.dokter2);
        d3= findViewById(R.id.dokter3);
        d4= findViewById(R.id.dokter4);

        onDetail();
    }

    public void onDetail(){
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarDokterActivity.this, Dokter1.class);
                startActivity(intent);
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarDokterActivity.this, Dokter2.class);
                startActivity(intent);
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarDokterActivity.this, Dokter3.class);
                startActivity(intent);
            }
        });

        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarDokterActivity.this, Dokter4.class);
                startActivity(intent);
            }
        });


    }


}
