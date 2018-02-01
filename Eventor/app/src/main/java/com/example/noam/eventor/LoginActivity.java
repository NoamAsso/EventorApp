package com.example.noam.eventor;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class LoginActivity extends AppCompatActivity {

    private VideoView mVideoView;
    String username;
    String password;
    AddItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        TextView title = (TextView) findViewById(R.id.app_title);
        TextView slogan = (TextView) findViewById(R.id.app_slogan);
        Typeface myFont = Typeface.createFromAsset(getAssets(),"fonts/avefedan.ttf");
        Typeface myFont2 = Typeface.createFromAsset(getAssets(),"fonts/Quikhand.ttf");
        adapter = AddItemAdapter.getInstance();
        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        title.setTypeface(myFont);
        slogan.setTypeface(myFont2);
        if(callingActivity == 700){
            Toast.makeText(getApplicationContext(), "Logout successfully", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPref = getSharedPreferences("preferences",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sharedPref.edit();
            e.putBoolean("rememberMe", false);
            e.commit();
        }
        else{
            SharedPreferences sharedPref = getSharedPreferences("preferences",
                    Context.MODE_PRIVATE);
            boolean remember = sharedPref.getBoolean("rememberMe", false);
            if (remember) {
                username = sharedPref.getString("username", null);
                password = sharedPref.getString("password", null);
                login();
            }
        }
        // Get value

        mVideoView = (VideoView) findViewById(R.id.bgVideoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kaki);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //mediaPlayer.setVolume(0f, 0f);
                mediaPlayer.setLooping(true);
            }
        });
        Button loginactivity = (Button) findViewById(R.id.loginactivity);
        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, LoginMenu.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
        Button registeractivity = (Button) findViewById(R.id.register);
        registeractivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
        Button facebook = (Button) findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not available yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mVideoView.start();
    }

    public void login() {

        NetworkManager instance = NetworkManager.getInstance();
        JSONObject json2 = new JSONObject();
        try {
            json2.putOpt("userName", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json2.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String send = json2.toString();
        Log.e("What i send:::", send);
        int i = 0;
        instance.addRequest(new GetUserLoginRequest(send, new ServerCallback() {
            @Override
            public void onSuccess(Object res, int statusCode) {
                final String result = (String) res;
                Log.e("Login Menu", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch(result){
                            case "1":
                                Toast.makeText(getApplicationContext(), "This username doesn't exists", Toast.LENGTH_SHORT).show();
                                break;
                            case "2":
                                Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Gson gson = new Gson();
                                User usertest = new User(2, "noam", "12345", 27, "noam@gmail.com", "0524217111", "Male");
                                String temp = gson.toJson(usertest, User.class);
                                usertest = gson.fromJson(temp, User.class);
                                User user = gson.fromJson(result, User.class);
                                CurrentUser userInstance = CurrentUser.getInstance();
                                userInstance.setUser(user);
                                final NetworkManager instance = NetworkManager.getInstance();
                                instance.addRequest(new GetEventsOfUserRequest(CurrentUser.getInstance().getUser().getUserId(), new ServerCallback() {
                                    @Override
                                    public void onSuccess(Object res, int statusCode) {
                                        final String result2 = (String) res;
                                        Log.e("Fragment One second", result2);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Gson gson = new Gson();
                                                ArrayList<Integer> userEventsIDsArray;
                                                userEventsIDsArray = gson.fromJson(result2, new TypeToken<List<Integer>>() {
                                                }.getType());
                                                CurrentUserEvents.getInstance().setUserEvents(userEventsIDsArray);
                                                GetListFromServer();

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Object err, int statusCode) {
                                        Toast.makeText(getApplicationContext(), "failure to receive message", Toast.LENGTH_SHORT).show();
                                        Log.e("main menu", "Connection to Server failed for userEvents");
                                    }
                                }));

                                finish();
                                break;
                        }

                    }
                });
            }

            @Override
            public void onFailure(Object err, int statusCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Login Menu", "Connection to Server failed");
                        Toast.makeText(getApplicationContext(), "Connection to Server failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }));
    }
    public void GetListFromServer() {
        final NetworkManager instance = NetworkManager.getInstance();
        instance.addRequest(new GetEventsRequest(new ServerCallback() {

            @Override
            public void onSuccess(Object res, int statusCode) {
                final String result = (String) res;
                Log.e("Login menu", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != "[]") {
                            Gson gson = new Gson();
                            ArrayList<GenericEvent> eventsArray;

                            eventsArray = gson.fromJson(result, new TypeToken<List<GenericEvent>>() {
                            }.getType());
                            adapter.setModel(null);


                            if (adapter.setModel(eventsArray)) {

                                adapter.notifyDataSetChanged();

                            }
                            Intent myIntent = new Intent(LoginActivity.this, UserAreaMain.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LoginActivity.this.startActivity(myIntent);

                        } else
                            Log.e("Fragment One", "Can't initialize model");
                    }
                });
            }

            @Override
            public void onFailure(Object err, int statusCode) {
                Toast.makeText(getApplicationContext(), "failure to receive message", Toast.LENGTH_SHORT).show();
                Log.e("Fragment2", "Connection to Server failed");
            }
        }));
    }//fetchFromNetwork
}