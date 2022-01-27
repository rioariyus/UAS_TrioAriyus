package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText password;
    private EditText confPassword;
    private EditText email;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        email = (EditText) findViewById(R.id.emailReg);
        password = (EditText) findViewById(R.id.passwordReg);

         btnSignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 registerUser();

             }
         });


    }

    private void registerUser() {
        final String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        final String nama = getIntent().getStringExtra("nama");
        final String tanggalLahir = getIntent().getStringExtra("tanggalLahir");
        final String jenisKelamin = getIntent().getStringExtra("jenisKelamin");
        final String alamat = getIntent().getStringExtra("alamat");
        final String tinggiBadan = getIntent().getStringExtra("tinggiBadan");
        final String beratBadan = getIntent().getStringExtra("beratBadan");
        final String golonganDarah = getIntent().getStringExtra("golonganDarah");


        if (TextUtils.isEmpty(mEmail)) {
            Toast.makeText(this, "Pleas enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, "Pleas enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(SignUpActivity.this, "Please Wait...","Processing...", true);
        firebaseAuth.createUserWithEmailAndPassword(mEmail,mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference ref_user = FirebaseDatabase.getInstance().getReference("UserData").child(userID);
                            DataPribadi dataPribadi = new DataPribadi(mEmail,nama,tanggalLahir,jenisKelamin,alamat,tinggiBadan,beratBadan
                            ,golonganDarah);
                            ref_user.setValue(dataPribadi);

                            sendEmailVerification();
                            Toast.makeText(SignUpActivity.this, "Check Your Email to Verification", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d("ERROR", task.getException().toString());
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }


}
