package com.pipalapipapalapi.smartplaces.activity;

import com.pipalapipapalapi.smartplaces.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class SplashScreenActivity extends ActionBarActivity {
	
    private static int SPLASH_TIME_OUT = 5000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		new Handler().postDelayed(new Runnable() {
			
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
	}

}
