package com.example.noam.eventor;

import android.content.Intent;
import android.mtp.MtpConstants;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

public class UserAreaMain extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar;
        MyAdapter adapter;
        CharSequence Titles[]={"Recent Events","My Events","Invitations"};
        int Numboftabs =3;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area_main);
        Intent intent = getIntent();
        AddItemAdapter adapter1;
        adapter1 = AddItemAdapter.getInstance();
        adapter1.setContext(this);
       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
      mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
       mDrawerLayout.addDrawerListener(mToggle);
       mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
       // setSupportActionBar(toolbar);

        adapter =  new MyAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),Titles,Numboftabs));
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });



        FloatingActionButton addEvent = (FloatingActionButton) findViewById(R.id.add);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserAreaMain.this, AddEvent.class);
                //myIntent.putExtra("key", value); //Optional parameters
                UserAreaMain.this.startActivity(myIntent);
                //finish();
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
