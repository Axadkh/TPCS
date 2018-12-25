package com.police.org.tpcs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.police.chDataBae.tpscDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class profile extends AppCompatActivity {

    Button btnback,btnSubmit;
    EditText txtpersonemal,txtphone;
          TextView txtname,txtdob,txtsection;
    ImageView imgProile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        iniComp();
        setData();
    }

    public void btnBackClick(View v){
        Intent i = new Intent(profile.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

    public  void submitClick(View v){
       if(update()){
           Toast.makeText(getApplicationContext(),"data has been updated",Toast.LENGTH_SHORT).show();
           Intent i = new Intent(profile.this,profile.class);
           startActivity(i);
           finish();
       }
       else{
           Toast.makeText(getApplicationContext(),"data updation failed",Toast.LENGTH_SHORT).show();
       }

    }

    public  boolean update(){
tpscDb db = new tpscDb(this);
        session ses = new session(this);
        String id = ses.getId();
        boolean b =false;

        String email = txtpersonemal.getText().toString();
        String phone = txtphone.getText().toString();

        HashMap<String,String> data = new HashMap<>();
        data.put("email",email);
        data.put("phone",phone);

        if(id !=""){
          b =db.updateUser(data,id);
        return b;
        }
        else{
            Log.d("updated","user data succ");
            return b;
        }

    }

    @Override
    public  void onBackPressed(){
        Intent i = new Intent(profile.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }



    public void setData(){
        session ses = new session(this);
        String id = ses.getId();

        tpscDb db = new tpscDb(this);
        HashMap<String,String> data = db.getUser(id);
        String name= data.get("name");
        String email=data.get("email");
        String phone=data.get("phone");
        String dob=data.get("dob");
        String section=data.get("section");
        String path =data.get("pic");
        Bitmap b = getBitmap(path);
        imgProile.setImageBitmap(b);


     txtdob.setText(dob);
      txtsection.setText(section);
        txtphone.setText(phone);
        txtpersonemal.setText(email);
        txtname.setText(name);

    }
    public void iniComp(){
        btnback = findViewById(R.id.btnback);
        btnSubmit = findViewById(R.id.btnsubmit);
        imgProile = findViewById(R.id.profleimage2);
        txtpersonemal = findViewById(R.id.txtpersonemal);
        txtphone = findViewById(R.id.txtphone);
        txtsection = findViewById(R.id.txtsection);
txtdob =findViewById(R.id.txtdob);
        txtname =findViewById(R.id.txtname);

    }

    private Bitmap getBitmap(String path){

        File f = new File(path);
        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
