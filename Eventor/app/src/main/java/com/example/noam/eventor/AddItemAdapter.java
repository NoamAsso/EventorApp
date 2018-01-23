package com.example.noam.eventor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Noam on 19/01/2018.
 */


public class AddItemAdapter extends BaseAdapter {
        private Context context; //context
        private static int currentIndex;
        private ArrayList<GenericEvent> model;
        private static AddItemAdapter sInstance;
        private LayoutInflater layoutInflater;

    private AddItemAdapter() {
        model = new ArrayList<>();
        this.context = null;
        layoutInflater = null;
    }

    public static AddItemAdapter getInstance() {
        if (sInstance == null) {
            sInstance = new AddItemAdapter();
        }
        return sInstance;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public void AddObj(GenericEvent obj){
    model.add(obj);
}
public void setContext (Context context){
    this.context = context;
    layoutInflater = LayoutInflater.from(context);
}
    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
    if(!model.isEmpty())
        return model.get(i);

    else
        return null;
    }
    public ArrayList getModel(){
    return  model;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int year, month, day;
        Date datecheck = new Date();
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.event_list_item, parent, false);
        }

        final GenericEvent currentItem = (GenericEvent) getItem(position);

        GeoDataClient mGeoDataClient = Places.getGeoDataClient(context, null);
        PlaceDetectionClient mPlaceDetectionClient = Places.getPlaceDetectionClient(context, null);
        Button detailButton = (Button) convertView.findViewById(R.id.details_button);
        TextView timeHrsMin = (TextView) convertView.findViewById(R.id.time_item);
        TextView title = (TextView) convertView.findViewById(R.id.event_title_item);
        TextView maxNumOfUsers = (TextView) convertView.findViewById(R.id.max_num_users);
        TextView date = (TextView) convertView.findViewById(R.id.event_date_item);
        final TextView location = (TextView) convertView.findViewById(R.id.location_item);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_item);
        //sets the text for item name and item description from the current item object
        title.setText(currentItem.getCategory());
        year = currentItem.getDate().getYear();
        month = currentItem.getDate().getMonth();
        day = currentItem.getDate().getDate();
        if((year == (datecheck.getYear()+1900)) && (month == (datecheck.getMonth()+1))){
            switch (day-datecheck.getDate()){
                case 0:
                    date.setText("Today");break;
                case 1:
                    date.setText("Tommorow");break;
                default:
                    date.setText(currentItem.getstringDate());
            }
        }
        else
            date.setText(Integer.toString(datecheck.getDate()));
        if(currentItem.getDate().getMinutes()<10 && currentItem.getDate().getHours()>9){
            timeHrsMin.setText("At: "+currentItem.getDate().getHours()+":0"+currentItem.getDate().getMinutes());
        }
        else if(currentItem.getDate().getHours()<10 && currentItem.getDate().getMinutes()>9){
            timeHrsMin.setText("At: 0"+currentItem.getDate().getHours()+":"+currentItem.getDate().getMinutes());
        }
        else if((currentItem.getDate().getHours()<10 && currentItem.getDate().getMinutes()<10))
        timeHrsMin.setText("At: "+currentItem.getDate().getHours()+":0"+currentItem.getDate().getMinutes());
        else
            timeHrsMin.setText("At: "+currentItem.getDate().getHours()+":"+currentItem.getDate().getMinutes());
        //date.setText(currentItem.getstringDate());
        image.setImageBitmap(StringToBitMap(currentItem.getEventImage()));
        maxNumOfUsers.setText(Integer.toString(currentItem.getMaxUsers()));
        String placeId = currentItem.getstringDate();
        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            public static final String TAG = "AddItemAdapter";
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place place2 = places.get(0);
                    location.setText(place2.getName()+", "+place2.getAddress());
                    Log.i(TAG, "Place found: " + place2.getName() + place2.getAddress());
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });
        //String address = String.format("%s, %s",currentItem.getplaceID().getLocale(),currentItem.getplaceID().getAddress());
        //location.setText(address);



        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = position;
                Intent myIntent = new Intent(context, EventPage.class);
                //myIntent.putExtra("key", value); //Optional parameters
                context.startActivity(myIntent);
                //finish();
            }
        });





        // returns the view for the current row
        return convertView;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
