package com.example.iiitd.droid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by iiitd on 26/11/16.
 */



public class ServiceStarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Intent serviceIntent = new Intent(arg0, ServiceClass.class);
        arg0.startService(serviceIntent);
    }
}

