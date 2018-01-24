package com.example.noam.eventor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Itay on 18/1/2018
 */

public class FragmentTwo extends Fragment {

    Button fetchButton;
    TextView testText;

    public static final String TAG = "Fragment2";

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        testText = view.findViewById(R.id.textFromServer);


        fetchFromNetwork(view);

        return view;
    }

    public void fetchFromNetwork(View view) {
        fetchButton = view.findViewById(R.id.fetchButton); //Removed the casting to button (redundant?)

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NetworkManager instance = NetworkManager.getInstance();
               instance.addRequest(new GetEventsRequest(new ServerCallback() {

                   @Override
                   public void onSuccess(Object res, int statusCode) {
                       final String result = (String) res;
                       Log.e("Fragment2", result);
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(getActivity().getApplicationContext(), "yay got the message!", Toast.LENGTH_SHORT).show();
                               testText.setText(result + "fetch from network");
                           }
                       });
                   }

                   @Override
                   public void onFailure(Object err, int statusCode) {
                       Log.e("Fragment2", "Connection to Server failed");
                   }
               }));
            }//OnClick
        });//setOnClickListener
    }//fetchFromNetwork
}