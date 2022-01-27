package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuLainyaActivity extends AppCompatActivity {

    Button dataPribadi;
    Button detailDokter;
    Button transaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lainya);

        dataPribadi = findViewById(R.id.dataPribadi);
        detailDokter = findViewById(R.id.detailDokter);
        transaksi = findViewById(R.id.dataTransaksi);


        dataPribadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuLainyaActivity.this,ViewDataPribadi.class);
                startActivity(intent);
            }
        });

        detailDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuLainyaActivity.this,DaftarDokterActivity.class);
                startActivity(intent);
            }
        });

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuLainyaActivity.this,ViewDataTransaksiActivity.class);
                startActivity(intent);
            }
        });

    }
}
