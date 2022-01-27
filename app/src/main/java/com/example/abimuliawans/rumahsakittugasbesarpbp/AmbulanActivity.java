package com.example.abimuliawans.rumahsakittugasbesarpbp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.abimuliawans.rumahsakittugasbesarpbp.models.PlaceInfo;

public class AmbulanActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "MenuUtamaActivity";

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71,136));
    private AutoCompleteTextView mSearchText;
    private ImageView mGPS;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Boolean mLocationPermissionGranted = false;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulan);
        mSearchText = findViewById(R.id.input_search);
        mGPS = findViewById(R.id.ic_gps);

        getLocationPermission();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReadyMap: Map is Ready");
        mMap = googleMap;

        if(mLocationPermissionGranted)
        {
            getDeviceLocation();

            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                return;
            {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                init();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequsetPermissionResult: called");
        mLocationPermissionGranted=false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0)
                {
                    for (int i =0; i < grantResults.length; i++)
                    {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted=false;
                            Log.d(TAG,"onRequestpermissionResult: permission failed");
                            break;
                        }
                    }
                }
                mLocationPermissionGranted=true;
                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                initMap();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void moveCamera(LatLng latLng, float zoom, String title)
    {
        Log.d(TAG, "moveCamera : moving the camera to lat: " +latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location"))
        {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getDeviceLocation()
    {
        Log.d(TAG, "getDeviceLocation: getting the devices current location..");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try
        {
            if(mLocationPermissionGranted)
            {
                com.google.android.gms.tasks.Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,"My Location");
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(AmbulanActivity.this, "unable to get current location",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }
        catch (SecurityException e)
        {
            Log.d(TAG, "getDeviceLocation: SecurityException: "+e.getMessage());
        }
    }

    private void getLocationPermission()
    {
        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted =true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void geoLocate()
    {
        Log.d(TAG, "geoLocated: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(AmbulanActivity.this);
        List<Address> list = new ArrayList<>();

        try
        {
            list= geocoder.getFromLocationName(searchString, 1);
        }
        catch (IOException e)
        {
            Log.d(TAG, "geoLocate: IOException: " +e.getMessage());
        }

        if(list.size() > 0)
        {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " +address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void initMap()
    {
        Log.d(TAG, "initMap: initializing map..");
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync( AmbulanActivity.this);
    }

    private void init()
    {
        Log.d(TAG, "init: initializing..");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction()==KeyEvent.ACTION_DOWN
                        || event.getAction()==KeyEvent.KEYCODE_ENTER)
                {
                    geoLocate();
                }
                return false;
            }
        });

        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick clicked gps icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new
            AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    hideSoftKeyboard();
                    final AutocompletePrediction item =mPlaceAutocompleteAdapter.getItem(position);
                    final String placeId = item.getPlaceId();
                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                            .getPlaceById(mGoogleApiClient,placeId);
                    placeResult.setResultCallback(mUpdatePlaceDetailCallback);
                }
            };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailCallback = new
            ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(@NonNull PlaceBuffer places) {
                    if(!places.getStatus().isSuccess()){
                        Log.d(TAG, "onResult: Place query did not complete successfully!" + places.getStatus().toString());
                        places.release();
                        return;
                    }
                    final Place place = places.get(0);
                    try{
                        mPlace = new PlaceInfo();
                        mPlace.setName(place.getName().toString());
                        mPlace.setAddress(place.getAddress().toString());
                        mPlace.setId(place.getId().toString());
                        mPlace.setLatLng(place.getLatLng());
                        mPlace.setRating(place.getRating());
                        mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                        mPlace.setWebsiteUri(place.getWebsiteUri());
                        Log.d(TAG, "onResult: place " + mPlace.toString());
                    }catch (NullPointerException e){
                        Log.e(TAG, "onResult: NullPointerException " + e.getMessage());
                    }
                    moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM,
                            mPlace.getName());
                    places.release();
                }
            };
}

