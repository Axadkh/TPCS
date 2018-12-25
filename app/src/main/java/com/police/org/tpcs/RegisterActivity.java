package com.police.org.tpcs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.police.chDataBae.tpscDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText txtphone, txtSection, inptUserId,inptUserEmai,inptUserName,inptUserPass,inptUserConfPass;
    private Button btnRegister;
    private TextView txtBackToogin,txtBirth;
    CardView picbtn;
    ImageView pic;
    private String imagePath;


    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initComp();
        getRdata();
        picbtn.setOnClickListener(this);

        date =new DatePickerDialog.OnDateSetListener() {



            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dob = day +" - "+month+ " - "+ year;



                txtBirth.setText(dob);
            }
        };
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



    public void backClick(View v){
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
    }

    public boolean isDataInserted(){

        tpscDb db = new tpscDb(this);

        boolean success = db.inserIntouser(getRdata());

        Log.d("insert signup", "success :" + success);
        return success;

    }

    public void signupClick(View v){
        if(valdation()) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

        }

    }

    public HashMap<String,String> getRdata(){
        HashMap<String,String > data= new HashMap<>();
        String userid = inptUserId.getText().toString();
        String  userName = inptUserName.getText().toString();
        String   userpass = inptUserPass.getText().toString();
        String   userEmail = inptUserEmai.getText().toString();
        String  birth = txtBirth.getText().toString();
        String phone = txtphone.getText().toString();
        String  section = txtSection.getText().toString();
        data.put("id",userid);
        data.put("name",userName);
        data.put("section",section);
        data.put("pass",userpass);
        data.put("email",userEmail);
        data.put("phone",phone);
        data.put("birth",birth);
        data.put("picpath",this.imagePath);
        return  data;
    }

    public void birthClick(View v){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        DatePickerDialog dp = new DatePickerDialog(RegisterActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,date,day,month,year
        );
        dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dp.show();


    }

    private void initComp(){
        inptUserId = findViewById(R.id.inpt_register_id);
        inptUserName = findViewById(R.id.inpt_register_name);
        inptUserEmai = findViewById(R.id.inpt_register_email);
        txtBirth =findViewById(R.id.txtbirth);
        inptUserPass = findViewById(R.id.inpt_register_pass);
        inptUserConfPass = findViewById(R.id.inpt_register_conformpass);
        btnRegister = findViewById(R.id.btn_register);
        txtBackToogin = findViewById(R.id.txtback_tologin_regact);
        txtphone = findViewById(R.id.inpt_register_phone);

        txtSection = findViewById(R.id.txtsection);
        picbtn = findViewById(R.id.cardpic);
        pic = findViewById(R.id.rpic);


    }

    public boolean valdation(){

        String email= inptUserEmai.getText().toString().trim();
        String name= inptUserName.getText().toString().trim();
        String birth= txtBirth.getText().toString().trim();
        String pass = inptUserPass.getText().toString().trim();
        String cpass = inptUserConfPass.getText().toString().trim();
        String section = txtSection.getText().toString().trim();
        String phone = txtphone.getText().toString().trim();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inptUserEmai.setError("please enter valid Email");
            return false;
        }

        else if(TextUtils.isEmpty(phone)){
            inptUserName.setError("Filed is Empty");

            return false;
        }
        else if(pass.length()<6 ){
            inptUserPass.setError("password length should me minim 6 character");
            return false;
        }
        else if(!pass.equals(cpass)){
            inptUserConfPass.setError("Password is not match try again");
            return false;
        }
        else if(TextUtils.isEmpty(name)){
            inptUserName.setError("Filed is Empty");

            return false;
        }
        else if(birth.equalsIgnoreCase("Select Date of Birth")){
            txtBirth.setError("Please Select Date of birth");

            return false;
        }
        else if(TextUtils.isEmpty(section)){
            txtSection.setError(" Filed is Empty");

            return false;
        }
        else if(TextUtils.isEmpty(imagePath)){
            Dialog d = new Dialog(this);
            d.setTitle("Error");
            d.setCancelable(true);
            d.setContentView(R.layout.errdialog);
            d.show();
            return false;
        }
        else if(!isDataInserted()){

            Toast.makeText(getApplicationContext(),"Data insertion Failed ",Toast.LENGTH_SHORT).show();
            return false;
        }



        else {return true;}

    }


    @Override
    public void onClick(View v) {
        String name =inptUserName.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            inptUserName.setError("! Filed is Empty");
            Toast.makeText(getApplicationContext(),"please first input your name",Toast.LENGTH_SHORT).show();
        }
        else {
            switch (v.getId()) {
                case R.id.cardpic:
                    Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(pickImage, 0);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(resultCode,resultCode,data);
        String imagePath;

        switch(requestCode){
            case 0:

                if(resultCode == RESULT_OK){
                    manageImageFromUri(data.getData());
                }

        }
    }

    private void manageImageFromUri(Uri imageUri) {

        Bitmap bitmap = null;
        String name = inptUserName.getText().toString();

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);
            //return absolute path
            String path =saveToInternalStorage(bitmap,name);
            Bitmap b= getPath(path,name);
            pic.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap != null) {

        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage,String imgName){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());


        File directory = cw.getDir("user_data", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imgName+".jpg");

        FileOutputStream fos = null;
        try {


            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            Bitmap c = Bitmap.createScaledBitmap(bitmapImage,400,400,true);
            c.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.d("after",c.getWidth()+" h"+c.getHeight());


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


    private Bitmap getPath(String path,String name) {

        try {
            File f=new File(path, name+".jpg");

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));


            this.imagePath= f.getPath();

            return b;


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
