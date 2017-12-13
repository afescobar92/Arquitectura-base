package com.cosmo.arquitecturamvpbase.views.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.helper.Constants;
import com.cosmo.arquitecturamvpbase.helper.Utilities;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.model.Location;
import com.cosmo.arquitecturamvpbase.model.Phone;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String TAG = "MapsActivity";
    private Customer customer;
    private GoogleApiClient mGoogleApiClient;
    private android.location.Location mLastLocation;
    LatLng myLocation = null;
    ArrayList<LatLng> pointsRoutes;
    int colorTrace = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (checkPlayServices()) {
            obtainLocation();
            customer = (Customer) getIntent().getSerializableExtra(Constants.ITEM_CUSTOMER);

        } else {
            //TODO
        }
    }


    private void obtainLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(getConnectionCallBack())
                .addOnConnectionFailedListener(getConnectionFailedListener())
                .addApi(LocationServices.API)
                .build();
    }

    private GoogleApiClient.ConnectionCallbacks getConnectionCallBack() {
        GoogleApiClient.ConnectionCallbacks callBack = new GoogleApiClient.ConnectionCallbacks() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }else {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        };
        return callBack;
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private boolean checkPlayServices(){

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if(result != ConnectionResult.SUCCESS){
            if(googleApiAvailability.isUserResolvableError(result)){
                googleApiAvailability.getErrorDialog(this,result,5000).show();
            }
            return Boolean.FALSE;
        }

        return Boolean.TRUE;

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(Utilities.isNight()){
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_map_night));
            colorTrace = R.color.colorPrimary;
        }else{
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_map_standard));
            colorTrace = R.color.colorPrimaryDark;
        }
        getLocation();

    }

    private void createMarkers(){
        ArrayList<LatLng> points = new ArrayList();
        for (Phone phone :customer.getPhoneList()){
            Location location = phone.getLocations();
            LatLng point = new LatLng(location.getCoordinates()[0], location.getCoordinates()[1]);
            mMap.addMarker(new MarkerOptions().position(point).title(location.getType()).icon(getBitMapFromVector(this,R.drawable.ic_location_on_black_24dp)));
            points.add(point);
        }

        if(mLastLocation != null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLocation).title("Mi ubicación").icon(getBitMapFromVector(this, R.drawable.ic_location_on_black_24dp)));
            points.add(myLocation);
        }else{
            Toast.makeText(MapsActivity.this, "La ubicación no esta activa", Toast.LENGTH_SHORT).show();
        }

        createTraceRoute(points);
    }

    private void createTraceRoute(ArrayList<LatLng> points){


        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(points)
                .key(getString(R.string.google_maps_key))
                .optimize(true)
                .alternativeRoutes(true)
                .withListener(getRouteListener(points))
                .build();
        routing.execute();

        centerRoutes(points);

    }

    private void centerRoutes(ArrayList<LatLng> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        pointsRoutes = new ArrayList<>(points);
        for(LatLng latLng: pointsRoutes){
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,50);
        mMap.animateCamera(cameraUpdate);
    }

    private RoutingListener getRouteListener(ArrayList<LatLng> points){

        RoutingListener routingListener = new RoutingListener() {

            @Override
            public void onRoutingFailure(RouteException e) {
                Log.e(TAG,e != null ?"Error: "+e.getMessage():"Error Routing Failure");
            }

            @Override
            public void onRoutingStart() {
                Log.e(TAG,"Routing Start");
            }

            @Override
            public void onRoutingSuccess(ArrayList<Route> routes, int shortestRoutsIndex) {

                ArrayList<Polyline> polylines = new ArrayList();

                for (Route route: routes){
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.color(ContextCompat.getColor(getApplicationContext(), colorTrace));
                    polylineOptions.width(10);
                    polylineOptions.addAll(route.getPoints());
                    Polyline polyline = mMap.addPolyline(polylineOptions);
                    polylines.add(polyline);

                    int distance = route.getDistanceValue();
                    int duration = route.getDurationValue();

                    Toast.makeText(MapsActivity.this, "Distance: "+distance + " Duration: "+duration, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onRoutingCancelled() {
                Log.e(TAG,"Routing Cancelled");
            }
        };

        return routingListener;
    }

    private void initMapUiSettings(){
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    public BitmapDescriptor getBitMapFromVector(Context context, int idVector) {
        Bitmap bitMap = null;

        Drawable drawableVector = ContextCompat.getDrawable(context,idVector);
        drawableVector.setBounds(0,0,drawableVector.getIntrinsicWidth(),drawableVector.getIntrinsicHeight());
        bitMap = Bitmap.createBitmap(drawableVector.getIntrinsicWidth(),drawableVector.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitMap);
        drawableVector.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitMap);
    }

    public GoogleApiClient.OnConnectionFailedListener getConnectionFailedListener() {
        GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        };
        return connectionFailedListener;
    }

    private void getLocation() {
        mMap.clear();
        createMarkers();
        initMapUiSettings();
    }

    public void getMyLocation(View view) {

        if(mLastLocation != null) {
            if (myLocation == null) {
                myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLocation, 18);
            mMap.animateCamera(cameraUpdate);
        }
    }

    public void centerLocation(View view) {
        centerRoutes(pointsRoutes);
    }
}
