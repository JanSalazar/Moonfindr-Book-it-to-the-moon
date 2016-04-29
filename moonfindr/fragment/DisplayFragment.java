package com.tanion.aston.rovery.moonfindr.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tanion.aston.rovery.moonfindr.R;
import com.tanion.aston.rovery.moonfindr.activity.MoonFactActivity;
import com.tanion.aston.rovery.moonfindr.activity.PhotoGalleryActivity;
import com.tanion.aston.rovery.moonfindr.activity.QuizActivity;
import com.tanion.aston.rovery.moonfindr.activity.StoryActivity;
import com.tanion.aston.rovery.moonfindr.utility.MoonAngle;

/**
 * Created by Aston Tanion on 26/04/2016.
 */
public class DisplayFragment extends Fragment implements SensorEventListener {
    public static final String TAG = "DisplayFragment";

    private EditText mInstructionEditText;
    private ImageView mCompassImageView;
    private Button mButton;
    private SensorManager mSensorManager;
    private MoonAngle mMoonAngle;

    private GoogleApiClient mClient;
    private float mCurrentDegreeAzimuth = 0f;
    private float mCurrentDegreePitch = 0f;
    private Double mPhoneLatitude = 0.0;
    private Double mPnoneLongitude = 0.0;
    float degreeAzimuth = 0.f;

    public static DisplayFragment newInstance() {

        Bundle args = new Bundle();

        DisplayFragment fragment = new DisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        mInstructionEditText = (EditText) view.findViewById(R.id.fragment_display_instruction);
        mCompassImageView = (ImageView) view.findViewById(R.id.fragment_display_compass);

        mButton = (Button) view.findViewById(R.id.fragment_display_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent moonFactActivityIntent = MoonFactActivity.newIntent(getActivity());
                startActivity(moonFactActivityIntent);
            }
        });

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.display_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.display_menu_story:
                Intent storyActivityIntent = StoryActivity.newIntent(getActivity());
                startActivity(storyActivityIntent);
                return false;
            case R.id.display_menu_picture:
                Intent photoGalleryActivityIntent = PhotoGalleryActivity.newIntent(getActivity());
                startActivity(photoGalleryActivityIntent);
                return false;
            case R.id.display_menu_quiz:
                Intent quizActivityIntent = QuizActivity.newIntent(getActivity());
                startActivity(quizActivityIntent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findDeviceLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mPhoneLatitude =  location.getLatitude();
                        mPnoneLongitude =  location.getLongitude();

                        Log.i(TAG, "Phone latitude : " + location.getLatitude());
                        Log.i(TAG, "Phone longitude : " + location.getLongitude());
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth_angle = event.values[0];
        float pitch_angle = event.values[1];
        float roll_angle = event.values[2];

        mMoonAngle = new MoonAngle(0, mPhoneLatitude, mPnoneLongitude);

        mCompassImageView.setRotation(azimuth_angle + (float) mMoonAngle.getAzimuthAngle());

        if (azimuth_angle > 180) azimuth_angle = azimuth_angle - 360;

        mInstructionEditText.setText(((int) azimuth_angle) + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
