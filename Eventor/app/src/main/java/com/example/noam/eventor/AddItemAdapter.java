package com.example.noam.eventor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
/**
 * Created by Noam on 19/01/2018.
 */


public class AddItemAdapter extends BaseAdapter {
        private Context context; //context
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.event_list_item, parent, false);
        }

        // get current item to be displayed
        GenericEvent currentItem = (GenericEvent) getItem(position);

        // get the TextView for item name and item description
        TextView title = (TextView)
                convertView.findViewById(R.id.event_title_item);
        TextView maxNumOfUsers = (TextView)
                convertView.findViewById(R.id.max_num_users);

        //sets the text for item name and item description from the current item object
        title.setText(currentItem.getTitle());
        maxNumOfUsers.setText(Integer.toString(currentItem.getMaxUsers()));

        // returns the view for the current row
        return convertView;
    }
}
