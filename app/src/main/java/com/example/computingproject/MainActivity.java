package com.example.computingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
private  final long TIME_MS=5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                public void run() {
                                    // TODO: Your application init goes here.
                                    Intent minIntent = new Intent(MainActivity.this, login.class);
                                    MainActivity.this.startActivity(minIntent);
                                    MainActivity.this.finish();
                                }
                            },
                TIME_MS);
    }
}