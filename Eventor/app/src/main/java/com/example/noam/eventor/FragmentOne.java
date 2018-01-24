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
String result;
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

        list = (ListView) v.findViewById(R.id.eventlist1);
        RefreshList();
        final SwipeRefreshLayout mSwipeRefreshView;
        mSwipeRefreshView = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            RefreshList();
                mSwipeRefreshView.setRefreshing(false);
            }
        });



        //connect the adapter to the ListView
    }




    public void RefreshList() {

        GetListFromServer();


        String kaki = result;
        //Toast.makeText(getActivity().getApplicationContext(), "lllllllllllaaaaaaa"+kaki, Toast.LENGTH_SHORT).show();

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
                                        AddItemAdapter adapter;
                                        adapter = AddItemAdapter.getInstance();
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