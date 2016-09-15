package com.example.admin.childcaremessage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Admin on 20-04-2016.
 */

public class SecondActivity extends Activity {
    Button call_b,send;
    GPSTracker gps;
    String phone="8675673201",msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        call_b =(Button)findViewById(R.id.call);
        send = (Button)findViewById(R.id.send);
        call_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
/*
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("8675673201"));//change the number
                startActivity(callIntent);
*/
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +phone));

                try{
                    startActivity(intent);
                }

                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }

            }
        });
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    gps = new GPSTracker(SecondActivity.this);

                    // check if GPS enabled
                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        // \n is for new line
                        msg = "Latitude ===>11.0332° N,Longitude====> 77.0277° E";
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, msg, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });


    }

}
