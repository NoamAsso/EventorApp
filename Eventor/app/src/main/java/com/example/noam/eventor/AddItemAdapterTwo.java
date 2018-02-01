package com.example.noam.eventor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Noam on 24/01/2018.
 */

public class AddItemAdapterTwo extends BaseAdapter {
    private Context context; //context
    private static int currentIndex;
    private ArrayList<GenericEvent> model;
    private static AddItemAdapterTwo sInstance;
    private LayoutInflater layoutInflater;
    private Button join;
    private User currentUser;

    private AddItemAdapterTwo() {
        model = new ArrayList<>();
        this.context = null;
        layoutInflater = null;

    }

    public static AddItemAdapterTwo getInstance() {
        if (sInstance == null) {
            sInstance = new AddItemAdapterTwo();
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

    public boolean setModel(ArrayList<GenericEvent> list){
        if(list != null){
            this.model = list;
            return true;
        }
        else
            return false;

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
        TextView currentUsers = (TextView) convertView.findViewById(R.id.current_num_users);
        TextView date = (TextView) convertView.findViewById(R.id.event_date_item);
        TextView price = convertView.findViewById(R.id.price);
        final TextView location = (TextView) convertView.findViewById(R.id.location_item);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_item);
        join = (Button) convertView.findViewById(R.id.event_join);
        CurrentUser Uinstance = CurrentUser.getInstance();
        currentUser = CurrentUser.getInstance().getUser();
        currentUsers.setText(Integer.toString(currentItem.getCurrentUsers()));

        if (CheckIfJoined(position)) {
            join.setEnabled(false);
            join.setText("Joined");
            join.setTextColor(Color.GRAY);
            join.setBackground(context.getResources().getDrawable(R.drawable.rounded_edittext));
        }
        else{
            if(join.getText()=="Joined"){
                join.setEnabled(true);
                join.setText("Join");
                join.setTextColor(Color.WHITE);
                join.setBackground(context.getResources().getDrawable(R.drawable.curve_edges_button_location));
            }
        }
        //sets the text for item name and item description from the current item object
        title.setText(currentItem.getCategory());
        year = currentItem.getDate().getYear();
        month = currentItem.getDate().getMonth();
        day = currentItem.getDate().getDate();
        if ((year == (datecheck.getYear() + 1900)) && (month == (datecheck.getMonth() + 1))) {
            switch (day - datecheck.getDate()) {
                case 0:
                    date.setText("Today");
                    break;
                case 1:
                    date.setText("Tommorow");
                    break;
                default:
                    date.setText(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
            }
        }
        if (currentItem.getDate().getMinutes() < 10 && currentItem.getDate().getHours() > 9) {
            timeHrsMin.setText("At: " + currentItem.getDate().getHours() + ":0" + currentItem.getDate().getMinutes());
        } else if (currentItem.getDate().getHours() < 10 && currentItem.getDate().getMinutes() > 9) {
            timeHrsMin.setText("At: 0" + currentItem.getDate().getHours() + ":" + currentItem.getDate().getMinutes());
        } else if ((currentItem.getDate().getHours() < 10 && currentItem.getDate().getMinutes() < 10))
            timeHrsMin.setText("At: " + currentItem.getDate().getHours() + ":0" + currentItem.getDate().getMinutes());
        else
            timeHrsMin.setText("At: " + currentItem.getDate().getHours() + ":" + currentItem.getDate().getMinutes());

        String imageicon = currentItem.getCategory();
        switch (imageicon) {
            case "Football":
                image.setBackground(context.getResources().getDrawable(R.drawable.football_icon));
                break;
            case "Basketball":
                image.setBackground(context.getResources().getDrawable(R.drawable.basketball_iconn));
                break;
            case "Volleyball":
                image.setBackground(context.getResources().getDrawable(R.drawable.volleyball_icon));
                break;
            case "Tennis":
                image.setBackground(context.getResources().getDrawable(R.drawable.tennis_icon));
                break;
            case "Baseball":
                image.setBackground(context.getResources().getDrawable(R.drawable.baseball_icon));
                break;
            case "Cricket":
                image.setBackground(context.getResources().getDrawable(R.drawable.cricket_icon));
                break;
            case "No image":
                image.setBackground(context.getResources().getDrawable(R.drawable.add_image));
                break;
            default:
                image.setImageBitmap(StringToBitMap(currentItem.getEventImage()));
                break;
        }

        if (currentItem.getMaxUsers() == Integer.MAX_VALUE) {
            maxNumOfUsers.setText("Unlimited");
        } else {
            maxNumOfUsers.setText(Integer.toString(currentItem.getMaxUsers()));
        }

        price.setText("Price: "+Integer.toString(currentItem.getPrice())+"₪");
        String placeId = currentItem.getPlaceID();
        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            public static final String TAG = "AddItemAdapter";

            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place place2 = places.get(0);
                    location.setText(place2.getName() + ", " + place2.getAddress());
                    Log.i(TAG, "Place found: " + place2.getName() + place2.getAddress());
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = position;
                Intent myIntent = new Intent(context, EventPage.class);
                Bundle b = new Bundle();
                b.putInt("key", position);
                b.putInt("from", 2);
                myIntent.putExtras(b);
                context.startActivity(myIntent);
                //finish();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckIfJoined(position)) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //JoinEvent((GenericEvent) getItem(position));
                                    join.setEnabled(false);
                                    join.setText("Joined");
                                    join.setTextColor(Color.GRAY);
                                    join.setBackground(context.getResources().getDrawable(R.drawable.rounded_edittext));

                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to join this game?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                } else {
                    Toast.makeText(context, "already joined.", Toast.LENGTH_SHORT).show();
                }

                //finish();
            }
        });
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
    public boolean CheckIfJoined(int position) {
        ArrayList<Integer> text = CurrentUserEvents.getInstance().getUserEvents();
        GenericEvent temp = (GenericEvent) getItem(position);
        int x = temp.getId();
        if (text.contains(temp.getId())) {
            return true;
        } else {
            return false;
        }


    }
}
