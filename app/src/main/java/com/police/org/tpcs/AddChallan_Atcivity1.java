package com.police.org.tpcs;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddChallan_Atcivity1 extends AppCompatActivity implements View.OnClickListener {

   private TextInputEditText txtvehicalID,txtlicenseNo,txtmobileNo,txtnicNo,txtuserName;
   private Spinner spVhicalType,splicenseType;
   private CardView cardForImg1,cardForImg2,cardForImg3;
   private ImageView img1,img2,img3;
   private Button btnNextToChallan2;

   private boolean disablecamra;


   private String imagePath,imagePath2,licenseType;

   // vehicle Type this value will be change dynamicaly when user select item
    String vehicleType,vId,licNo,mobileNo,nic,name;


   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challan__atcivity1);
        iniComp();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Challan");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //spineer invokation
        spineerIni();
        spinlictyps();

        cardForImg1.setOnClickListener(this);
        cardForImg2.setOnClickListener(this);
        cardForImg3.setOnClickListener(this);


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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(AddChallan_Atcivity1.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private  void checkName(){
       String id =txtvehicalID.getText().toString().trim();
       if(TextUtils.isEmpty(id)){
           txtvehicalID.setError("please first Enter id then take a pic");
           this.disablecamra = false;}
       else{
           this.disablecamra =true;
       }

    }

    @Override
    public void onClick(View view) {
        checkName();
        if(disablecamra){


         switch (view.getId()){

             case R.id.card_for_img1:

                 Intent cami = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(cami,200);

                 break;

             case R.id.card_for_img2:

                 Intent cami1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(cami1,201);

                 break;

             case R.id.card_for_img3:
                 Intent cami2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(cami2,202);
                 break;

         }
        }
        else{
            Toast.makeText(AddChallan_Atcivity1.this,"Please first Enter user name then take a pic",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected  void onActivityResult(int resultCode, int requestCode,Intent data){
        super.onActivityResult(resultCode,requestCode,data);

        if(resultCode ==200 && requestCode ==RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img1.setImageBitmap(photo);
        }

           else if(resultCode ==201 && requestCode ==RESULT_OK)
           {
               imagePath=null;

            String picid =txtvehicalID.getText().toString()+"1";
               Bitmap photo = (Bitmap) data.getExtras().get("data");
               Log.d("my pic",photo.getHeight()+" "+photo.getWidth());
               String path =  saveToInternalStorage(photo,picid);
               img2.setImageBitmap(loadImageFromStorage(path,picid,1));
               Log.d("imagepath",path);
               Log.d("instance path",imagePath+" image");



           }
        else if(resultCode ==202 && requestCode ==RESULT_OK)
        {
            String picid =txtvehicalID.getText().toString()+"2";
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagePath2=null;
          String path =  saveToInternalStorage(photo,picid);
             img3.setImageBitmap(loadImageFromStorage(path,picid,2));
            Log.d("instance path",imagePath2+" image");


        }

    }

    private Bitmap loadImageFromStorage(String path,String name,int code) {

        try {
            File f=new File(path, name+".jpg");

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.d( " loadimagepath",f.getPath());
            if(code==1){
                imagePath= f.getPath();
                Log.d( " loadimagepath code 1",imagePath);
            }
            else{
                imagePath2 =f.getPath();
                Log.d( " loadimage code 2 ",imagePath2);
            }

            return  b;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private String saveToInternalStorage(Bitmap bitmapImage,String imgName){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        Log.d("befor",bitmapImage.getWidth()+" h"+bitmapImage.getHeight());

        File directory = cw.getDir("TPCS_data", Context.MODE_PRIVATE);
        // Create imageDir

        File mypath=new File(directory,imgName+".jpg");

        FileOutputStream fos = null;
        try {


            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream

            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public byte[] converToBytes(Bitmap image){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        return bArray;


    }

    public void getChallan1Data(){
        vId=txtvehicalID.getText().toString().toLowerCase();
       this.licNo =txtlicenseNo.getText().toString();
        mobileNo = txtmobileNo.getText().toString();
         nic=txtnicNo.getText().toString();
         name = txtuserName.getText().toString();
         Log.d("data",vId+"");
        Log.d("data",licNo+"");
        Log.d("data",txtlicenseNo.getText().toString()+"");
        Log.d("data",mobileNo+"");
        Log.d("data",nic+"");
        Log.d("data",name+"");



    }

    public void spineerIni(){
        String[] vehiclesList = {"Select Vehicle Type","Car", "Motorcycles", "Passenger Car", "School bus", "Truck", "Tanker", "Rickshaw", "Public Bus", "Company Bus", "Coach", "Van", "Tractor", "Lorry"};

        ArrayAdapter<String> adpter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehiclesList);
        spVhicalType.setAdapter(adpter);

        spVhicalType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int position, long l) {

                vehicleType = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
    }

    public void spinlictyps(){
        String licensearray []={ "Select Challan Type","None","LTV","HTV","PSV","Learning License","International Driver permit"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,licensearray);
        splicenseType.setAdapter(adapter);
        splicenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                licenseType = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
}

    public void nextClick(View v){
        // implemeting un comment if condition
        Log.d("during transition",txtlicenseNo.getText().toString()+" instanc"+licNo);
        if(validateInput()) {
            getChallan1Data();
            Intent intent = new Intent(AddChallan_Atcivity1.this, AddChallan_Activity2.class);intent.putExtra("vId",vId);
            intent.putExtra("vid",vId);
            intent.putExtra("vType",vehicleType);
            intent.putExtra("licenseno",txtlicenseNo.getText().toString());
            intent.putExtra("mobNo",mobileNo);
            intent.putExtra("licensetype",licenseType);
            intent.putExtra("nic",nic);
        intent.putExtra("name",name);
        intent.putExtra("image1",this.imagePath);
        intent.putExtra("image2",this.imagePath2);

            startActivity(intent);


       }

    }

    public boolean validateInput(){
         boolean check=false;
        String error="Field is Empty ";
        String vvId=txtvehicalID.getText().toString().trim();
        String vlicNo=txtlicenseNo.getText().toString().trim();
        String vmobilNo =txtmobileNo.getText().toString().trim();
        String vnic=txtnicNo.getText().toString().trim();
        String username = txtuserName.getText().toString().trim();

                 if(TextUtils.isEmpty(vvId)){
                     txtvehicalID.setError(error);
                     check =false;}


//                 else if(TextUtils.isEmpty(vlicNo)){
//                     txtlicenseNo.setError(error);
//                     check =false;;}

                 else if(TextUtils.isEmpty(username)){
                     txtuserName.setError(error);
                     check =false;;}


//                 else if(TextUtils.isEmpty(vnic)){
//                     txtnicNo.setError(error);
//                     check =false;;}

//                 else if(vnic.length() != 13){
////                     txtnicNo.setError("Number must be 13 digit");
////                     check =false;}

                 else if(TextUtils.isEmpty(vmobilNo)){
                     txtmobileNo.setError(error);
                     check=false;
                 }

                 else if(vmobilNo.length()!=11){
                     txtmobileNo.setError("please Enter 11 digit Mobile No");
                     check=false;}

                 else if(TextUtils.isEmpty(imagePath)){
                    Toast.makeText(getApplicationContext(),"please take picture of Vehicle",Toast.LENGTH_SHORT).show();
                     check=false;}

//                 else if(TextUtils.isEmpty(imagePath2)){
//                     Toast.makeText(getApplicationContext(),"please take one more picture",Toast.LENGTH_SHORT).show();
//                     check=false;}


                 else {
                     check=true;
                 }

         return check;
    }

    private void iniComp(){
        //button
        btnNextToChallan2 = findViewById(R.id.btn_next_tochallan2);
        //text view
        txtuserName =findViewById(R.id.txt_username);
        txtvehicalID = findViewById(R.id.txt_vehical_id);
        txtlicenseNo = findViewById(R.id.txt_license_no);
        txtmobileNo = findViewById(R.id.txt_mobile_no);
        txtnicNo = findViewById(R.id.txt_nic_no);
        //spineer
        spVhicalType = findViewById(R.id.sp_vehical_type);
        //image view
        img1 =findViewById(R.id.card_img1);
        img2 =findViewById(R.id.card_img2);
        img3 =findViewById(R.id.card_img3);
          splicenseType = findViewById(R.id.license_type);
        //card view
        cardForImg1 =findViewById(R.id.card_for_img1);
        cardForImg2 =findViewById(R.id.card_for_img2);
        cardForImg3 =findViewById(R.id.card_for_img3);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
    }




}
