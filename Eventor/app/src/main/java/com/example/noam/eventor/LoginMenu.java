package com.example.noam.eventor;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginMenu extends AppCompatActivity {
    EditText username;
    EditText userPassword;
    Button loginactivity;
    CheckBox remember;
    String userName;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        final Intent intent = getIntent();
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        username = (EditText) findViewById(R.id.username_login);
        userPassword = (EditText) findViewById(R.id.password_login);
        loginactivity = (Button) findViewById(R.id.loginButton);
        remember = (CheckBox) findViewById(R.id.remember);

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
                                            Intent myIntent = new Intent(LoginMenu.this, UserAreaMain.class);
                                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            LoginMenu.this.startActivity(myIntent);
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
}
