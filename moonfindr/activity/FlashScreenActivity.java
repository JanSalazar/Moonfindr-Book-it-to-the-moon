package com.tanion.aston.rovery.moonfindr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tanion.aston.rovery.moonfindr.R;

public class FlashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        Intent displayActivityIntent = DisplayActivity.newIntent(this);
        startActivity(displayActivityIntent);
        finish();
    }
}
