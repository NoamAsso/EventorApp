package com.example.noam.eventor;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class LoginMenu extends AppCompatActivity {
    EditText username;
    EditText userPassword;
    Button loginactivity;
    CheckBox remember;
    String userName;
    String password;
    AddItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        final Intent intent = getIntent();
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        TextView title = (TextView) findViewById(R.id.app_title2);
        TextView slogan = (TextView) findViewById(R.id.app_slogan2);
        TextView logint = (TextView) findViewById(R.id.logintext);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/avefedan.ttf");
        Typeface myFont2 = Typeface.createFromAsset(getAssets(), "fonts/Quikhand.ttf");
        Typeface myFont3 = Typeface.createFromAsset(getAssets(), "fonts/myriad_light.otf");
        title.setTypeface(myFont);
        slogan.setTypeface(myFont2);
        logint.setTypeface(myFont3);
        username = (EditText) findViewById(R.id.username_login);
        userPassword = (EditText) findViewById(R.id.password_login);
        loginactivity = (Button) findViewById(R.id.loginButton);
        remember = (CheckBox) findViewById(R.id.remember);
        adapter = AddItemAdapter.getInstance();
        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = username.getText().toString();
                password = userPassword.getText().toString();
                if (userName == "" || password == "")
                    Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
                else {


                    NetworkManager instance = NetworkManager.getInstance();
                    JSONObject json2 = new JSONObject();
                    try {
                        json2.putOpt("userName", userName);
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
                                    switch (result) {
                                        case "1":
                                            Toast.makeText(getApplicationContext(), "This username doesn't exists", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "2":
                                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            if (remember.isChecked()) {
                                                SharedPreferences sharedPref = getSharedPreferences("preferences",
                                                        Context.MODE_PRIVATE);
                                                SharedPreferences.Editor e = sharedPref.edit();
                                                e.putBoolean("rememberMe", true);
                                                e.putString("username", userName);
                                                e.putString("password", password);
                                                e.commit();
                                                //am.addAccount()
                                            }
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
            }
        });


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
                            Intent myIntent = new Intent(LoginMenu.this, UserAreaMain.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LoginMenu.this.startActivity(myIntent);

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
