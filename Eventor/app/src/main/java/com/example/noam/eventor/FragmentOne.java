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

    Button fetchButton;

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
        fetchButton = (Button) v.findViewById(R.id.fetchButton);
        adapter = AddItemAdapter.getInstance();
        context = getActivity();
        if(context==null)
            fetchButton.setText("papaappapapaxt");

        list.setAdapter(adapter);
        GenericEvent event;
        if(!adapter.getModel().isEmpty()){
            event = (GenericEvent) adapter.getItem(0);
            fetchButton.setText(event.getTitle());
        }





        //connect the adapter to the ListView

    }
    public void fetchFromNetwork(View view) {
        fetchButton = (Button) view.findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*myfragment = new FragmentOne();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, myfragment);
        fragmentTransaction.commit();*/
    }
}