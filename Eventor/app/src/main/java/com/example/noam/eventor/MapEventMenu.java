package com.example.noam.eventor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.runner.Describable;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Itay on 18/1/2018
 */
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class MapEventMenu extends Fragment implements OnMapReadyCallback {
    private static final String DESCRIBABLE_KEY = "describable_key";

    private static final String TAG = "MapEventMenu";
    private static final int REQUEST_CODE = 1;
    MapView mapView;
    GoogleMap map;
    FloatingActionButton myLocation;
    FloatingActionButton myPinLocation;
    LocationManager mLocationManager;
    AddItemAdapter adapter;
    GenericEvent currentevent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_event_menu, container, false);
        adapter = AddItemAdapter.getInstance();
        currentevent = (GenericEvent) adapter.getModel().get(adapter.getCurrentIndex());

        myPinLocation = (FloatingActionButton) v.findViewById(R.id.pin_location_button);
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //mapView.loca = YES;
        mapView.getMapAsync(this);
        myPinLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm2 = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                double longitude = currentevent.getLongitude();
                double latitude = currentevent.getLatitude();
                final CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);
                map.animateCamera(cameraUpdate2);
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;





        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            SetMap();
        } else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }




    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private Location getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
            return bestLocation;
        } else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return null;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SetMap();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to use your location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void SetMap(){
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Location myLocation = getLastKnownLocation();
        //LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //ocation location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = currentevent.getLongitude();
        double latitude = currentevent.getLatitude();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);



        String placeId = currentevent.getPlaceID();
        GeoDataClient mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            public static final String TAG = "AddItemAdapter";

            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place place2 = places.get(0);
                    map.addMarker(new MarkerOptions()
                            .position(place2.getLatLng())
                            .title(place2.getName()+", "+place2.getAddress()));
                    map.animateCamera(cameraUpdate);
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }

        });
    }
}