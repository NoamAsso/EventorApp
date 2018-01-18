package com.example.noam.eventor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LoginMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        Intent intent = getIntent();
        android.support.v7.app.ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        Button loginactivity = (Button) findViewById(R.id.loginButton);
        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginMenu.this, UserAreaMain.class);
                //myIntent.putExtra("key", value); //Optional parameters
                LoginMenu.this.startActivity(myIntent);
                //finish();
            }
        });


    }
}
