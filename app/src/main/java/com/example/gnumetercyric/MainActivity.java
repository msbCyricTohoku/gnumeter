package com.example.gnumetercyric;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final boolean SHOW_SPEED_IN_BITS = false;

    private TrafficSpeedMeasurer mTrafficSpeedMeasurer;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.connection_class);

        mTrafficSpeedMeasurer = new TrafficSpeedMeasurer(TrafficSpeedMeasurer.TrafficType.ALL);
        mTrafficSpeedMeasurer.startMeasuring();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTrafficSpeedMeasurer.stopMeasuring();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // mTrafficSpeedMeasurer.removeListener(mStreamSpeedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTrafficSpeedMeasurer.registerListener(mStreamSpeedListener);
    }




    private ITrafficSpeedListener mStreamSpeedListener = new ITrafficSpeedListener() {





        @Override
        public void onTrafficSpeedMeasured(final double upStream, final double downStream) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String upStreamSpeed = Utils.parseSpeed(upStream, SHOW_SPEED_IN_BITS);
                    String downStreamSpeed = Utils.parseSpeed(downStream, SHOW_SPEED_IN_BITS);

                    final double totspeed =upStream+downStream;

                    String totalspeed = Utils.parseSpeed(totspeed,SHOW_SPEED_IN_BITS);



                    mTextView.setText("Upload Speed: " + upStreamSpeed + "\n" + "Download Speed: " + downStreamSpeed
                    +"\n"+"Total Speed: "+totalspeed+"\n");

                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle(upStreamSpeed+" "+downStreamSpeed);


                    Integer totss = Utils.parseval(totspeed,SHOW_SPEED_IN_BITS);



                    String CHANNEL_ID = "channel_01";

                    int NOTIFICATION_ID = 234;
                    NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    // String CHANNEL_ID;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CHANNEL_ID = "my_channel_01";
                        CharSequence name = "my_channel";
                        String Description = "This is my channel";
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                        mChannel.setDescription(Description);
                        mChannel.enableLights(true);
                        // mChannel.setLightColor(Color.RED);
                        //  mChannel.enableVibration(true);
                        //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        mChannel.setShowBadge(false);
                        notificationManager.createNotificationChannel(mChannel);
                    }


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);

                    // NotificationChannel builderx = new NotificationChannel(CHANNEL_ID,"GNUmeter",NotificationManager.IMPORTANCE_HIGH);

                    //builderx.setShowBadge(false);

                //    String name = "your_drawable";
                //    int id = getResources().getIdentifier(name, "drawable", getPackageName());
                //    Drawable jj = getResources().getDrawable(id);
//                    Drawable d = new BitmapDrawable(getResources(), bmpFinal);

                    String val1="0.0 B/s";


                    if(totss < 10)
                    {
//                        builder.setSmallIcon(upme);
                        builder.setSmallIcon(R.drawable.no);
                    }
                    else if((totss >=10) && (totss<512))
                    {
                        builder.setSmallIcon(R.drawable.bl);
                    }
                    else if((totss >=512) && (totss<1024))
                    {
                        builder.setSmallIcon(R.drawable.bh);

                    }
                    else if((totss >=1024) && (totss<5242))
                    {
                        builder.setSmallIcon(R.drawable.kbl);
                    }
                    else if((totss >=5242) && (totss<10485))
                    {
                        builder.setSmallIcon(R.drawable.kbh);
                    }
                    else if((totss >=10485) && (totss<3579139))
                    {
                        builder.setSmallIcon(R.drawable.mbl);
                    }
                    else if((totss >=3579139) && (totss<5368709))
                    {
                        builder.setSmallIcon(R.drawable.mbh);
                    }
                    else if((totss >=5368709) && (totss<10737418))
                    {
                        builder.setSmallIcon(R.drawable.mbvh);
                    }
                    else if(totss >= 10737418)
                    {
                        builder.setSmallIcon(R.drawable.gb);
                    }



/*

                    else{
  //                      builder.setSmallIcon(upme);
                        builder.setSmallIcon(R.drawable.green);
                    }

*/




                    //builder.setSmallIcon(R.drawable.speed);
                    //builder.setLargeIcon(jj);
                    builder.setContentTitle("GNUmeter");
                    builder.setContentText("UP:"+upStreamSpeed+" DW:"+downStreamSpeed);
                    builder.setPriority(NotificationCompat.PRIORITY_LOW);
                    builder.setAutoCancel(true);
                    builder.setTicker(upStreamSpeed);
                    builder.setPriority(Notification.PRIORITY_HIGH);

                   // builder.setWhen(System.currentTimeMillis());
                   // NotificationManager.IMPORTANCE_LOW;






                    NotificationManagerCompat managerCompat =NotificationManagerCompat.from(MainActivity.this);
                    managerCompat.notify(1,builder.build());






                }
            });



        }



    };

}
