package com.example.iiitd.droid;

import android.app.Service;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.iiitd.droid.model.PingTask;
import com.example.iiitd.droid.model.PingTheTarget;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.Z;

public class ServiceClass extends Service {



    public static String target = "example.com";
    public static boolean pingcheck = false;

    /** indicates how to behave if the service is killed */
    //final Handler handler = new Handler();;
    int mStartMode;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;

    public final Handler handler = new Handler();
    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;
    //PingTask pingTask = new PingTask();

    /*
    Thread pingthread = new Thread() {
        @Override
        public void run() {
            boolean check = true;
            try {
                while(check) {
                    PingTask pingTask = new PingTask();
                    pingTask.execute();
                    if(pingcheck)
                        Log.d("ping test","ping successful");
                    else
                    Log.d("ping test","ping failed");
                    sleep(5000);
                    handler.post(this);
                    check = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    */


    /*

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            PingTask pingTask = new PingTask();
                            // PingTask this class is the class that extends AsynchTask
                            pingTask.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 5000 ms
    }

*/


    /** Called when the service is being created. */
    @Override
    public void onCreate() {
       // Sync sync = new Sync(this,6000);


    }



    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // int i=1;

        //callAsynchronousTask();
        try {
        while(true)
        {                Log.v("test", "this will run every minute");
        //    Intent intent2 = new Intent();

/*            intent2.setClassName("com.android.mms",
                    "com.android.mms.transaction.SmsReceiverService");

            intent2.setAction("android.provider.Telephony.SMS_RECEIVED");
            intent2.putExtra("pdus", new Object[] { "pdu" });
            intent2.putExtra("format", "3gpp");

            Log.v("Intent Sent","SMS");*/
           // this.startService(intent);

            Thread.sleep(6000);

            /*
            pingthread.run();

            Thread.sleep(30000);
            PingTheTarget pingTheTarget = new PingTheTarget();
            pingTheTarget.execute();

            */
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


            return START_STICKY;
        }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


    }

    public static void updateTarget(String server_response)
    {
        target = server_response;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }
}//https://www.google.co.in/search?client=ubuntu&channel=fs&q=how+to+run+service+indefinetely+even+with+restart&ie=utf-8&oe=utf-8&gfe_rd=cr&ei=fJQ4WOf-CMaL8QebhaDoDA#channel=fs&q=All+intents+android