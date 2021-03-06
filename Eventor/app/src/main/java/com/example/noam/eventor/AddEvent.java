package com.example.noam.eventor;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "AddEvent";

    public static final int Selected_Image = 1;
    int PLACE_PICKER_REQUEST = 2;
    EditText eventDescription;
    ImageButton datePick;
    Bitmap bitmap;
    TextView dateDisplay;
    ImageView kaka;
    ImageButton eventImage;
    EditText numParticipant;
    EditText price;
    CheckBox noLimit;
    CheckBox isFree;
    ImageButton locationButton;
    EditText locationText;
    Place place;
    CheckBox isPrivate;
    Button createButton;
    GoogleApiClient googleApiClient;
    int day, month, year, hour, minute;
    int fday, fmonth, fyear, fhour, fminute;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    GenericEvent event;
    Context context;
    Place place2 = new Place() {
        @Override
        public String getId() {
            return null;
        }

        @Override
        public List<Integer> getPlaceTypes() {
            return null;
        }

        @Override
        public CharSequence getAddress() {
            return null;
        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public CharSequence getName() {
            return null;
        }

        @Override
        public LatLng getLatLng() {
            return null;
        }

        @Override
        public LatLngBounds getViewport() {
            return null;
        }

        @Override
        public Uri getWebsiteUri() {
            return null;
        }

        @Override
        public CharSequence getPhoneNumber() {
            return null;
        }

        @Override
        public float getRating() {
            return 0;
        }

        @Override
        public int getPriceLevel() {
            return 0;
        }

        @Override
        public CharSequence getAttributions() {
            return null;
        }

        @Override
        public Place freeze() {
            return null;
        }

        @Override
        public boolean isDataValid() {
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGeoDataClient = Places.getGeoDataClient(AddEvent.this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        eventDescription = (EditText) findViewById(R.id.description_text);
        eventImage = (ImageButton) findViewById(R.id.event_image);
        datePick = (ImageButton) findViewById(R.id.date_choose);
        dateDisplay = (TextView) findViewById(R.id.date_text);
        numParticipant = (EditText) findViewById(R.id.numOfParticipants);
        noLimit = (CheckBox) findViewById(R.id.noLimit);
        price = (EditText) findViewById(R.id.priceText);
        isFree = (CheckBox) findViewById(R.id.isFree);
        isPrivate = (CheckBox) findViewById(R.id.isPrivate);
        createButton = (Button) findViewById(R.id.create_button);
        locationButton = (ImageButton) findViewById(R.id.loaction_button);
        locationText = (EditText) findViewById(R.id.location_text);
        final EditText costumeCategory = (EditText) findViewById(R.id.costume_category);

        numParticipant.setEnabled(false);
        price.setEnabled(false);
        locationText.setEnabled(false);
        context = getApplicationContext();

        //spinner
        String[] items = new String[]{"Football", "Basketball", "Volleyball", "Tennis", "Baseball", "Cricket", "Custom"};
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_catgory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String chosenOption = spinner.getSelectedItem().toString();
                if (chosenOption.matches("custom")) {
                    ViewGroup.LayoutParams params = spinner.getLayoutParams();
                    params.height = 0;
                    spinner.setLayoutParams(params);
                    params = costumeCategory.getLayoutParams();
                    params.height = 100;
                    costumeCategory.setLayoutParams(params);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        //spinner


        //create button
        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //check if valid fields
                EditTextNormalize();
                boolean error = false;
                if (eventDescription.getText().toString().matches("")) {
                    error = true;
                    eventDescription.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
                if (numParticipant.getText().toString().matches("") && !noLimit.isChecked()) {
                    error = true;
                    numParticipant.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
                if (price.getText().toString().matches("") && !isFree.isChecked()) {
                    error = true;
                    price.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
                if (dateDisplay.getText().toString().matches("")) {
                    error = true;
                    dateDisplay.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
                if (locationText.getText().toString().matches("")) {
                    error = true;
                    locationText.setBackgroundResource(R.drawable.rounded_edittext_red);
                }
                if (!error) {  //all fields are valid creat event
                    Gson gson = new Gson();
                    Date date = new Date();
                    String category;
                    String bitmapString = "No Image";
                    int maxUsers = Integer.MAX_VALUE; //if unlimited # of players
                    int priceOfEvent = 0;
                    if (spinner.getSelectedItem().toString().equals("Custom"))
                        category = costumeCategory.getText().toString();
                    else
                        category = spinner.getSelectedItem().toString();
                    if (!noLimit.isChecked()) {
                        maxUsers = Integer.parseInt(numParticipant.getText().toString());
                    }
                    if (!isFree.isChecked()) {
                        priceOfEvent = Integer.parseInt(price.getText().toString());
                    }
                    if (bitmap != null)
                        bitmapString = getStringFromBitmap(bitmap);

                    date.setYear(fyear);
                    date.setMonth(fmonth);
                    date.setDate(fday);
                    date.setHours(fhour);
                    date.setMinutes(fminute);

                    String placeId = place.getId();
                    event = new GenericEvent(
                            category,
                            date,
                            maxUsers,
                            eventDescription.getText().toString(),
                            priceOfEvent,
                            isPrivate.isChecked(),
                            bitmapString,
                            placeId,
                            place.getLatLng().longitude,
                            place.getLatLng().latitude,
                            CurrentUser.getInstance().getUser().getUserId()
                    );

                    //send event to server
                    String json = gson.toJson(event);
                    String toSend = json.toString();
                    NetworkManager instance = NetworkManager.getInstance();
                    instance.addRequest(new PostEventRequest(toSend, new ServerCallback() {

                        @Override
                        public void onSuccess(Object res, int statusCode) {
                            final String result = (String) res;
                            Log.e("AddEvent", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Event successfuly created!", Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(AddEvent.this, UserAreaMain.class);
                                    //myIntent.putExtra("key", value); //Optional parameters
                                    AddEvent.this.startActivity(myIntent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Object err, int statusCode) {
                            Log.e("AddEvent", "Connection to Server failed");
                        }
                    }));
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Some fields are missing", Toast.LENGTH_SHORT).show();
                }
            }
        });//end create button



        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(AddEvent.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagebuttonClick(view);

            }
        });

        //max Paticipants limit and price handler
        noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    findViewById(R.id.numOfParticipants).setEnabled(false);
                    findViewById(R.id.numOfParticipants).setBackgroundResource(R.drawable.rounded_edittext_gray);
                } else {
                    findViewById(R.id.numOfParticipants).setEnabled(true);
                    findViewById(R.id.numOfParticipants).setBackgroundResource(R.drawable.rounded_edittext);
                }
            }
        });
        isFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    findViewById(R.id.priceText).setEnabled(false);
                    findViewById(R.id.priceText).setBackgroundResource(R.drawable.rounded_edittext_gray);
                } else {
                    findViewById(R.id.priceText).setEnabled(true);
                    findViewById(R.id.priceText).setBackgroundResource(R.drawable.rounded_edittext);
                }
            }
        });
        //max Paticipants limit and price handler


        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent.this, AddEvent.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        fyear = i;
        fmonth = i1 + 1;
        fday = i2;
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this, AddEvent.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        fhour = i;
        fminute = i1;
        dateDisplay.setText(fday + "/" + fmonth + "/" + fyear + "   " + fhour + ":" + fminute);
    }

    public void ImagebuttonClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (data != null)) {
            if (requestCode == Selected_Image) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    eventImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            if (requestCode == PLACE_PICKER_REQUEST) {
                place = PlacePicker.getPlace(data, this);
                String address = String.format("%s, %s", place.getName(), place.getAddress());
                locationText.setText(address);

            }
        }

    }

    public void EditTextNormalize() {
        eventDescription.setBackgroundResource(R.drawable.rounded_edittext);
        if (noLimit.isChecked())
            numParticipant.setBackgroundResource(R.drawable.rounded_edittext_gray);
        else
            numParticipant.setBackgroundResource(R.drawable.rounded_edittext);
        if (isFree.isChecked())
            price.setBackgroundResource(R.drawable.rounded_edittext_gray);
        else
            price.setBackgroundResource(R.drawable.rounded_edittext);
        dateDisplay.setBackgroundResource(R.drawable.rounded_edittext);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}

