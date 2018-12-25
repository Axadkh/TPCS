package com.police.org.tpcs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.police.chDataBae.tpscDb;

import java.util.HashMap;
import java.util.List;
public class SearchActivity extends AppCompatActivity  {



    Toolbar toolbar;
    private List<String> list;
    private boolean flage= true;
    private boolean flage2= true;
    private TextView  txtpstatus,txtChallId,txtNotFound ,txtDName,txtChalanTitle,txtCount,txtDateTime,txtWardenName,txtLocation,txtLicense;
    private  HashMap<String,String> data;
    private  LinearLayout linearLayout;
    private  SearchView sview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        iniComp();
        getSupportActionBar().setTitle("Search");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setInvisible();




    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(SearchActivity.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

    public  void setVisible(){
        if(!flage){
            linearLayout.setVisibility(View.VISIBLE);
            this.flage=true;
        }
    }
public  void setInvisible(){
        if(flage){
            linearLayout.setVisibility(View.GONE);
            flage = false;
        }


}
    public void notVisible(){
        if(flage2){
        txtNotFound.setVisibility(View.VISIBLE);
       }
        else{
            txtNotFound.setVisibility(View.GONE);
            flage2 =true;
        }
    }
//    public void getData() {
//        tpscDb db = new tpscDb(this);
//        list = new ArrayList();
//        ArrayList<String> data = new ArrayList<>();
//        Cursor c = db.getVids();
//        if (c.getCount() < 0) {
//            Log.d("dbdata", "no data avable");
//        } else {
//            while (c.moveToNext()) {
//
//                list.add(c.getString(0));
//                Log.d("dbdata", c.getString(0));
//            }
//        }
//
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        sview = (SearchView) item.getActionView();
        sview.setQueryHint("Search");

        sview.setSubmitButtonEnabled(true);
        sview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

             getDataFromDb(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void getDataFromDb(String query) {
     data = new HashMap<>();
        tpscDb db = new tpscDb(this);
        Cursor c = db.searchChallan(query.toLowerCase());
     int i = c.getCount();

        while(c.moveToNext()){

          data.put("id",c.getString(0));
           data.put("name",c.getString(1));
            data.put("license",c.getString(2));
            data.put("date",c.getString(3));
            data.put("time",c.getString(4));
            data.put("title",c.getString(5));
            data.put("location",c.getString(6));
            data.put("payment",c.getString(7));
            data.put("userid",c.getString(8));
        }
data.put("count",i+"");
        setData();

    }

public  void setData(){
        if(data.get("id")!=null) {
            flage2=false;
            notVisible();
            setVisible();
            String payment = data.get("payment");
            txtChallId.setText(data.get("id"));
            txtDName.setText(data.get("name"));
            txtChalanTitle.setText(data.get("title"));
            txtLicense.setText(data.get("license"));
            txtDateTime.setText(data.get("date") + " At " + data.get("time"));
            txtWardenName.setText(data.get("userid"));
            txtCount.setText(data.get("count"));
            txtLocation.setText(data.get("location"));
            txtpstatus.setText(payment + "");

        }
        else{
            setInvisible();
            flage2 =true;
            notVisible();

        }



}
//public void changColor(String s){
//        if(s!=null && s.equalsIgnoreCase("paid")){
//
//
//        }
//        else if(s!=null && s.equalsIgnoreCase("pending")){
//
//        }
//        else{
//
//        }
//}

    public void iniComp(){
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Search By ID");
        linearLayout = findViewById(R.id.mainlayout);
          //text ini
        txtChallId = findViewById(R.id.txtchallid );
        txtDName = findViewById(R.id.txtdname);
        txtChalanTitle = findViewById(R.id. txtchalantitle);
        txtCount = findViewById(R.id. txtcount );
        txtDateTime = findViewById(R.id.txtdatetime);
        txtWardenName = findViewById(R.id.txtwardenname);
        txtLocation = findViewById(R.id.txtlocation );
           txtpstatus = findViewById(R.id.txtstatus);
        txtLicense = findViewById(R.id.txlicense);
        txtNotFound =findViewById(R.id.txtnotfound);




    }

}
