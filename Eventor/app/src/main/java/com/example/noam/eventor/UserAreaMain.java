package com.example.noam.eventor;

import android.content.ClipData;
import android.content.Intent;
import android.mtp.MtpConstants;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class UserAreaMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//main screen after login activity!

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar;
        MyAdapter adapter;
        CharSequence Titles[] = {"Recent Events", "My Events", "Invitations"};
        int Numboftabs = 3;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area_main);
        Intent intent = getIntent();

        AddItemAdapter adapter1;
        adapter1 = AddItemAdapter.getInstance();
        adapter1.setContext(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new MyAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), Titles, Numboftabs));
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
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout2) {

            CurrentUserEvents x = CurrentUserEvents.getInstance();
            Intent myIntent = new Intent(UserAreaMain.this, LoginActivity.class);
           /* Bundle b = new Bundle();
            b.putInt("key2", 1);
            myIntent.putExtras(b);*/
            myIntent.putExtra("calling-activity", 700);
            UserAreaMain.this.startActivity(myIntent);
            // Handle the camera action
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
