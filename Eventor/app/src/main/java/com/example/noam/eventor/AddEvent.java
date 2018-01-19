package com.example.noam.eventor;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

public static final int Selected_Image = 1;
    EditText eventTitle;
    EditText eventDescription;
    ImageButton datePick;
    TextView dateDisplay;
    ImageView kaka;
    ImageButton eventImage;
    EditText numParticipant;
    EditText price;
    CheckBox isLimit;
    CheckBox isFree;
    CheckBox isPrivate;
    Button createButton;
    int day, month, year, hour, minute;
    int fday,fmonth, fyear, fhour, fminute;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTitle = (EditText) findViewById(R.id.event_title);
        eventDescription = (EditText) findViewById(R.id.description_text);
        eventImage = (ImageButton) findViewById(R.id.event_image);
        datePick = (ImageButton) findViewById(R.id.date_choose);
        dateDisplay = (TextView) findViewById(R.id.date_text);
        numParticipant = (EditText) findViewById(R.id.numOfParticipants);
        isLimit =  (CheckBox) findViewById(R.id.isLimit);
        price = (EditText) findViewById(R.id.priceText);
        isFree =  (CheckBox) findViewById(R.id.isFree);
        isPrivate =  (CheckBox) findViewById(R.id.isPrivate);
        kaka = (ImageView) findViewById(R.id.kak);
        createButton = (Button) findViewById(R.id.create_button);




        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditTextNormalize();
                boolean error = false;
                if(eventTitle.getText().toString().matches("")){
                    error = true;
                    eventTitle.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(eventDescription.getText().toString().matches("")){
                    error = true;
                    eventDescription.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(numParticipant.getText().toString().matches("")&&!isLimit.isChecked()){
                    error = true;
                    numParticipant.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(price.getText().toString().matches("")&&!isFree.isChecked()){
                    error = true;
                    price.setBackgroundResource(R.drawable.rounded_edittext_red );
                }
                if(dateDisplay.getText().toString().matches("")){
                    error = true;
                    dateDisplay.setBackgroundResource(R.drawable.rounded_edittext_red );
                }



                if(!error){
                    /*
                    GenericEvent event = new GenericEvent();
                    event.setTitle(eventTitle.getText().toString());
                    event.setDescription(eventDescription.getText().toString());
                    event.setDateTest(dateDisplay.getText().toString());
                    if(isLimit.isChecked())
                        event.setMaxUsers(Integer.parseInt(numParticipant.getText().toString()));
                    if(!isFree.isChecked())
                        event.setPrice(Integer.parseInt(price.getText().toString()));
                    */
                }
                else {

                    Toast.makeText(getApplicationContext(),"Some fields are missing",Toast.LENGTH_SHORT).show();
                }





            }
        });



        numParticipant.setEnabled(false);
        price.setEnabled(false);
        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick(view);
            }
        });
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
    public void buttonClick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,Selected_Image);
    }
    public void EditTextNormalize(){
        eventTitle.setBackgroundResource(R.drawable.rounded_edittext);
        eventDescription.setBackgroundResource(R.drawable.rounded_edittext);
        if(isLimit.isChecked())
            numParticipant.setBackgroundResource(R.drawable.rounded_edittext_gray );
        else
            numParticipant.setBackgroundResource(R.drawable.rounded_edittext );
        if(isFree.isChecked())
            price.setBackgroundResource(R.drawable.rounded_edittext_gray );
        else
            price.setBackgroundResource(R.drawable.rounded_edittext );
        dateDisplay.setBackgroundResource(R.drawable.rounded_edittext);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case Selected_Image:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(uri,projection,null,null,null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(projection[0]);
                    String filePath = c.getString(columnIndex);
                    c.close();

                    Bitmap event_pic = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(event_pic);

                    kaka.setBackground(d);
                }
        }
    }
}
