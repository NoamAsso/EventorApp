package com.example.noam.eventor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class EventPage extends AppCompatActivity {
    TextView categoty;
    TextView date;
    TextView time;
    TextView address;
    TextView currentUsers;
    TextView maxUsers;
    TextView eventId;
    TextView eventCreator;
    Button joinEvent;
    ListView participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        Intent intent = getIntent();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_button);
        fab.setVisibility(View.GONE);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_event_page);
// The View with the BottomSheetBehavior
        CurrentEvent eventInstance = CurrentEvent.getInstance();
        GenericEvent event = eventInstance.getCurrentEvent();

        categoty = (TextView) findViewById(R.id.event_category);
        date = (TextView) findViewById(R.id.event_date);
        time = (TextView) findViewById(R.id.event_time);
        address = (TextView) findViewById(R.id.place_address);
        currentUsers = (TextView) findViewById(R.id.current_joined_number);
        maxUsers = (TextView) findViewById(R.id.max_joined_number);
        eventId = (TextView) findViewById(R.id.event_id);
        eventCreator = (TextView) findViewById(R.id.event_creator_name);
        Date datecheck = new Date();
        categoty.setText(event.getCategory().toString());
        int year = event.getDate().getYear();
        int month = event.getDate().getMonth();
        int day = event.getDate().getDate();
        if((year == (datecheck.getYear()+1900)) && (month == (datecheck.getMonth()+1))){
            switch (day-datecheck.getDate()){
                case 0:
                    date.setText("Today");break;
                case 1:
                    date.setText("Tommorow");break;
                default:
                    date.setText(Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year));
            }
        }
        if(event.getDate().getMinutes()<10 && event.getDate().getHours()>9){
            time.setText("At: "+event.getDate().getHours()+":0"+event.getDate().getMinutes());
        }
        else if(event.getDate().getHours()<10 && event.getDate().getMinutes()>9){
            time.setText("At: 0"+event.getDate().getHours()+":"+event.getDate().getMinutes());
        }
        else if((event.getDate().getHours()<10 && event.getDate().getMinutes()<10))
            time.setText("At: "+event.getDate().getHours()+":0"+event.getDate().getMinutes());
        else
            time.setText("At: "+event.getDate().getHours()+":"+event.getDate().getMinutes());

        GeoDataClient mGeoDataClient = Places.getGeoDataClient(this, null);
        String placeId = event.getPlaceID();
        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            public static final String TAG = "AddItemAdapter";
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place place2 = places.get(0);
                    address.setText(place2.getName()+", "+place2.getAddress());
                    Log.i(TAG, "Place found: " + place2.getName() + place2.getAddress());
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });
        currentUsers.setText(Integer.toString(event.getCurrentUsers()));
        maxUsers.setText(Integer.toString(event.getMaxUsers()));
        eventId.setText(Integer.toString(event.getId()));
        eventCreator.setText(event.getAdminUserId());
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    fab.setVisibility(View.VISIBLE);

                }
                if(behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
        });
    behavior.setHideable(false);
        behavior.setPeekHeight(300);
    }

}
