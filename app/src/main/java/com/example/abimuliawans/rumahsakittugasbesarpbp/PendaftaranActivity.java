package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PendaftaranActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker;
    private Button btnDaftar;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private String tanggal;
    private Spinner spinner;
    private String dokter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        firebaseAuth = FirebaseAuth.getInstance();
        spinner = (Spinner)findViewById(R.id.daftarDokter);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tvDateResult = (TextView) findViewById(R.id.tv_dateresult);
        btDatePicker = (Button) findViewById(R.id.bt_datepicker);
        btnDaftar = (Button)findViewById(R.id.btnDaftar);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dokter = spinner.getSelectedItem().toString();
                userID = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference ref_pendaftaran = FirebaseDatabase.getInstance().getReference("PendaftaranDokter").child(userID);
                DataPendaftaran dataPendaftaran = new DataPendaftaran(dokter,tanggal);
                ref_pendaftaran.setValue(dataPendaftaran);
                Toast.makeText(PendaftaranActivity.this, "Terima Kasih Sudah Mendaftar", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PendaftaranActivity.this,MenuUtamaActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tvDateResult.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
                tanggal = dateFormatter.format(newDate.getTime());
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
