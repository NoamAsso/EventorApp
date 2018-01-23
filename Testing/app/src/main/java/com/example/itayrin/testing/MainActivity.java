package com.example.itayrin.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}



    //Building test Json for testing
    String test1 = "first try";
    int test2 = 17;

    JSONObject json2 = new JSONObject();
                    try {
                            json2.putOpt("test1", test1);
                            } catch (JSONException e) {
                            e.printStackTrace();
                            }
                            try {
                            json2.put("test2",test2);
                            } catch (JSONException e) {
                            e.printStackTrace();
                            }

                            String toSend = json2.toString();

                            NetworkManager instance = NetworkManager.getInstance();
                            //John purcell's way
                            //instance.sendDataToServer(toSend);
                            // The old way
                            instance.addRequest(new PostEventRequest(toSend, new ServerCallback() {

@Override
public void onSuccess(Object res, int statusCode) {
final String result = (String) res;
        Log.e("AddEvent", result);
        runOnUiThread(new Runnable() {
@Override
public void run() {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        });
        }

@Override
public void onFailure(Object err, int statusCode) {
        Log.e("AddEvent", "Connection to Server failed");
        }
        }));