package com.police.org.tpcs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class RegisteredVehical extends AppCompatActivity {
private Toolbar toolbar;

WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_vehical);
        toolbar=findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitle("Verify Vehicle");
        browser = findViewById(R.id.webviewsearch);
        if(isConnected()) {
            startWebView("http://www.excise.gos.pk/vehicle/vehicle_search");
        }
        else{
            startWebView("file:///android_asset/html/dance.html");

        }

    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(RegisteredVehical.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }

    public void startWebView(String url){
        browser.setWebViewClient(new WebViewClient(){
            ProgressDialog progressDialog;
            public boolean shouldOverrideUrlLoading(WebView view ,String url){
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource(WebView view,String url){
                if (progressDialog ==null){
                    progressDialog = new ProgressDialog(RegisteredVehical.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();

                }
            }

            public  void onPageFinished(WebView view,String url){
                try{
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
                catch (Exception e){e.printStackTrace();}
            }
        });


        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDisplayZoomControls(true);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setBuiltInZoomControls(true);

        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        browser.setScrollbarFadingEnabled(false);
        browser.loadUrl(url);
    }

    private boolean isConnected(){
        ConnectivityManager conM =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conM !=null){
            NetworkInfo[]info = conM.getAllNetworkInfo();
          if(info !=null){
              for(int i=0;i<info.length;i++)
                  if(info[i].getState()==NetworkInfo.State.CONNECTED){
                      return true;
              }

          }

        }
         return false;
    }

}








