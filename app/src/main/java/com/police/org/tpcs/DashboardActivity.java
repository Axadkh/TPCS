package com.police.org.tpcs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.police.chDataBae.tpscDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
      private CardView cardChallan,cardRegisterVehical,cardTrafficStatistcs;
      private CardView cardChallanStatistcs,cardAccount,cardIncidents;
      private DrawerLayout drawlayout;
      private Toolbar toolbar;
      private NavigationView navView;
      ImageView profile;
      private Button btnSearch;
      tpscDb db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initiatComp();
        inttolbar();
        getSupportActionBar().setTitle("HOME");


        cardChallan.setOnClickListener(this);
        cardRegisterVehical.setOnClickListener(this);
        cardChallanStatistcs.setOnClickListener(this);
        cardTrafficStatistcs.setOnClickListener(this);
        cardAccount.setOnClickListener(this);
        cardIncidents.setOnClickListener(this);
        drwarToggle = new ActionBarDrawerToggle(this,drawlayout,toolbar,R.string.open,R.string.close);
        drwarToggle.syncState();
        drawlayout.addDrawerListener(drwarToggle);
       setupDrawerContent(navView);
        setHeader();

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

    TextView username,userSection;
    private ActionBarDrawerToggle drwarToggle;


    public void searchClick(View v){
        Intent i = new Intent(DashboardActivity.this,SearchActivity.class);
        startActivity(i);
        finish();
    }

    public void inttolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("TPSC");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initiatComp(){
        toolbar = findViewById(R.id.toolbar);
        drawlayout = findViewById(R.id.drawerlayout);
        cardChallan = findViewById(R.id.dashcard_challan);
        cardRegisterVehical = findViewById(R.id.dashcard_regvihical);
        cardTrafficStatistcs = findViewById(R.id.dashcard_traffic_sttcs);
        cardChallanStatistcs = findViewById(R.id.dashcard_chalan_sttics);
        cardIncidents = findViewById(R.id.dashcard_incedents);
        cardAccount = findViewById(R.id.dashcard_account);
        navView = findViewById(R.id.naveview);
        btnSearch =findViewById(R.id.btnsearch);
        View view =navView.getHeaderView(0);
        profile = view.findViewById(R.id.pimg);
       username = view.findViewById(R.id.txtpname);
        userSection = view.findViewById(R.id.txtpsection);
    }

    public void setHeader(){
     session ses = new session(DashboardActivity.this);

     //geting id from session class
     String id= ses.getId();
     tpscDb db = new tpscDb(this);

     //getting data from database
    HashMap<String,String> data= db.getUser(id);

    String name = data.get("name");
    String path =data.get("pic");
    String section = data.get("section");

       Bitmap b = loadImage(path);
        username.setText(name);
        userSection.setText(section);
        profile.setImageBitmap(b);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dashcard_challan :
                Intent ic = new Intent(DashboardActivity.this,AddChallan_Atcivity1.class);
                startActivity(ic);
                finish();
                break;

            case R.id.dashcard_regvihical :
                Intent ir = new Intent(DashboardActivity.this,RegisteredVehical.class);
                startActivity(ir);
                finish();
                break;

            case R.id.dashcard_traffic_sttcs :
                Intent its = new Intent(DashboardActivity.this,MapsActivity.class);
                startActivity(its);
                finish();
                break;

            case R.id.dashcard_chalan_sttics :
                Intent itcs = new Intent(DashboardActivity.this,ChallanStatistics_Activity.class);
                startActivity(itcs);
                finish();
                break;

            case R.id.dashcard_account :
                Intent iac = new Intent(DashboardActivity.this,profile.class);
                startActivity(iac);
                finish();
                break;

            case R.id.dashcard_incedents :
                Intent iinc = new Intent(DashboardActivity.this,MapsActivity.class);
                startActivity(iinc);
                finish();
                break;
        }
    }

    public void setupDrawerContent(NavigationView navView) {
      navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectDrawerContent(item);
              return true;
          }
      });
    }


private void selectDrawerContent(MenuItem item){

    session sess = new session(DashboardActivity.this);
    switch (item.getItemId()){
        case R.id.pinfo:
           Intent i = new Intent(DashboardActivity.this,profile.class);
           startActivity(i);
            finish();
            break;
        case R.id.pstatics:
            Intent statics = new Intent(DashboardActivity.this,ChallanStatistics_Activity.class);
            startActivity(statics);
            finish();
            break;
        case R.id.logout:
            sess.removeSession();
            Intent logout = new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(logout);
            finish();

            break;
        case R.id.pcheckduty:
            Intent timing = new Intent(DashboardActivity.this,dutyTime.class);
            startActivity(timing);
            finish();
            break;


        case R.id.phelp:

            Intent help = new Intent(DashboardActivity.this,Help_Act.class);
            startActivity(help);
            finish();
            break;

        case R.id.preport:

            Intent report = new Intent(DashboardActivity.this,ReportProble_Act.class);
            startActivity(report);
            finish();
            break;
        case R.id.parea:
            Intent area = new Intent(DashboardActivity.this,MapsActivity.class);
            startActivity(area);
            finish();
            break;



    }

    item.setChecked(true);
    setTitle(item.getTitle());
    drawlayout.closeDrawers();
}

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        // the drawer will be open or close
        switch (item.getItemId()){
            case android.R.id.home:
                drawlayout.openDrawer(GravityCompat.START);
                return false;

        }
        return super.onOptionsItemSelected(item);
}

    private Bitmap loadImage(String path){

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











