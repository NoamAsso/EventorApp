package com.example.noam.eventor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {


    EditText userName;
    EditText userPassword;
    EditText userEmail;
    EditText userPhone;
    EditText userAge;

    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();

        userName= (EditText) findViewById(R.id.user_name);
        userPassword= (EditText) findViewById(R.id.user_password);
        userEmail= (EditText) findViewById(R.id.user_email);
        userPhone= (EditText) findViewById(R.id.user_phone);
        userAge= (EditText) findViewById(R.id.user_age);
        registerButton = (Button) findViewById(R.id.register_button);

        final Spinner spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        String[] items = new String[] {"Male", "Female", "Gay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditTextNormalize();
                boolean error = false;
                if(userName.getText().toString().matches("")){
                    error = true;
                    userName.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(userPassword.getText().toString().matches("")){
                    error = true;
                    userPassword.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(userEmail.getText().toString().matches("")){
                    error = true;
                    userEmail.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(userPhone.getText().toString().matches("")){
                    error = true;
                    userPhone.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(userAge.getText().toString().matches("")){
                    error = true;
                    userAge.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(!error){
                    User user = new User(0,userName.getText().toString(),userPassword.getText().toString(),Integer.parseInt(userAge.getText().toString()),userEmail.getText().toString(),userPhone.getText().toString(),spinnerGender.getSelectedItem().toString());
                    Gson gson = new Gson();
                    Date date = new Date();
                    String json = gson.toJson(user);
                    User userTest = gson.fromJson(json,User.class);

                    NetworkManager instance = NetworkManager.getInstance();
                    instance.addRequest(new PostUserRequest(json, new ServerCallback() {
                        @Override
                        public void onSuccess(Object res, int statusCode) {
                            final String result = (String) res;
                            Log.e("AddEvent", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                        @Override
                        public void onFailure(Object err, int statusCode) {
                            Log.e("AddEvent", "Connection to Server failed");
                        }
                    }));


                    Intent myIntent = new Intent(RegisterActivity.this, LoginMenu.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    RegisterActivity.this.startActivity(myIntent);
                    //finish();
                }
                else {

                    Toast.makeText(getApplicationContext(),"Some fields are missing",Toast.LENGTH_SHORT).show();
                }
            }
            //create button
        });



    }

    public void EditTextNormalize(){
        userName.setBackgroundResource(R.drawable.rounded_edittext);
        userPassword.setBackgroundResource(R.drawable.rounded_edittext);
        userEmail.setBackgroundResource(R.drawable.rounded_edittext );
        userPhone.setBackgroundResource(R.drawable.rounded_edittext );
        userAge.setBackgroundResource(R.drawable.rounded_edittext );
    }
}
