package com.tanion.aston.rovery.moonfindr.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tanion.aston.rovery.moonfindr.fragment.MoonFactFragment;

/**
 * Created by Aston Tanion on 27/04/2016.
 */
public class MoonFactActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, MoonFactActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return MoonFactFragment.newInstance();
    }
}
