package com.example.arsalansiddiq.beem.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.arsalansiddiq.beem.R;
import com.example.arsalansiddiq.beem.databases.BeemPreferences;
import com.example.arsalansiddiq.beem.utils.Constants;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        final SharedPreferences loginStatusPreferences = getSharedPreferences(Constants.LOGIN_STATUS, MODE_PRIVATE);
        final int status = loginStatusPreferences.getInt(Constants.KEY_LOGIN_STATUS, 0);

        /**
         * Thread Handler to hold Splash Screen for seconds
         */
        final Handler handler = new Handler();
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = null;
                    
                    if (status == 0) {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    } else if (status == 1) {
                        intent = new Intent(SplashActivity.this, NavigationDrawerActivity.class);
                    }
                    
                    startActivity(intent);

                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
