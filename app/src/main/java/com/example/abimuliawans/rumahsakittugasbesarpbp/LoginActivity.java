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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp;
    private Button btnLogin;
    private EditText emailLog;
    private EditText passwordLog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login);
        emailLog = (EditText)findViewById(R.id.emailLogin);
        passwordLog = (EditText)findViewById(R.id.passwordLogin);
        signUp = (TextView)findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,DataPribadiActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProses();
            }
        });

    }

    public void loginProses(){

        if(TextUtils.isEmpty(emailLog.getText().toString())){
            Toast.makeText(this,"Masukan Email Anda",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordLog.getText().toString())){
            Toast.makeText(this,"Masukan Password Anda",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait...","Processing...", true);
        (firebaseAuth.signInWithEmailAndPassword(emailLog.getText().toString(), passwordLog.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            checkIfEmailVerified();
                        }
                        else{
                            Log.d("ERROR", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified())
        {
            finish();
            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,MenuUtamaActivity.class);
            startActivity(intent);
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(LoginActivity.this, "Check Email Verification", Toast.LENGTH_SHORT).show();

        }
    }


}
