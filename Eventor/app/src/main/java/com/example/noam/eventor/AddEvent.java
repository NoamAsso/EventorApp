package com.example.noam.eventor;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class AddEvent extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);


        EditText numParti = (EditText) findViewById(R.id.numOfParticipants);
        EditText price = (EditText) findViewById(R.id.priceText);
        numParti.setEnabled(false);
        price.setEnabled(false);

        CheckBox isLimit =  (CheckBox) findViewById(R.id.isLimit);
        CheckBox isFree =  (CheckBox) findViewById(R.id.isFree);

        isLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()){
                    findViewById(R.id.numOfParticipants).setEnabled(false);
                    findViewById(R.id.numOfParticipants).setBackgroundResource(R.drawable.rounded_edittext_gray );
                }

                else{
                    findViewById(R.id.numOfParticipants).setEnabled(true);
                    findViewById(R.id.numOfParticipants).setBackgroundResource(R.drawable.rounded_edittext );
                }

            }
        });
        isFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()){
                    findViewById(R.id.priceText).setEnabled(false);
                findViewById(R.id.priceText).setBackgroundResource(R.drawable.rounded_edittext_gray );
                }
                else{
                    findViewById(R.id.priceText).setEnabled(true);
                    findViewById(R.id.priceText).setBackgroundResource(R.drawable.rounded_edittext );
                }

            }
        });


        }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), UserAreaMain.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

}
