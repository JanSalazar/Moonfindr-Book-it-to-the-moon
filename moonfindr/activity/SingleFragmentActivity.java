package com.tanion.aston.rovery.moonfindr.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tanion.aston.rovery.moonfindr.R;

/**
 * Created by Aston Tanion on 25/04/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);

        if (fragment == null) {
            fragment = createFragment();

            if (fragment == null) return;

            fm.beginTransaction()
                    .add(R.id.single_fragment_container, fragment)
                    .commit();
        }
    }
}
