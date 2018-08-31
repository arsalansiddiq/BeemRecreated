package com.example.arsalansiddiq.beem.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arsalansiddiq.beem.R;
import com.example.arsalansiddiq.beem.databases.BeemDatabase;
import com.example.arsalansiddiq.beem.databases.BeemPreferences;
import com.example.arsalansiddiq.beem.interfaces.AttandanceInterface;
import com.example.arsalansiddiq.beem.interfaces.EndAttendanceInterface;
import com.example.arsalansiddiq.beem.models.responsemodels.AttandanceResponse;
import com.example.arsalansiddiq.beem.models.responsemodels.LoginResponse;
import com.example.arsalansiddiq.beem.utils.AppUtils;
import com.example.arsalansiddiq.beem.utils.Constants;
import com.example.arsalansiddiq.beem.utils.NetworkUtils;

import java.io.File;

import retrofit2.Response;


public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, LocationListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    private com.github.clans.fab.FloatingActionButton fab_menu_startBreak, fab_menu_endDay, fab_menu_sale, fab_menu_attandance, fab_menu_target,
            fab_menu_map;
    private LocationManager locationManager;
    private float latitude, longitude;
    private BeemDatabase beemDatabase;
    private AppUtils appUtils;

    private int meetingStatus = 0;
    private BeemPreferences beemPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        beemPreferences = new BeemPreferences(NavigationDrawerActivity.this);
        beemDatabase = new BeemDatabase(this);
        beemDatabase.getReadableDatabase();

        appUtils = new AppUtils(this);

        fab_menu_startBreak = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_startBreak);
        fab_menu_endDay = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_endDay);
        fab_menu_sale = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_sale);
        fab_menu_attandance = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_attandance);
        fab_menu_target = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_target);
        fab_menu_map = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_map);


        fab_menu_startBreak.setOnClickListener(this);
        fab_menu_endDay.setOnClickListener(this);
        fab_menu_sale.setOnClickListener(this);
        fab_menu_attandance.setOnClickListener(this);
        fab_menu_target.setOnClickListener(this);
        fab_menu_map.setOnClickListener(this);

        hideTopFabs();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = this.getSharedPreferences(Constants.ATTENDANCE_STATUS, MODE_PRIVATE);
        int id = preferences.getInt(Constants.KEY_ATTENDANCE_STATUS, 0);
        if (id == 1) {
            hideBottomFabs();
        } else {
            hideTopFabs();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            File userImageFile = appUtils.getImageFile(imageBitmap);

            NetworkUtils networkUtils = new NetworkUtils(NavigationDrawerActivity.this);

            if (networkUtils.isNetworkConnected()) {
                if (meetingStatus == 1) {

                    final int status = 1;
                    final LoginResponse loginResponse = beemDatabase.getUserDetail();

                    networkUtils.attandanceBA(appUtils.getDate(), loginResponse.getUserId(), loginResponse.getName(), userImageFile,
                            appUtils.getTime(), latitude, longitude, status, new AttandanceInterface() {
                                @Override
                                public void success(Response<AttandanceResponse> attandanceResponseResponse) {
                                    if (attandanceResponseResponse.body().getStatus() == 1) {
                                        beemPreferences.initialize_and_createPreferences(attandanceResponseResponse.body().getId());
                                        beemPreferences.initialize_and_createPreferences_forAttendanceStatus(1);

                                        beemDatabase.insertMark_BA_attendanceInfo(loginResponse.getUserId(), appUtils.getDate(), loginResponse.getName(),
                                                appUtils.imageBitmapToBiteConversion(imageBitmap), appUtils.getTime(),
                                                latitude, longitude, attandanceResponseResponse.body().getStatus(), attandanceResponseResponse.body().getId());

                                        hideBottomFabs();
                                    } else {
                                        beemDatabase.insertMark_BA_attendanceInfo(loginResponse.getUserId(), appUtils.getDate(), loginResponse.getName(),
                                                appUtils.imageBitmapToBiteConversion(imageBitmap), appUtils.getTime(),
                                                latitude, longitude, 0, 0);
                                        Toast.makeText(NavigationDrawerActivity.this, "Something Went Wrong Please Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void failed(String error) {
                                    beemDatabase.insertMark_BA_attendanceInfo(loginResponse.getUserId(), appUtils.getDate(), loginResponse.getName(),
                                            appUtils.imageBitmapToBiteConversion(imageBitmap), appUtils.getTime(),
                                            latitude, longitude, 0, 0);
                                    Toast.makeText(NavigationDrawerActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else if (meetingStatus == 0){

                    SharedPreferences preferences = this.getSharedPreferences(Constants.BA_ATTENDANCE_ID, MODE_PRIVATE);
                    int id = preferences.getInt(Constants.KEY_BA_ATTENDANCE_ID, 0);

                   Log.i("thisClass", "token" + id);

                    networkUtils.endAttandenceBA(id, appUtils.getTime(), latitude, longitude, userImageFile,
                            new EndAttendanceInterface() {
                                @Override
                                public void success(Response<AttandanceResponse> attandanceResponseResponse) {
                                    if (attandanceResponseResponse.body().getStatus() == 1) {
                                        beemPreferences.initialize_and_createPreferences_forAttendanceStatus(0);
                                        hideTopFabs();

                                    } else {
                                        Toast.makeText(NavigationDrawerActivity.this, "Something Went Wrong Please Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void failed(String error) {
                                    Toast.makeText(NavigationDrawerActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_attandance) {
            // Handle the camera action
        } else if (id == R.id.nav_target) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_target) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_map) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_menu_attandance:

                meetingStatus = 1;

                getLocation();
                dispatchTakePictureIntent();

                break;
                case R.id.fab_menu_endDay:

                    meetingStatus = 0;

                    getLocation();
                    dispatchTakePictureIntent();

                    break;
            case R.id.fab_menu_map:
                break;
            case R.id.fab_menu_sale:
                Intent intent = new Intent(NavigationDrawerActivity.this, SalesActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_menu_startBreak:
                break;
            case R.id.fab_menu_target:
                break;
        }
    }

    void hideTopFabs() {
        fab_menu_startBreak.setVisibility(View.GONE);
        fab_menu_endDay.setVisibility(View.GONE);

        //**Temp
        //**Default Mode: GONE
        fab_menu_sale.setVisibility(View.GONE);
        //***

        fab_menu_attandance.setVisibility(View.VISIBLE);
        fab_menu_map.setVisibility(View.VISIBLE);
    }

    void hideBottomFabs() {
        fab_menu_startBreak.setVisibility(View.VISIBLE);
        fab_menu_endDay.setVisibility(View.VISIBLE);

        //***temp
        //**Default Mode: VISIBLE
        fab_menu_sale.setVisibility(View.VISIBLE);
        //*****

        fab_menu_attandance.setVisibility(View.GONE);
        fab_menu_map.setVisibility(View.GONE);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = (float) location.getLatitude();
        longitude = (float) location.getLongitude();

        Log.i("Location", latitude + "  " + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("onStatusChanged", provider + "  " + status + "   " + extras);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("onProviderEnabled", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("onProviderDisabled", provider);
    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},99);
            return;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, NavigationDrawerActivity.this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;
    }

}
