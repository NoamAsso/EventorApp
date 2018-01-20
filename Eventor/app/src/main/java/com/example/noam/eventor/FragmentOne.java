package com.example.noam.eventor;

        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.widget.Button;
        import android.widget.ListView;

        import java.util.ArrayList;

/**
 * Created by Itay on 18/1/2018
 */

public class FragmentOne extends Fragment {


    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        perform(v);

        return v;


    }
    public void perform(View v) {
        Context context;
        ListView list = (ListView) v.findViewById(R.id.eventlist1);
        AddItemAdapter adapter;
        adapter = AddItemAdapter.getInstance();
        context = getActivity();
        list.setAdapter(adapter);
        GenericEvent event;
        //connect the adapter to the ListView
    }

}