package com.police.org.tpcs;

public class ChallanList {

    boolean checked;
    String title;
    int itemPrice;
    public ChallanList(){}

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ChallanList(String title, int itemPrice, boolean checked){

        this.checked = checked;
        this.title = title;
        this.itemPrice = itemPrice;


    }
}
