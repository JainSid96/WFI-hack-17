package com.example.dcube.foodo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NearbyNgo_Activity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    private DatabaseReference mDatabase;
    private DatabaseReference userLocation;
    private DatabaseReference geofireReference;
    private DatabaseReference userLatitude;
    private DatabaseReference userLongitude;

    Dialog popup;
    TextView nameTextView;
    TextView distanceTextView;
    TextView addressTextView;
    Button contactNgo;

    String[] ngoInfo = new String[4];

    GeoFire geoFire;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_ngo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userLocation = mDatabase.child("userLocation");
        userLatitude = userLocation.child("Latitude");
        userLongitude = userLocation.child("Longitude");

        geofireReference = FirebaseDatabase.getInstance().getReference("geofire");
        geoFire = new GeoFire(geofireReference);

        //geoFire.setLocation("feedingIndia", new GeoLocation(28.55594, 77.234726));
        //geoFire.setLocation("ngo1", new GeoLocation(28.734371, 77.1197519));
        //geoFire.setLocation("ngo2", new GeoLocation(28.6994455, 77.1341652));
        //geoFire.setLocation("ngo3", new GeoLocation(28.6939352, 77.1441365));
        //geoFire.setLocation("ngo4", new GeoLocation(28.7021856, 77.1454668));
        //geoFire.setLocation("ngo5", new GeoLocation(28.7026784, 77.1256188));

    }

    private void addDataToFirebase(Double latitude, Double longitude) {

        double userLat = latitude;
        double userLong = longitude;
        userLatitude.setValue(latitude);
        userLongitude.setValue(longitude);

        /*
        geoFire.setLocation("userLocation", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {

                if (error != null) {

                    Toast.makeText(NearbyNgo_Activity.this, "Error saving location to geofire", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(NearbyNgo_Activity.this, "Location to geoFire saved.", Toast.LENGTH_SHORT).show();

                }

            }
        });
        */

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f));

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        googleMap.setOnInfoWindowClickListener(this);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location currentLocation) {

                final LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                addDataToFirebase(currentLocation.getLatitude(), currentLocation.getLongitude());

                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(userLocation).title("You're Here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13f));

                final GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(userLocation.latitude, userLocation.longitude), 100);
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {

                        Log.i(" geoQuery ", String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
                        Query locationQuery = FirebaseDatabase.getInstance().getReference().child("geofire");
                        locationQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                    Double latitude = (Double) snap.child("l/0").getValue();
                                    Double longitude = (Double) snap.child("l/1").getValue();
                                    LatLng meetLatLng = new LatLng(latitude, longitude);
                                    String ngo = snap.getKey();
                                    if (userLocation.latitude != latitude && userLocation.longitude != longitude) {
                                        googleMap.addMarker(new MarkerOptions().position(meetLatLng).title(ngo).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ngo", 100, 100))));
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(final Marker marker) {

                                final String name = marker.getTitle();

                                popup = new Dialog(NearbyNgo_Activity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                                popup.setContentView(R.layout.popup_dialog);
                                popup.setTitle("Ngo Info");
                                final String[] ngoName = {""};
                                final Double[] lat = new Double[1];
                                final Double[] lng = new Double[1];
                                final String[] phone = new String[1];

                                distanceTextView = (TextView) popup.findViewById(R.id.ngoDistance);
                                nameTextView = (TextView) popup.findViewById(R.id.ngoName);
                                addressTextView = (TextView) popup.findViewById(R.id.ngoAddress);
                                contactNgo = (Button) popup.findViewById(R.id.contactNgo);

                                mDatabase.child("ngoList").child(name).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //System.out.println("ngo data" + name + dataSnapshot.getValue());
                                        int i = 0;
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            ngoInfo[i] = childSnapshot.getValue().toString();
                                            i++;
                                        }

                                        lat[0] = Double.parseDouble(ngoInfo[0]);
                                        lng[0] = Double.parseDouble(ngoInfo[1]);
                                        ngoName[0] = ngoInfo[2];
                                        phone[0] = ngoInfo[3];

                                        String address = "";
                                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                        try {

                                            List<Address> listAddresses = geocoder.getFromLocation(lat[0], lng[0], 1);

                                            if (listAddresses != null && listAddresses.size() > 0) {

                                                Log.i("PlaceInfo", listAddresses.get(0).toString());


                                                if (listAddresses.get(0).getThoroughfare() != null) {

                                                    address += listAddresses.get(0).getThoroughfare() + ", ";

                                                }

                                                if (listAddresses.get(0).getLocality() != null) {

                                                    address += listAddresses.get(0).getLocality() + ", ";

                                                }

                                                if (listAddresses.get(0).getPostalCode() != null) {

                                                    address += listAddresses.get(0).getPostalCode();

                                                }


                                                //Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (IOException e) {

                                            e.printStackTrace();

                                        }

                                        float distance = calculateDistance(lat[0], lng[0], currentLocation.getLatitude(), currentLocation.getLongitude());

                                        nameTextView.setText(ngoName[0]);
                                        addressTextView.setText(address);
                                        distanceTextView.setText(distance + " Kms Away!");

                                        contactNgo.setVisibility(View.VISIBLE);
                                        contactNgo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                intent.setData(Uri.parse("tel:" + phone[0]));
                                                if (ContextCompat.checkSelfPermission(NearbyNgo_Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                                    ActivityCompat.requestPermissions(NearbyNgo_Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                                                } else {
                                                    try {
                                                        startActivity(intent);
                                                    } catch(SecurityException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                /*Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                                                phoneIntent.setData(Uri.parse(phone[0]));
                                                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    // TODO: Consider calling
                                                    //    ActivityCompat#requestPermissions
                                                    // here to request the missing permissions, and then overriding
                                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                    //                                          int[] grantResults)
                                                    // to handle the case where the user grants the permission. See the documentation
                                                    // for ActivityCompat#requestPermissions for more details.
                                                    return;
                                                }*/
                                                //startActivity(phoneIntent);
                                            }
                                        });

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                                popup.show();

                                return null;
                            }
                        });

                    }

                    @Override
                    public void onKeyExited(String key) {
                        System.out.println(String.format("Key %s is no longer in the search area", key));
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {
                        System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {
                        Toast.makeText(NearbyNgo_Activity.this, "There was an error with the query", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }


        };
        if(Build.VERSION.SDK_INT< 23)

        {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else

        {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


            }


        }
    }

    public float calculateDistance(double ngoLat, double ngoLng, double userLat, double userLng) {

        Location startPoint = new Location("User Location");
        startPoint.setLatitude(userLat);
        startPoint.setLongitude(userLng);

        Location endPoint = new Location("NGO Location");
        endPoint.setLatitude(ngoLat);
        endPoint.setLongitude(ngoLng);

        float distance = Float.parseFloat(String.format("%.2f", startPoint.distanceTo(endPoint) / 1000));

        return distance;
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
