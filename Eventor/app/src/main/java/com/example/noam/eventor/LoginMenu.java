package com.example.noam.eventor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        Intent intent = getIntent();
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        username = (EditText) findViewById(R.id.username_login);
        userPassword = (EditText) findViewById(R.id.password_login);
        Button loginactivity = (Button) findViewById(R.id.loginButton);
        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String password = userPassword.getText().toString();
                NetworkManager instance = NetworkManager.getInstance();
                JSONObject json2 = new JSONObject();
                try {
                    json2.putOpt("userName", userName);
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
                                        Intent myIntent = new Intent(LoginMenu.this, UserAreaMain.class);
                                        LoginMenu.this.startActivity(myIntent);
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

                //finish();
            }
        });


    }
}
