package com.example.nowmoneytask.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nowmoneytask.R;
import com.example.nowmoneytask.util.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        IntentLauncher launcher = new IntentLauncher();
        launcher.start();

    }


    private class IntentLauncher extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SessionManager sessionManager = new SessionManager(SplashActivity.this);

            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        }
    }
}