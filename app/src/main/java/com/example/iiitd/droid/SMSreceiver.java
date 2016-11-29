package com.example.iiitd.droid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Rohit on 28/11/2016.
 */
public class SMSreceiver extends BroadcastReceiver
{
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();

        String strMessage = "";

        if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );

            if (smsextras == null)
                return;

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                String strMsgBody = smsmsg.getMessageBody().toString();
                String strMsgSrc = smsmsg.getOriginatingAddress();

                strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;
                Log.v("SMS","SSSSSS MMMMMM SSSSS Receivede");
                Log.v(TAG, strMessage);
            }

        }

    }

}
