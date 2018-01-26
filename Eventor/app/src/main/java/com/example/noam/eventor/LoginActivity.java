package com.example.noam.eventor;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private VideoView mVideoView;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
        SharedPreferences sharedPref = getSharedPreferences("preferences",
                Context.MODE_PRIVATE);

        // Get value
        boolean remember = sharedPref.getBoolean("rememberMe",false);
        if(remember){
            username = sharedPref.getString("username", null);
            password = sharedPref.getString("password", null);
            login();
        }

        mVideoView = (VideoView) findViewById(R.id.bgVideoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.kaki);

        mVideoView.setVideoURI(uri);

        mVideoView.start();



        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f, 0f);
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
        TextView registeractivity = (TextView) findViewById(R.id.register);
        registeractivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mVideoView.start();
    }
    public void login(){

        NetworkManager instance = NetworkManager.getInstance();
        JSONObject json2 = new JSONObject();
        try {
            json2.putOpt("userName", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json2.put("password",password);
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
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        switch(result){
                            case "1":
                                Toast.makeText(getApplicationContext(), "This username doesn't exists", Toast.LENGTH_SHORT).show();
                                break;
                            case "2":
                                Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Gson gson = new Gson();
                                User usertest = new User(2,"noam","12345",27,"noam@gmail.com","0524217111","Male");
                                String temp = gson.toJson(usertest,User.class);
                                usertest = gson.fromJson(temp, User.class);
                                User user = gson.fromJson(result, User.class);
                                CurrentUser userInstance = CurrentUser.getInstance();
                                userInstance.setUser(user);
                                Intent myIntent = new Intent(LoginActivity.this, UserAreaMain.class);
                                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                LoginActivity.this.startActivity(myIntent);
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
}