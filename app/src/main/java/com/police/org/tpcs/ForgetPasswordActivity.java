package com.police.org.tpcs;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputEditText userId,userEmail;
    private TextView txtbackToLogin;
    private Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initaitComp();
    }

    public void txtBackToLoginClick(View v){
        Intent i = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void getData(){
        String userid = userId.getText().toString();
        String email = userEmail.getText().toString();
    }

    private void initaitComp(){
        userId = findViewById(R.id.txtuseridforget);
        userEmail = findViewById(R.id.txtuser_emailforgetemail);
        txtbackToLogin = findViewById(R.id.txtback_tologin);
        btnsubmit = findViewById(R.id.btnaubmit_forgetpas);

    }
}
