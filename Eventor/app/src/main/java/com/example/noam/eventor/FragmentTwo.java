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

        NetworkManager instance = NetworkManager.getInstance();
        instance.addRequest(new GetEventsRequest(new ServerCallback() {

            @Override
            public void onSuccess(Object res, int statusCode) {
                final String tempresult = (String) res;
                result = tempresult;
                Log.e("Fragment2", result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getActivity().getApplicationContext(), "yay got the message!"+result, Toast.LENGTH_SHORT).show();
                        ArrayList<GenericEvent> currentList;
                        if(result != "[]"){
                            Gson gson = new Gson();
                            currentList = gson.fromJson(result, new TypeToken<List<GenericEvent>>(){}.getType());
                            AddItemAdapterTwo adapter;
                            adapter = AddItemAdapterTwo.getInstance();
                            adapter.setContext(getActivity());
                            //adapter.AddObj(eventTest);
                            if(adapter.setModel(currentList)) {
                                list.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        else
                            Toast.makeText(getActivity(),"cant initial model",Toast.LENGTH_SHORT).show();

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