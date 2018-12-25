package com.police.org.tpcs;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.police.chDataBae.tpscDb;
import com.police.entity.FormatMaker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class ChallanStatistics_Activity extends AppCompatActivity {
    //card today > next

private BarChart bChart;
private  CardView cardToday,cardyesterday,cardthismonth,cardcash,cardavg,cardlastmonth,cardallthtime;
private TextView txttoday,txtyesterday,txtthismonth,txtcash,txtavg,txtlastmonth,txtallthetime;
private ImageView imgToday,imgyesterday;
private Toolbar toolbar;
tpscDb db;
    session ses;

int [] color = {R.color.lightBlue400,R.color.lightBlue500,R.color.lightBlue300};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan_statistics);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setTitle("Statics");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initiatCom();
        setData();
        handleArrow();
        Mchart();


    }

    public  void Mchart(){

        bChart =findViewById(R.id.chart);
        bChart.setDrawBarShadow(false);
        bChart.setDrawValueAboveBar(true);
        // bChart.setMaxVisibleValueCount(50);
        bChart.setPinchZoom(false);
        bChart.setDrawGridBackground(false);
        ArrayList<BarEntry> barlist = new ArrayList<>();
        barlist.add(new BarEntry(1,40f));
        barlist.add(new BarEntry(2,25f));
        barlist.add(new BarEntry(3,20f));
        barlist.add(new BarEntry(4,30f));
        BarDataSet barDataSet = new BarDataSet(barlist,"Challan Statics");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        bChart.setData(barData);
        XAxis xAxis = bChart.getXAxis();
        String [] months = new String[]{"Aug","Sep","Oct","Nov","Dec"};
        xAxis.setValueFormatter(new MyXAxisValueFormater(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        bChart.animateY(3000);
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setAxisMinimum(1);



        YAxis left = bChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        bChart.getAxisRight().setEnabled(false); // no right axis

    }

public class MyXAxisValueFormater implements IAxisValueFormatter{
String[] values;

      public MyXAxisValueFormater(String[] values){
          this.values =values;
      }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values [(int) value];
    }
}

    public  int getTotalAmount(){
        ses = new session(this);
        String id = ses.getId();
        db = new tpscDb(this);
        int totalCash=0;
        Cursor c =db.getTotalCash(id);
        if(c.moveToFirst()){
            totalCash =c.getInt(0);
        return  totalCash;
        }
        return totalCash;
    }

    public  int getTodaystat(){
        db = new tpscDb(this);
        Cursor c =db.getTodaystat(getDate());
        int todayCount = c.getCount();
        db.close();

            return todayCount;
    }

    private int getTotalChallan(){

        ses = new session(this);
        String id = ses.getId();
        tpscDb db = new tpscDb(this);
        Cursor c = db.getchallaninfo(id);
        int count =c.getCount();
        return count;
    }

    public  int getLastMonth(){
        db = new tpscDb(this);
        Cursor c =db.getTodaystat(getDatemins(1));
        int lastMonth = c.getCount();
        db.close();
        if(lastMonth<=0){
            return 0;
        }
        else{
            return lastMonth;
        }

    }

    public  int getYesterDay(){
        db = new tpscDb(this);

        Cursor c =db.getTodaystat(getDatemins(2));
        int yeasterday = c.getCount();
        db.close();
        if(yeasterday<=0){
            return 0;
        }

        else{
            return yeasterday;
        }

    }

    public  float getAvg(){
        ses = new session(this);
        String id = ses.getId();
        db = new tpscDb(this);
       float avg = 0.0f;
       Cursor c= db.getAvg(id);
       if(c.moveToFirst()){
         avg = (float) c.getInt(0);
       }

       db.close();
        Log.d("average",avg+"");
        return avg/30;
    }

    public String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());

        return date;

    }

    public String getDatemins(int code){
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c;

        Date d = new Date();
        if(code==1){
        c = Calendar.getInstance();
        c.setTime(d);

        c.add(Calendar.MONTH, -1);
        d=c.getTime();
            String  month = df.format(d);
     return month;
        }
     else if (code ==2){
            c = Calendar.getInstance();
            c.setTime(d);

            c.add(Calendar.DAY_OF_MONTH, -1);
            d=c.getTime();
           String day  = df.format(d);
            return day;
        }

        else{ return null;
        }
    }


        public void handleArrow(){
        String s=txttoday.getText().toString().trim();
       String yester = txtyesterday.getText().toString().trim();
        int today = Integer.parseInt(s);
        int yesterday= Integer.parseInt(yester);

        if(today>yesterday){

            imgToday.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        }

      if(yesterday>today){
            imgyesterday.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        }
       if(yesterday==today){
            imgyesterday.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            imgToday.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        }
    if(yesterday<today){

            imgyesterday.setImageResource(R.drawable.ic_arrow_downward_black_24dp);

        }

}

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ChallanStatistics_Activity.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

public void setData(){
    FormatMaker fo = new FormatMaker();

        String formater = fo.Format(getTotalAmount());
        txtcash.setText(formater);
        txttoday.setText(getTodaystat()+"");
           txtavg.setText(String.format("%.3f", getAvg()));
            txtlastmonth.setText(getLastMonth()+"");
            txtthismonth.setText(getTotalChallan()+"");
            txtyesterday.setText(getYesterDay()+"");
            txtallthetime.setText(getTotalChallan()+"");




}
    private void initiatCom(){
        //toolbar;

        cardToday =findViewById(R.id.scardtody);
        txttoday = findViewById(R.id.stxt_today);
        imgToday = findViewById(R.id.simg_today);

        //yesterday
        cardyesterday =findViewById(R.id.scardyesterday);
        txtyesterday = findViewById(R.id.stxtyesterday);
        imgyesterday = findViewById(R.id.simgyesterday);

        //this month

        cardthismonth =findViewById(R.id.card_thismonth);
        txtthismonth = findViewById(R.id.stxt_thismonth);

        //last month
        cardlastmonth =findViewById(R.id.card_lastmonth);
        txtlastmonth = findViewById(R.id.stxt_lastmonth);

        //Avg
        cardavg =findViewById(R.id.cardavg);
        txtavg = findViewById(R.id.stxt_avg);

        //last year
        cardcash =findViewById(R.id.card_cash);
        txtcash = findViewById(R.id.stxt_cash);

        //all the time
        cardallthtime =findViewById(R.id.card_allthetime);
        txtallthetime = findViewById(R.id.stxt_allthetime);

    }
}
