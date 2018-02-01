package com.example.noam.eventor;

import android.app.Activity;
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
 * Created by Noam on 01/02/2018.
 */
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class UserListAdapter extends BaseAdapter {//adapter for list of users that joined a specific
    private Context context; //context
    private static int currentIndex;
    private ArrayList<User> model;
    private LayoutInflater layoutInflater;
    private static UserListAdapter sInstance;
    private static int positionL;
    private User currentUser;

    private UserListAdapter() {

        model = new ArrayList<>();
        this.context = null;
        layoutInflater = null;
    }

    public static UserListAdapter getInstance() {
        if (sInstance == null) {
            sInstance = new UserListAdapter();
        }
        return sInstance;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public void AddObj(User obj) {
        model.add(obj);
    }

    public void setContext(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        if (!model.isEmpty())
            return model.get(i);

        else
            return null;
    }

    public ArrayList getModel() {
        return model;
    }

    public boolean setModel(ArrayList<User> list) {
        if (list != null) {
            this.model = list;
            return true;
        } else
            return false;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e("GetView", "position " + position);
        int year, month, day;
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.user_list_item, parent, false);
        }


        TextView userName = (TextView) convertView.findViewById(R.id.username_list);
        TextView age = (TextView) convertView.findViewById(R.id.age_list);
        TextView gender = (TextView) convertView.findViewById(R.id.gender_list);
        TextView rating = (TextView) convertView.findViewById(R.id.rating_list);
        currentUser = model.get(position);
        userName.setText(currentUser.getUserName());
        age.setText(Integer.toString(currentUser.getAge()));
        gender.setText(currentUser.getSex());

        return convertView;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
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
