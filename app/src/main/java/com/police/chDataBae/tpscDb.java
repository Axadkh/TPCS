package com.police.chDataBae;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class tpscDb extends SQLiteOpenHelper {


    private static final String dbName = "tcsdb";
    private static final int dbVersion = 1;
    //table  names
    private static final String tblUser = "user";
    private static final String tblAudit = "audit";
    private static final String tblLocation = "location";

    private static final String tblchallanInfo = "challan_info";

    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE "+ tblLocation + "( id  INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"lat REAL,longi REAL, chid INTEGER,foreign key (chid) references "+tblchallanInfo+"(id))";

    // create table users is ok
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+ tblUser + "( id  TEXT PRIMARY KEY,"
    +"name TEXT,section TEXT, email TEXT, phone TEXT,dob TEXT,password TEXT,pic TEXT)";


    // create challaninfo is ok
     static final String location ="location Text,";
    private static final String CREATE_TABLE_CHALLAN_INFO = "CREATE TABLE "+tblchallanInfo +
                    "( id  INTEGER PRIMARY KEY AUTOINCREMENT, vid TEXT, name TEXT, vtype TEXT,"+
     " licenseNo TEXT, nic TEXT,mobile TEXT, date TEXT, time TEXT, challan1 TEXT, challan2 TEXT, "+
    "location TEXT,totalAmount INTEGER,paymentType TEXT, comment TEXT, pic1 TEXT,pic2 TEXT,userid Text, "+
    "FOREiGN KEY (userid) REFERENCES user(id))";

    // create table audit is ok
    private static final String CREATE_TABLE_AUDIT = "CREATE TABLE "+tblAudit+
           "( id  INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, time text,"+
   "userid TEXT, FOREIGN KEY(userid) REFERENCES user(id))";






    //constructor
    public tpscDb(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_AUDIT);
        db.execSQL(CREATE_TABLE_CHALLAN_INFO);
        db.execSQL(CREATE_TABLE_LOCATION);
    }
public Cursor getTodaystat(String date){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+tblchallanInfo +" where date = ? ",new String[] {date});
        return c;
}
    public Cursor getAvg(String uid){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT AVG(id) FROM "+tblchallanInfo +" WHERE userid = ?"  ,new String[] {uid});
        return c;
    }
public Cursor searchChallan(String id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT challan_info.id, challan_info.name," +
                " challan_info.licenseNo," +
                "challan_info.date, challan_info.time, challan_info.challan1," +
                " challan_info.location,challan_info.paymentType," +
                tblUser+".name from "+tblchallanInfo +
                " join user " +
                " on "+ tblUser + ".id = "+tblchallanInfo+".userid"
                +" where "+tblchallanInfo +".vid = ? ",new String[] {id}
                 );


        return cursor;
}

    public Cursor getLastChallColum(){
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT id from "+ tblchallanInfo + " order by id DESC limit 1";
        Cursor c = db.rawQuery(query,null);
        return c;


    }

public  boolean insetLocation(double lat,double longi,int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lat",lat);
        values.put("longi",longi);
        values.put("chid",id);
      long i=  db.insert(tblLocation,null,values);
      db.close();
      if(i<0){
          return false;
      }
      else {return true;}

}

public Cursor getVids(){


        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT vid from "+tblchallanInfo, null);

        return c;

}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + tblUser);
        db.execSQL("DROP TABLE IF EXISTS " + tblAudit);
        db.execSQL("DROP TABLE IF EXISTS " + tblchallanInfo);
        db.execSQL("DROP TABLE IF EXISTS " + tblLocation);

        onCreate(db);
    }


public Cursor getAudit(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT time FROM "+tblAudit +" where userid = ?",new String[]{id});
        return c;
}

public Cursor getTotalCash(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sum(totalAmount) from "+tblchallanInfo +" WHERE userid = ?",new String[] {id});
        return c;
}

    //insert into user
    public boolean inserIntouser(HashMap<String,String> data)  {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id",data.get("id"));
        values.put("name",data.get("name"));
        values.put("section",data.get("section"));
        values.put("email",data.get("email"));
        values.put("phone",data.get("phone"));
        values.put("dob",data.get("birth"));
        values.put("password",data.get("pass"));
        values.put("pic",data.get("picpath"));
        // insert row
        long result = db.insert(tblUser, null, values);
        db.close();
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean vaildateUser( String id , String pass){
        boolean isvaild=false;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor =null;
        cursor = db.rawQuery("SELECT * FROM " + tblUser + " WHERE  id = ? AND password = ?", new String[]{id, pass});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
             isvaild = true;
            }
        }
        else{
            isvaild = false;
        }
        return isvaild;
    }

    public  boolean updateUser(HashMap<String,String> data,String id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",data.get("email"));
        contentValues.put("phone",data.get("phone"));

        int i =db.update(tblUser, contentValues, "id = ?",new String[] { id });
        if(i>0){

        return true;}
        else {return false;}
    }


    public HashMap<String,String> getUser(String id){

        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor =null;
        HashMap<String, String> data = new HashMap<>();
        cursor = db.rawQuery("SELECT id,name,section,email,phone,dob,pic FROM " + tblUser + " WHERE  id = ?", new String[]{id});
        while (cursor.moveToNext()) {
           data.put("id",cursor.getString(0));
            data.put("name",cursor.getString(1));
            data.put("section",cursor.getString(2));
            data.put("email",cursor.getString(3));
            data.put("phone",cursor.getString(4));
            data.put("dob",cursor.getString(5));
            data.put("pic",cursor.getString(6));

        }
        db.close();
        return data;
    }

    public boolean insertChallanInfo(HashMap<String,String> challandata) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("vid",challandata.get("vehicleId"));
        values.put("name",challandata.get("name"));
        values.put("vtype",challandata.get("vehicleType"));
        values.put("licenseNo",challandata.get("userLicense"));
        values.put("nic",challandata.get("userNic"));
        values.put("mobile",challandata.get("userMobile"));
        values.put("date",challandata.get("date"));
        values.put("time",challandata.get("time"));

        values.put("challan1",challandata.get("challan1"));
        values.put("challan2",challandata.get("challan2"));
        values.put("location",challandata.get("location"));
        values.put("totalAmount",challandata.get("totalAmount"));
        //add this ccolumn
        values.put("paymentType",challandata.get("payment"));
        values.put("comment",challandata.get("comment"));
        values.put("pic1",challandata.get("imagepath"));
        values.put("pic2",challandata.get("imagepath2"));
        //add this colum
        values.put("userid",challandata.get("userid"));
        // insert row


        long result = db.insert(tblchallanInfo, null, values);
        db.close();

        Log.d("databaseactiviy","interted record "+result );

        for(HashMap.Entry<String,String> e :challandata.entrySet()){
            Log.d("hashmap",e.getKey()+" :"+ e.getValue());
        }
        if(result==-1){
            return false;
        }
        else{
            return true;
        }

    }

    //get user data

    public void getuser(){
        Cursor cursor =null;
        while (cursor.moveToNext()) {
            HashMap<String, String> data = new HashMap<>();
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String emai = cursor.getString(2);
            String password = cursor.getString(3);
            String pic = cursor.getString(4);

        }
}

    // retrive challndata
   public Cursor getchallaninfo(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+tblchallanInfo +" WHERE userid = ? ",new String[]{id});

        return cursor;

    }

    public boolean inserAduit(String id,String date,String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("date",date);
        values.put("time",time);
        values.put("userid",id);
        long result = db.insert(tblAudit, null, values);
        db.close();
        if(result==-1){
            return false;
        }
        else{


            return true;
        }
    }


}
