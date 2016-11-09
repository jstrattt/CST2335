package com.example.jessica.lab1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.X509TrustManagerExtensions;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.R.attr.entries;
import static android.R.attr.minDate;
import static android.R.attr.value;

public class WeatherForecast extends AppCompatActivity  {
    protected static final String ACTIVITY_START ="WeatherForecast";
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar);

        progress.setVisibility(View.VISIBLE);
        ForecastQuery fq = new ForecastQuery();
        fq.execute();




    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        ProgressBar progressbar = (ProgressBar) findViewById(R.id.progressBar);
        String min = "";
        String max = "";
        String current = "";
        String iconName = "";

        protected String doInBackground(String... params) {


            InputStream stream = null;

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream istream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(istream, "UTF8");
                boolean finished = false;
                int type = XmlPullParser.START_DOCUMENT;

                while (type != XmlPullParser.END_DOCUMENT) {

                    switch (type) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            finished = true;
                            break;
                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            if (name.equals("temperature")) {
                                min = xpp.getAttributeValue(null, "min");
                                publishProgress(25);
                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(50);
                                current = xpp.getAttributeValue(null, "value");
                                publishProgress(75);
                            }
                            if (name.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    type = xpp.next(); //advances to next xml event
                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

            try {

                if (fileExistance(String.valueOf(iconName + ".png"))) {

                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(WeatherForecast.this.getFilesDir()+"/"+iconName + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bit = BitmapFactory.decodeStream(fis);


                } else {
                    URL url2 = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                    conn2.setReadTimeout(10000 /* milliseconds */);
                    conn2.setConnectTimeout(15000 /* milliseconds */);
                    conn2.setRequestMethod("GET");
                    conn2.setDoInput(true);
                    conn2.connect();
                    InputStream instream = conn2.getInputStream();
                    bit = BitmapFactory.decodeStream(instream);
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    bit.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();

                }
            }
            catch(Exception e) {

                Log.e("Error" , e.getMessage());
            }
            publishProgress(100);
            return null;
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();

        }


        public void onProgressUpdate(Integer...progress) {

        progressbar.setVisibility(View.VISIBLE);
        progressbar.setProgress(progress[0]);


        }

        @Override
        protected void onPostExecute(String result) {
            ImageView weatherimage = (ImageView) findViewById(R.id.currentWeather);
            weatherimage.setImageBitmap(bit);

            TextView textmin = (TextView) findViewById(R.id.minTemp);
            textmin.setText("Minimum Temperature: " + min);
            TextView textmax = (TextView) findViewById(R.id.maxTemp);
            textmax.setText("Maximum Temperature: " + max);
            TextView textcurrent = (TextView) findViewById(R.id.currentTemp);
            textcurrent.setText("Current Temperature: " + current);

            progressbar.setVisibility(View.VISIBLE);
    }


    }
}
