package com.police.org.tpcs;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.police.chDataBae.tpscDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class AddChallan_Activity2 extends AppCompatActivity {

    private Spinner spTicketType, spPaymentMethod, spTicketType2;
    private TextView txtLocation, txtDateTime, txtTotalAmount, txtComment;
    private Button btnsubmit, btnshowLayout;
    Toolbar toolbar;
    int amonut2;
    int amount1;
    boolean flage = true;
    LocationManager locationManager;
    Context mcontext;
    LinearLayout layoutCh2;
    Geocoder geocoder;
    int totalAmount;

    double latitude;
    double longitude;
    String challanTitle, challanTitle2, paymentm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challan_2);
        mcontext = this;
        initiatComp();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Challan");
        locationPermission();


        setDateTime();

        setInVisiable();

        iniPaymentMethod();

        spPaymentMethod.setOnItemSelectedListener(new paymentMethod());
        iniChallanType();
       // getChallanData();

    }

    public void locationPermission() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                3, locationListenerGPS);


    }
    LocationListener locationListenerGPS = new LocationListener() {


        @Override
        public void onLocationChanged(android.location.Location location) {
            latitude = location.getLatitude();
             longitude = location.getLongitude();

            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            Log.d("mylocation",latitude +" "+longitude);
            txtLocation.setText(getAddress(latitude,longitude));
            Log.d("location name",location.toString());
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mcontext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    };

    //layout initaily hide
    public void setInVisiable() {
        if (flage) {

            layoutCh2.setVisibility(View.GONE);

        }

    }

    public void iniPaymentMethod() {
        String[] lstPayMethod = {"Cash", "PostPaid"};
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lstPayMethod);
        spPaymentMethod.setAdapter(paymentAdapter);
        spPaymentMethod.setOnItemSelectedListener(new paymentMethod());


    }

    public void iniChallanType() {

String[] list = getResources().getStringArray(R.array.ticket_type);
                String[] chList = {"Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spTicketType.setAdapter(adapter);
        spTicketType.setOnItemSelectedListener(new challanTitle());
//        ChalanAdapter adapter = new ChalanAdapter(this,getList());
//        spTicketType.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spTicketType2.setAdapter(adapter1);
        spTicketType2.setOnItemSelectedListener(new challanTitle2());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void submitClicke(View v) {

        Toast.makeText(getApplicationContext(), "data is being sumbmited ", Toast.LENGTH_SHORT).show();

       if(inertIntodb()) {
           sendSms();

           Toast.makeText(getApplicationContext(), "data succsessfuly inserted ", Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(AddChallan_Activity2.this, DashboardActivity.class);
           startActivity(intent);
           finish();
       }

       else{
           Toast.makeText(getApplicationContext(), "data insertion failed ", Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(AddChallan_Activity2.this, DashboardActivity.class);
           startActivity(intent);
           finish();
       }

    }


//on button click hide and show layout
    public void hideLayout(View v) {
        if (flage) {
            layoutCh2.setVisibility(v.VISIBLE);
            iniChallanType();
            btnshowLayout.setBackgroundResource(R.drawable.ic_remove_black_24dp);
            flage = false;
        } else {
            int amount;
            amount=totalAmount-amonut2;

            totalAmount = amount;
            layoutCh2.setVisibility(v.GONE);
            txtTotalAmount.setText("" + amount);
            btnshowLayout.setBackgroundResource(R.drawable.ic_action_pluse);
            flage = true;
        }
    }

public boolean inertIntodb(){
//license type will not be save in DB for now if you want to save then add one colmn in challaninf

    Log.d("db method",""+getDataFromUser().get("userLicense"));
    tpscDb db= new tpscDb(this);
    boolean success = db.insertChallanInfo(getDataFromUser());
    insertLocation();

    return success;
}

public long getLastColumn(){
        long last =-1;
        tpscDb db = new tpscDb(this);
        Cursor c =db.getLastChallColum();
       if(c.moveToFirst())
           last =c.getInt(0);
        Log.d("lastCoulum",""+last);
        return last+1;



}

public void insertLocation(){
        int id= (int) getLastColumn();
    tpscDb db = new tpscDb(this);
    db.insetLocation(latitude,longitude,id);

}

    public HashMap<String,String> getDataFromUser() {
            String payment ="null";
        HashMap<String, String> challanData = new HashMap<>();
if(paymentm.equalsIgnoreCase("PostPaid")){
    payment ="pending";
    Log.d("status", " "+payment);
}
else{
    payment ="paid";
    Log.d("status", " "+payment);
}
       session user = new session(this);

       String userid = user.getId();

        challanData.put("vehicleId",getIntent().getExtras().getString("vId"));
        challanData.put("name",getIntent().getExtras().getString("name"));
        challanData.put("vehicleType", getIntent().getExtras().getString("vType"));
        challanData.put("userLicense",getIntent().getExtras().getString("licenseno"));
        challanData.put("userMobile",getIntent().getExtras().getString("mobNo"));
        challanData.put("userNic",getIntent().getExtras().getString("nic"));
        challanData.put("licensetype",getIntent().getExtras().getString("licensetype"));
        challanData.put("comment",txtComment.getText().toString());
        challanData.put("date",getDate());
        challanData.put("time", getTime());
        challanData.put("location",txtLocation.getText().toString());
        challanData.put("payment", payment);
        challanData.put("challan1", challanTitle);
        challanData.put("challan2", challanTitle2);
        challanData.put("totalAmount", txtTotalAmount.getText().toString());
        challanData.put("imagepath",getIntent().getExtras().getString("image1"));
        challanData.put("imagepath2",getIntent().getExtras().getString("image2"));
        challanData.put("userid", userid);

        Log.d("hash status", ""+challanData.get("payment"));



       return  challanData;
    }

    public void sendSms(){
        HashMap<String,String> data =getDataFromUser();
       String violationTitle2 = "violation tile";
       String  violatchecks =data.get("challan2");
       if(violatchecks==null){
           violationTitle2 =" ";
       }
       else{
           violationTitle2 += violatchecks;
       }
String pymentmes=null;
if(paymentm.equalsIgnoreCase("Cash")){
    pymentmes =" Callan Satus: paid Thank You";
}
else {pymentmes ="Please pay fine in 10 days  nearst easy piasa shop Account no 04356788999" +
        " or nearst traffic office after 10 days 10% of the total amount will be charged as late fee ";}
        SmsManager smsManager = SmsManager.getDefault();
        String message = "Dear: "+data.get("name")+" your fine with an Amount of "+data.get("totalAmount") + " Against Section 1: " +  data.get("challan1")+" Challan id: "+getLastColumn()+ " vehicle ID: " +data.get("vehicleId") +


                " Date: " + data.get("dateTime") + " Location " + data.get("location") + " " +pymentmes+" Thank you please Download our Application https//plys.google/cc ";
        ArrayList<String> parts = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage( data.get("userMobile"),null,parts,null,null);
    }
    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");

        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());
        return date;
    }

    public String getTime(){
        SimpleDateFormat df1 = new SimpleDateFormat("hh:mm:s a");
        Calendar c = Calendar.getInstance();
        String time = df1.format(c.getTime());
        return time;
    }

   private void setDateTime(){

        txtDateTime.setText(getDate()+" "+getTime());

    }

    public void initiatComp() {

        spTicketType = findViewById(R.id.sp_challanselecter);
        spTicketType2 = findViewById(R.id.sp_challanselecter2);
        spPaymentMethod = findViewById(R.id.sp_paymentmetode);

        layoutCh2 = findViewById(R.id.layout_chselection2);

        //text views

        txtLocation = findViewById(R.id.txtlocation);
        txtDateTime = findViewById(R.id.txt_date);
        txtTotalAmount = findViewById(R.id.txt_totalamount);

        txtComment = findViewById(R.id.txt_comment);
        //button
        btnshowLayout = findViewById(R.id.btn_showlayout);
        btnsubmit = findViewById(R.id.btnchallan_submit);
        toolbar = findViewById(R.id.toolbar);
        //toolbarbtn


    }



//public  void getChallanData(){
//        tpscDb db = new tpscDb(this);
//       Cursor c = db.getchallaninfo();
//       List<String> list = new ArrayList<>();
//       if(c.getCount()!=0) {
//           while (c.moveToNext()) {
//              list.add(c.getString(0));
//               list.add(c.getString(1));
//               list.add(c.getString(2));
//               list.add(c.getString(3));
//               list.add(c.getString(4));
//               list.add(c.getString(5));
//               list.add(c.getString(6));
//               list.add(c.getString(7));
//               list.add(c.getString(8));
//               list.add(c.getString(9));
//               list.add(c.getString(10));
//               list.add(c.getString(11));
//               list.add(c.getString(13));
//               list.add(c.getString(14));
//               list.add(c.getString(15));
//               list.add(c.getString(16));
//               list.add(c.getString(17));
//
//           }
//
//           for(String s: list){
//               Log.d("data ",""+s);
//           }
//
//       }
//}

//    public  void showDilog(View v){
//
//       final String[] item ={"ch1","ch2","ch3","ch4","ch5","ch6","ch7","ch8","ch9"};
//        final boolean [] chkedItem;
//        final List<Integer> userItems = new ArrayList<>();
//         ChallanList currentIte
//        chkedItem = new boolean[item.length];
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(AddChallan_Activity2.this);
//        dialog.setTitle(R.string.dialog_title);
//
//       dialog.setMultiChoiceItems(item, chkedItem, new DialogInterface.OnMultiChoiceClickListener() {
//           @Override
//           public void onClick(DialogInterface dialogInterface, int postion, boolean isChecked) {
//
//               if(isChecked){
//                   if(! userItems.contains(postion)){
//                       userItems.add(postion);
//                   }
//
//               }
//               else if(userItems.contains(postion)){userItems.remove(postion);}
//           }
//       });
//         //ok button
//       dialog.setCancelable(false);
//       dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//           @Override
//           public void onClick(DialogInterface dialogInterface, int which) {
//               String slitem ="";
//               for(int i=0;i<userItems.size();i++){
//                   slitem = slitem + item[userItems.get(i)];
//                   if(i != userItems.size()-1){
//                       slitem = slitem + ",";
//                   }
//               }
//               txtChalantitle.setText(slitem);
//           }
//       });
//
//       //cancel button
//       dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//           @Override
//           public void onClick(DialogInterface dialogInterface, int i) {
//               dialogInterface.dismiss();
//           }
//       });
//
//       // nutral button
//        dialog.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                for(int j=0;j<chkedItem.length;j++){
//                    chkedItem[j]=false;
//                    userItems.clear();
//                    txtChalantitle.setText("");
//                }
//            }
//        });
//
//      AlertDialog mdialog =dialog.create();
//      dialog.show();
//
//
//    }


    public String getAddress(Double lat, double longt) {
        List<Address> address;



        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            if(lat !=0 && longt !=0) {
                address = geocoder.getFromLocation(lat, longt,1);
                String addres = address.get(0).getAddressLine(0);
                //String area = address.get(0).getLocality();


                return addres;
            }
            else{
                locationPermission();
                return "wait.." ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return lat +"  "+longt;
        }

    }

    public ArrayList<ChallanList> getList() {

        ArrayList<ChallanList> chlist = new ArrayList<>();
        chlist.add(new ChallanList("title1", 200, false));
        chlist.add(new ChallanList("title1", 300, false));
        chlist.add(new ChallanList("title1", 240, false));
        return chlist;
    }

    class challanTitle implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            int i = (int) parent.getItemIdAtPosition(position);
            amount1=0;
            switch (i) {

                case 0:
                    amount1 = 200;
                    totalAmount = amount1 + amonut2;
                    challanTitle = parent.getItemAtPosition(position).toString();
                    txtTotalAmount.setText("" + totalAmount);

                    break;
                case 1:
                    challanTitle = parent.getItemAtPosition(position).toString();
                    amount1 = 400;
                    totalAmount = amount1 + amonut2;
                    txtTotalAmount.setText("" + totalAmount);


                    break;
                case 2:
                    challanTitle = parent.getItemAtPosition(position).toString();
                    amount1 = 260;
                    totalAmount = amount1 + amonut2;
                    txtTotalAmount.setText("" +totalAmount);

                    break;
                case 3:
                    challanTitle = parent.getItemAtPosition(position).toString();
                    amount1 = 500;
                    totalAmount = amount1 + amonut2;
                    txtTotalAmount.setText("" + totalAmount);

                    break;

                case 4:
                    challanTitle = parent.getItemAtPosition(position).toString();
                    amount1 = 600;
                    totalAmount = amount1 + amonut2;
                    txtTotalAmount.setText("" + totalAmount);

                    break;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class challanTitle2 implements AdapterView.OnItemSelectedListener {
        public challanTitle2(){}
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            int i = (int) parent.getItemIdAtPosition(position);


            switch (i) {

                case 0:
                    challanTitle2 = parent.getItemAtPosition(position).toString();
                    amonut2 = 200;
                    totalAmount = amount1 + amonut2;
                    txtTotalAmount.setText("" +  totalAmount);

                    break;
                case 1:
                    amonut2 = 400;
                    challanTitle2 = parent.getItemAtPosition(position).toString();
                    totalAmount =amount1 + amonut2;
                    txtTotalAmount.setText("" +  totalAmount);

                    break;
                case 2:
                    challanTitle2 = parent.getItemAtPosition(position).toString();
                    amonut2 = 260;
                    totalAmount = amount1 + amonut2;

                    txtTotalAmount.setText("" +  totalAmount);

                    break;
                case 3:
                    challanTitle2 = parent.getItemAtPosition(position).toString();
                    amonut2 = 500;
                    totalAmount = amount1 + amonut2;

                    txtTotalAmount.setText("" +  totalAmount);

                    break;
                case 4:
                    challanTitle2 = parent.getItemAtPosition(position).toString();
                    amonut2 = 600;
                    totalAmount = amount1 + amonut2;

                    txtTotalAmount.setText("" +  totalAmount);

                    break;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class paymentMethod implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

            Toast.makeText(getApplicationContext(), " " + parent.getItemAtPosition(position) + " " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            int i = (int) parent.getItemIdAtPosition(position);
            paymentm = parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
