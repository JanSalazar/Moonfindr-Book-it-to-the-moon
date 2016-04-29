package com.tanion.aston.rovery.moonfindr.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tanion.aston.rovery.moonfindr.fragment.DisplayFragment;

/**
 * Created by Aston Tanion on 25/04/2016.
 */
public class DisplayActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;

    public static Intent newIntent(Context context) {
        return new Intent(context, DisplayActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return DisplayFragment.newInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog;
            errorDialog = GooglePlayServicesUtil
                    .getErrorDialog(errorCode, this, REQUEST_ERROR,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // Leave if services are unavailable.
                                    finish();
                                }
                            });

            errorDialog.show();
        }
    }
}
