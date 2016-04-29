package com.tanion.aston.rovery.moonfindr.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tanion.aston.rovery.moonfindr.R;
import com.tanion.aston.rovery.moonfindr.dialog.DatePickerDialog;
import com.tanion.aston.rovery.moonfindr.utility.MoonLocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aston Tanion on 27/04/2016.
 */
public class MoonFactFragment extends Fragment {
    public static final String TAG = "MoonFactFragment";

    private static final int DATE_PICKER_REQUEST = 0;
    private ImageView mPhaseImaveView;
    private EditText mLongitudeEditView;
    private EditText mAgeEditView;
    private EditText mPhaseEditView;
    private EditText mDistanceEditView;
    private EditText mLatitudeEditView;
    private Button mButton;
    private Resources mResources;

    public static MoonFactFragment newInstance() {

        Bundle args = new Bundle();

        MoonFactFragment fragment = new MoonFactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResources = getResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fact, container, false);

        mPhaseImaveView = (ImageView) view.findViewById(R.id.fact_fragment_moon_phase_image);
        mPhaseEditView = (EditText) view.findViewById(R.id.fact_fragment_moon_phase);
        mAgeEditView = (EditText) view.findViewById(R.id.fact_fragment_moon_age);
        mDistanceEditView = (EditText) view.findViewById(R.id.fact_fragment_moon_distance);
        mLatitudeEditView = (EditText) view.findViewById(R.id.fact_fragment_moon_latitude);
        mLongitudeEditView = (EditText) view.findViewById(R.id.fact_fragment_moon_longitude);

        mButton = (Button) view.findViewById(R.id.fact_fragment_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerDialog dialog = DatePickerDialog.newInstance(new Date());
                dialog.setTargetFragment(MoonFactFragment.this, DATE_PICKER_REQUEST);
                dialog.show(fm, DatePickerDialog.TAG);
            }
        });

        updateUI(Calendar.getInstance());

        return view;
    }

    private void updateUI(Calendar calendar) {
        MoonLocation moonLocation = new MoonLocation(calendar);

        mPhaseEditView.setText(String.format(
                mResources.getString(R.string.moon_phase), moonLocation.getPhase()));
        mAgeEditView.setText(String.format(
                mResources.getString(R.string.moon_age), moonLocation.getAge()));
        mDistanceEditView.setText(String.format(
                mResources.getString(R.string.moon_distance), moonLocation.getDistance()));
        mLatitudeEditView.setText(String.format(
                mResources.getString(R.string.moon_latitude), moonLocation.getLatitude()));
        mLongitudeEditView.setText(String.format(
                mResources.getString(R.string.moon_longitude), moonLocation.getLongitude()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy", Locale.getDefault());
        mButton.setText(dateFormat.format(calendar.getTime()));

        Drawable drawable;
        if (moonLocation.getPhase().equals("Waxing crescent")) {
            drawable = mResources.getDrawable(R.drawable.waning_crescent);
        } else if (moonLocation.getPhase().equals("First quarter")) {
            drawable = mResources.getDrawable(R.drawable.first_quarter);
        } else if (moonLocation.getPhase().equals("Waxing gibbous")) {
            drawable = mResources.getDrawable(R.drawable.waxing_gibbous);
        } else if (moonLocation.getPhase().equals("Full moon")) {
            drawable = mResources.getDrawable(R.drawable.full_moon);
        } else if (moonLocation.getPhase().equals("Last quarter")) {
            drawable = mResources.getDrawable(R.drawable.last_quarter);
        } else if (moonLocation.getPhase().equals("Waning crescent")) {
            drawable = mResources.getDrawable(R.drawable.waning_gibbous);
        } else if (moonLocation.getPhase().equals("Waning gibbous")) {
            drawable = mResources.getDrawable(R.drawable.waning_gibbous);
        } else {
            drawable = mResources.getDrawable(R.drawable.new_moon);
        }

        mPhaseImaveView.setImageDrawable(drawable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DATE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) return;

                Date date = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                updateUI(calendar);
            }
        }
    }
}
