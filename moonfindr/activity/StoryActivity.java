package com.tanion.aston.rovery.moonfindr.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tanion.aston.rovery.moonfindr.R;
import com.tanion.aston.rovery.moonfindr.fragment.StoryFragment;

/**
 * Created by Aston Tanion on 24/04/2016.
 */
public class StoryActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, StoryActivity.class);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        if (viewPager != null) {
            viewPager.setAdapter(new FragmentPagerAdapter(fm) {
                @Override
                public Fragment getItem(int position) {
                    return StoryFragment.newInstance(position);
                }

                @Override
                public int getCount() {
                    return 3;
                }
            });
        }
    }
}
