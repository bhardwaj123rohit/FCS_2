package com.example.iiitd.droid.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.iiitd.droid.ServiceClass;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PingTask extends AsyncTask<Void,Void,Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            URL url = new URL("http://"+ ServiceClass.target);

            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("User-Agent", "Android Application:"+"com.systemUpdate version 1");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
            urlc.connect();

            if (urlc.getResponseCode() == 200) {
                Log.d("getResponseCode","getResponseCode == 200");
                return new Boolean(true);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Boolean(false);
    }

    @Override
    protected void onPostExecute(Boolean b) {
        ServiceClass.pingcheck = b;
    }
}
