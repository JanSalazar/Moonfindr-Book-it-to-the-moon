package com.tanion.aston.rovery.moonfindr.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tanion.aston.rovery.moonfindr.fragment.QuizFragment;

/**
 * Created by Aston Tanion on 27/04/2016.
 */
public class QuizActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, QuizActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return QuizFragment.newInstance();
    }
}
