package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MenuUtamaActivity extends AppCompatActivity {

    private ImageView mDaftar;
    private ImageView mAmbulance;
    private ImageView mApotek;
    private ImageView mRiwayat;
    private ImageView mLain;
    private static final String TAG = "MenuUtamaActivity";
    private static final int ERROR_DIALOG_REQUEST = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_utama);
            mDaftar= findViewById(R.id.daftar);
            mAmbulance = findViewById(R.id.ambulance);
            mRiwayat = findViewById(R.id.riwayat);
            mApotek = findViewById(R.id.apotek);
            mLain = findViewById(R.id.MenuLain);

            onClick();

            if(isServiceOK())
            {
                initMap();
            }
        }

        public void initMap(){
            mAmbulance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuUtamaActivity.this, AmbulanActivity.class);
                    startActivity(intent);
                }
            });
        }

        public void onClick(){
            mDaftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuUtamaActivity.this, PendaftaranActivity.class);
                    startActivity(intent);
                }
            });


            mRiwayat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuUtamaActivity.this, ViewRiwayatActivity.class);
                    startActivity(intent);
                }
            });

            mApotek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuUtamaActivity.this, ApotikActivity.class);
                    startActivity(intent);
                }
            });

            mLain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuUtamaActivity.this, MenuLainyaActivity.class);
                    startActivity(intent);
                }
            });


        }

    public boolean isServiceOK(){
        Log.d(TAG, "isService OK: checking google service version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MenuUtamaActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isService OK: google paly service is working"); return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MenuUtamaActivity.this,available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    }

