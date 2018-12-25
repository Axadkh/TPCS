package com.police.org.tpcs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;



import java.util.ArrayList;

public class ChalanAdapter extends ArrayAdapter<ChallanList> {

    TextView textvie;
    TextView rate ;
    CheckBox chkb ;



    public ChalanAdapter(Context c,ArrayList<ChallanList> chlist){

        super(c,0,chlist);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){


        return initView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){

        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView ==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chalan_list, parent, false);
        }

      textvie = convertView.findViewById(R.id.txt_listtite);
        rate = convertView.findViewById(R.id.txtrate);
       chkb = convertView.findViewById(R.id.chklist);
        ChallanList currentItem = getItem(position);
        if(currentItem !=null) {
            textvie.setText(currentItem.getTitle());
            rate.setText(Integer.toString(currentItem.getItemPrice()));
            chkb.setChecked(currentItem.isChecked());

        }
        return convertView;

    }


}
