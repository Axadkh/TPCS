package com.police.org.tpcs;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final session sess = new session(splashActivity.this);
        if (Build.VERSION.SDK_INT < 16) {
            //  Android 4.0 and Lower
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
             //Android 4.1
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        }

        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                if(sess.getId() !=""){
                    Intent i = new Intent(splashActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(splashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },3000);
    }
}
