package com.example.noam.eventor;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    ImageButton datePick;
    TextView dateDisplay;
int day, month, year, hour, minute;
int fday,fmonth, fyear, fhour, fminute;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        datePick = (ImageButton) findViewById(R.id.date_choose);
        dateDisplay = (TextView) findViewById(R.id.date_text);

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent.this,AddEvent.this,year,month,day);
                datePickerDialog.show();
            }
        });

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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        fyear = i;
        fmonth = i1+1;
        fday = i2;
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this,AddEvent.this,hour,minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        fhour = i;
        fminute = i1;
        dateDisplay.setText(fday + "/" + fmonth + "/"+ fyear + "   "+ fhour + ":"+ fminute );
    }
}
