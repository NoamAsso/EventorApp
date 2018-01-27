package com.example.noam.eventor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Itay on 18/1/2018
 */

public class FragmentOne extends Fragment {
    ListView list;
    AddItemAdapter adapter;
   // String result;

    public FragmentOne() {
        // Required empty public constructor
    }

    SwipeRefreshLayout mSwipeRefreshView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        list = (ListView) v.findViewById(R.id.eventlist1);


        mSwipeRefreshView = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetListFromServer();
                mSwipeRefreshView.setRefreshing(false);
            }
        });

        GetListFromServer();

        return v;
    }

    public void GetListFromServer() {
        final NetworkManager instance = NetworkManager.getInstance();
        instance.addRequest(new GetEventsRequest(new ServerCallback() {

            @Override
            public void onSuccess(Object res, int statusCode) {
                final String result = (String) res;
                Log.e("Fragment One", result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != "[]") {
                            Gson gson = new Gson();
                            ArrayList<GenericEvent> eventsArray;
                            eventsArray = gson.fromJson(result, new TypeToken<List<GenericEvent>>() {}.getType());

                            adapter = AddItemAdapter.getInstance();
                            if (adapter.setModel(eventsArray)) {
                                adapter.setContext(getActivity());
                            }

                            instance.addRequest(new GetEventsOfUserRequest(CurrentUser.getInstance().getUser().getUserId(),new ServerCallback() {
                                @Override
                                public void onSuccess(Object res, int statusCode) {
                                    final String result2 = (String) res;
                                    Log.e("Fragment One second", result2);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                        Gson gson = new Gson();
                                        ArrayList<Integer> userEventsIDsArray;

                                        if (!result2.equals("[]")) {
                                            userEventsIDsArray = gson.fromJson(result2, new TypeToken<List<Integer>>() {}.getType());
                                            CurrentUserEvents.getInstance().setUserEvents(userEventsIDsArray);
                                        }

                                        list.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Object err, int statusCode) {
                                    Toast.makeText(getActivity().getApplicationContext(), "failure to receive message", Toast.LENGTH_SHORT).show();
                                    Log.e("Fragment2", "Connection to Server failed");
                                }
                            }));
                        } else
                            Log.e("Fragment One","Can't initialize model");
                    }
                });
            }

            @Override
            public void onFailure(Object err, int statusCode) {
                Toast.makeText(getActivity().getApplicationContext(), "failure to receive message", Toast.LENGTH_SHORT).show();
                Log.e("Fragment2", "Connection to Server failed");
            }
        }));
    }//fetchFromNetwork
}