package com.police.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatMaker {
public FormatMaker(){

}

    public String Format(Integer number){
        String[] suffix = new String[]{"k","m","b","t"};
        int size = (number.intValue() != 0) ? (int) Math.log10(number) : 0;
        if (size >= 3){
            while (size % 3 != 0) {
                size = size - 1;
            }
        }
        double notation = Math.pow(10, size);
        String result = (size >= 3) ? + (Math.round((number / notation) * 100) / 100.0d)+suffix[(size/3) - 1] : + number + "";
        return result;
    }

    public void timeDiff(String t){

    String startTime = t;
    String result=null;
    String ctime =getCurrentTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
        Date time1=null;
        Date time2 = null;

        try{
           time1 = df.parse(startTime);
           time2 =df.parse(ctime);
           long diff = time1.getTime() - time2.getTime();
           long sec = diff/1000 %60;
           long minuts = diff /(60 *1000) /60;
           long hour = diff /(60*60*1000)%24;

           result =""+hour +" : " + minuts +" : " +sec;


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public String dateDiff(String t){

        String startTime = t;
        String result=null;
        String ctime =getCurrentTime();
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");
        Date time1=null;
        Date time2 = null;

        try{
            time1 = df.parse(startTime);
            time2 =df.parse(ctime);
            long diff = time1.getTime() - time2.getTime();
            long sec = diff/1000 %60;
            long minuts = diff /(60 *1000) %60;
            long hour = diff /(60*60*1000)%24;

            result =""+hour +" : " + minuts +" : " +sec;


        }
        catch (Exception e){
            e.printStackTrace();
        }
return  result;
    }
    public void timer(){


    }

    public String renmingTime(String startTime,int duration) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");




        String s1 =null;

        try {
            Date t1= df.parse(startTime);
            Date t2 = df.parse(df.format(c.getTime()));

              int h = t1.getHours()-t2.getHours()+duration;
            int m =t1.getMinutes()-t2.getMinutes();
            int s = t1.getSeconds()-t2.getSeconds();


            s1 = Math.abs(h) +":"+ Math.abs(m) +":"+Math.abs(s);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return  s1;
    }



    public String getCurrentTime(){
    SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");

        Calendar c = Calendar.getInstance();
       String time = df.format(c.getTime());
       return time;
    }

    public String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy");

        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());
        return date;
    }
}
