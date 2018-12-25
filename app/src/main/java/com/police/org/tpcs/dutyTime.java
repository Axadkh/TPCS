package com.police.org.tpcs;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextClock;
import android.widget.TextView;



import com.police.chDataBae.tpscDb;
import com.police.entity.FormatMaker;

public class dutyTime extends AppCompatActivity {

   private TextView loginTime,remTime;
   tpscDb db;
   TextClock txtc;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_time);
        iniComp();
        setdata();
        timediif();
       toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }



        public String getLoginTime(){
            db = new tpscDb(this);
            String login =null;
            session ses = new session(this);
            String id = ses.getId();
            Cursor c = db.getAudit(id);
            if(c.getCount()>0) {
                if (c.moveToLast()) {
                    login =c.getString(0);

                }
            }
            else{
                login = "Not Found";

            }
          return login;
            }

            public  void timediif(){
                FormatMaker f = new FormatMaker();
                String time =f.renmingTime(getLoginTime(),8);

                remTime.setText(time+"");


            }




    private void setdata(){
        loginTime.setText(getLoginTime()+"");
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(dutyTime.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }



    public void iniComp(){
        loginTime = findViewById(R.id.txtlogintime);
        remTime = findViewById(R.id.txtremintime);
    }
}
