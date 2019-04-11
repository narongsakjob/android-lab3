package com.example.loginfirebase;

import android.Manifest;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class ViewLocation extends AppCompatActivity {

    private Button getLocation, stopGPS, startTimer;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private int counter;
    long sec = 0;

    GPSTracker gps;

    Handler h = new Handler();
    Thread task;
    private long startTime;
    private String timeString;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);

        try{
            if(ActivityCompat.checkSelfPermission(this, mPermission) != PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        getLocation = findViewById(R.id.getLocation);
        startTimer = findViewById(R.id.button4);
        textView = findViewById(R.id.textView2);
        stopGPS = findViewById(R.id.stopGps);


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(ViewLocation.this);

                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(),counter + "\nCurrent loation is \n Lat:" + latitude + "\nLong: " + longitude,
                            Toast.LENGTH_LONG).show();
                }else {
                    gps.showSettingAlert();
                }
                counter++;
            }
        });

        stopGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTime();
                textView.setText("Timer Stop");
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime();


                counter = 1;
            }
        });
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
        task = new Thread() {
            @Override
            public void run() {
                super.run();

                long millis = System.currentTimeMillis() - startTime;
                sec = millis / 1000 % 60;

                timeString = String.format("%02d", sec);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gps = new GPSTracker(ViewLocation.this);

                        if(gps.canGetLocation()){
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            if (sec%10 == 0) {
                                Toast.makeText(getApplicationContext(),timeString + "\nCurrent location is \n Lat:" + latitude + "\nLong: " + longitude,
                                        Toast.LENGTH_LONG).show();
                            }

                        }else {
                            gps.showSettingAlert();
                        }
                        textView.setText(timeString);
                    }
                });
                h.postDelayed(task, 1000);
            }
        };
        h.postDelayed(task, 1000);
    }

    public void stopTime() {
        h.removeCallbacks(task);
    }
}
