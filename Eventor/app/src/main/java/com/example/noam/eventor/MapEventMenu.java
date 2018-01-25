package com.example.noam.eventor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class MapEventMenu extends Fragment implements OnMapReadyCallback {
    private static final String DESCRIBABLE_KEY = "describable_key";

    private static final String TAG = "MapEventMenu";
    private static final int REQUEST_CODE = 1;
    MapView mapView;
    GoogleMap map;
    Button myLocation;
    LocationManager mLocationManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_event_menu, container, false);
        Button myLocation = (Button) v.findViewById(R.id.my_location_button);
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        myLocation = (Button) v.findViewById(R.id.my_location_button);
        //mapView.loca = YES;

        mapView.getMapAsync(this);
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm2 = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                Location location2 = getLastKnownLocation();
                double longitude = location2.getLongitude();
                double latitude = location2.getLatitude();
                final CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 30);
                map.animateCamera(cameraUpdate2);
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setMyLocationButtonEnabled(false);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else
            map.setMyLocationEnabled(true);





        Location myLocation = getLastKnownLocation();



        //LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //ocation location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);


        AddItemAdapter adapter = AddItemAdapter.getInstance();
        GenericEvent currentevent = (GenericEvent) adapter.getModel().get(adapter.getCurrentIndex());
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
    }
}