package com.example.noam.eventor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Itay on 18/1/2018
 */

public class FragmentTwo extends Fragment {

    ListView list;
    String result;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);


        perform(view);

        return view;
    }
    public void perform(View v) {

        list = (ListView) v.findViewById(R.id.eventlist2);
        //GetListFromServer();
        GetListFromServer();
        final SwipeRefreshLayout mSwipeRefreshView;
        mSwipeRefreshView = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh2);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetListFromServer();
                mSwipeRefreshView.setRefreshing(false);
            }
        });
        //connect the adapter to the ListView
    }

    public void GetListFromServer() {
        AddItemAdapterTwo adapter;
        adapter = AddItemAdapterTwo.getInstance();
        adapter.setContext(getActivity());
        AddItemAdapter adapterOne = AddItemAdapter.getInstance();
        ArrayList<GenericEvent> eventList = AddItemAdapter.getInstance().getModel();
        ArrayList<GenericEvent> myEvents = new ArrayList<>();
        ArrayList<Integer> userEvents = CurrentUserEvents.getInstance().getUserEvents();
        //adapter.AddObj(eventTest);

            for (int i = 0; i < eventList.size(); i++) {
                for (int j = 0; j < userEvents.size(); j++) {
                    if (eventList.get(i).getId() == userEvents.get(j))
                        myEvents.add(eventList.get(i));
                }
            }
            adapter.setModel(myEvents);
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
    }//fetchFromNetwork
}