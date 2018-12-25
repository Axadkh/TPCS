package com.police.org.tpcs;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.police.chDataBae.tpscDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtuserName,txtuserPassword;
    Button btnLogin;

    TextView txtsignup,txtForgetPass,errorDilog;
    String name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiatLoginComp();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public boolean chkuserName(){
        if(TextUtils.isEmpty(name)){
            txtuserName.setError("Please Enter user Name");
            return false;

        }
        else{
            return true;
        }

    }



    public boolean vaildateUser(){
String name = txtuserName.getText().toString().trim();
String password =txtuserPassword.getText().toString().trim();
   if(TextUtils.isEmpty(name)){
       txtuserName.setError(" ! Filed is Empty");
       return  false;
   }
   else if(TextUtils.isEmpty(password)){
       txtuserPassword.setError("Filed is Empty");
       return false;
   }
   else if(!vailduserdata()){
       Dialog d = new Dialog(this);
       d.setTitle("Error");
       d.setCancelable(true);
       d.setContentView(R.layout.custom_dialog);
       d.show();
       txtuserName.setText("");
       txtuserPassword.setText("");
       return false;

   }
   else {return true;}
    }

    public boolean vailduserdata(){
        boolean vaild=false;
        session ses = new session(LoginActivity.this);
        String id = txtuserName.getText().toString();
        String pass = txtuserPassword.getText().toString();


        tpscDb db = new tpscDb(this);
        vaild = db.vaildateUser(id,pass);
        if(vaild){
            ses.setId(id);
           vaild = db.inserAduit(ses.getId(),getDate(),getTime());
        }
        return  vaild;
    }
    public void loginClick(View v){

        if(vaildateUser() ){
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
        }
    }


    public void regClick(View v){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);

    }
    public void forgotClick(View v){
        Intent i = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(i);

    }
    public String getTime(){
        SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:ss a");
        Calendar c = Calendar.getInstance();
        String time = df1.format(c.getTime());
        return time;
    }

    private void initiatLoginComp(){
        txtuserName = findViewById(R.id.txtusername);
        txtuserPassword = findViewById(R.id.txtpassword);
        btnLogin= findViewById(R.id.btnlogin);
        txtsignup = findViewById(R.id.txtsignup);
        txtForgetPass = findViewById(R.id.txtforget_pass);



    }

    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());
       return date;
    }


}
