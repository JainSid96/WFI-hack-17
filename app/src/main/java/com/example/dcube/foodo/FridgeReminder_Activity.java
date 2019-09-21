package com.example.dcube.foodo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FridgeReminder_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String APP_ID = "AIzaSyCMGmZNqhbmHMH_uK6hED0FPP-Q4XOd7W4";

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final String TAG ="HELLO" ;
    private GoogleApiClient googleApiClient;
    TextView t1;
    Spinner spinner2;
    Spinner spinner3;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_reminder_);
        t1 = (TextView) findViewById(R.id.textView11);
        b1 = (Button) findViewById(R.id.button3);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Ashok Vihar to Rithala");
        list.add("Rohini to Pitampura");
        list.add("Kohat Enclave to Inderlok");
        list.add("Inderlok to Karol Bagh");
        list.add("Janpath to CP");
        list.add("Karol Bagh to CP ");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list1 = new ArrayList<String>();
        list1.add("Reliance Fresh");
        list1.add("Krishna Store");
        list1.add("Grofer Store");
        list1.add("Kisan Dairy");
        list1.add("Mother Diary");
        list1.add("Big Bazaar");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNotification(getNotification("Seems like your near to a shop , Plan your meal."), 5000);
                //scheduleNotification(getNotification("Reliance Fresh is near by please check your fridge."), 10000);
            }
        });


    }

    public void addClick(View view){
        Toast.makeText(this, "Reminder Set", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (FridgeReminder_Activity.this , FridgeMenu_Activity.class);
        startActivity(intent);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Smart Fridge");
        builder.setContentText(content);
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.siri);
        builder.setSound(sound);

        builder.setSmallIcon(R.drawable.alert);
        return builder.build();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(FridgeMenu_Activity.class.getSimpleName(), "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
            String l1 = Double.toString(lat);
            String l2 = Double.toString(lon);
            try{
                Geocoder geo = new Geocoder(FridgeReminder_Activity.this.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(lat, lon, 1);
                if (addresses.isEmpty()) {
                    t1.setText("Waiting for Location");
                }
                else {
                    if (addresses.size() > 0) {
                       /* Log.d(TAG,addresses.get(0).getFeatureName() + ","+
                                " + addresses.get(0).getLocality() +",
                                " + addresses.get(0).getAdminArea() + ",
                                " + addresses.get(0).getCountryName()"); */
                        t1.setText(addresses.get(0).getAddressLine(0));

                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            // String units = "imperial";
            // String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
            //   lat, lon, units, APP_ID);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(FridgeMenu_Activity.class.getSimpleName(), "Can't connect to Google Play Services!");
    }



}
