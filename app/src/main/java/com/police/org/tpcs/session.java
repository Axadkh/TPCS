package com.police.org.tpcs;

import android.content.Context;
import android.content.SharedPreferences;

public class session {


    private String id;
    Context context;
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {

        this.dateTime = dateTime;
    }

    SharedPreferences sharedPre;
    public session(Context c) {
        this.context =c;
        sharedPre = c.getSharedPreferences("userinfo",Context.MODE_PRIVATE);


    }


    public String getId() {
        id= sharedPre.getString("userdata","");
        return id;
    }

    public void setId(String id) {
        this.id = id;
        sharedPre.edit().putString("userdata",id).commit();

    }

    public void removeSession(){
        sharedPre.edit().clear().commit();
    }


}
