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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;

public class EventPage extends AppCompatActivity {
    TextView category;
    TextView date;
    TextView time;
    TextView address;
    TextView currentUsers;
    TextView maxUsers;
    TextView eventId;
    TextView eventCreator;
    TextView description;
    ImageView image;
    Button joinEvent;
    ListView participants;
    CoordinatorLayout coor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b != null)
            value = b.getInt("key");
        MapEventMenu f = new MapEventMenu();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", value);
        f.setArguments(args);

        setContentView(R.layout.activity_event_page);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_button);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_event_page);
        GenericEvent event = (GenericEvent)AddItemAdapter.getInstance().getModel().get(value);

        category = (TextView) findViewById(R.id.event_category);
        date = (TextView) findViewById(R.id.event_date);
        time = (TextView) findViewById(R.id.event_time);
        address = (TextView) findViewById(R.id.place_address);
        currentUsers = (TextView) findViewById(R.id.current_joined_number);
        maxUsers = (TextView) findViewById(R.id.max_joined_number);
        eventId = (TextView) findViewById(R.id.event_id);
        eventCreator = (TextView) findViewById(R.id.event_creator_name);
        description = (TextView) findViewById(R.id.event_description);
        image = (ImageView) findViewById(R.id.event_pic);
        coor = (CoordinatorLayout) findViewById(R.id.main_event_page);
        Date datecheck = new Date();
        category.setText(event.getCategory().toString());
        description.setText(event.getDescription().toString());
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


        String imageicon = event.getCategory();
        switch (imageicon) {
            case "Football":
                image.setBackground(this.getResources().getDrawable(R.drawable.football_icon));
                coor.setBackground(this.getResources().getDrawable(R.drawable.soccer_wallpaper));
                break;
            case "Baketball":
                image.setBackground(this.getResources().getDrawable(R.drawable.basketball_iconn));
                coor.setBackground(this.getResources().getDrawable(R.drawable.basketball_wallpaper2));
                break;
            case "Volleyball":
                image.setBackground(this.getResources().getDrawable(R.drawable.volleyball_icon));
                coor.setBackground(this.getResources().getDrawable(R.drawable.volleyball_wallpaper));
                break;
            case "Tennis":
                image.setBackground(this.getResources().getDrawable(R.drawable.tennis_icon));
                coor.setBackground(this.getResources().getDrawable(R.drawable.tennis_wallpaper));
                break;
            case "Baseball":
                image.setBackground(this.getResources().getDrawable(R.drawable.baseball_icon));
                coor.setBackground(this.getResources().getDrawable(R.drawable.baseball_wallpaper));
                break;
            case "Cricket":
                image.setBackground(this.getResources().getDrawable(R.drawable.cricket_icon));
                coor.setBackground(this.getResources().getDrawable(R.drawable.cricket_wallpaper));
                break;
            case "No image":
                image.setBackground(this.getResources().getDrawable(R.drawable.add_image));
                break;
            default:
                //image.setImageBitmap(StringToBitMap(event.getEventImage()));
                break;
        }

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

        if (event.getMaxUsers() == Integer.MAX_VALUE) {
            maxUsers.setText("Unlimited");
        }
        else {
            maxUsers.setText(Integer.toString(event.getMaxUsers()));
        }

        eventId.setText("Event ID: "+Integer.toString(event.getId()));
        //eventCreator.setText(event.getAdminUserId());
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    fab.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_downward_white_24dp ));

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
                    fab.setImageResource(R.drawable.ic_arrow_downward_white_24dp );
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    fab.setImageResource(R.drawable.ic_arrow_upward_white_24dp );
                }
            }
        });
    behavior.setHideable(false);
        behavior.setPeekHeight(300);
    }

}
