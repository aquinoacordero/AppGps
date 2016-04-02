package com.example.admin.appgps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button map;
    Button location, voice;
    EditText latitude;
    EditText longitude;
    TextView res;
    Location l;
    int pos;
    String zone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = (Button)findViewById(R.id.butMap);
        location = (Button)findViewById(R.id.butLoc);
        voice = (Button)findViewById(R.id.butCallVoice);
        latitude = (EditText)findViewById(R.id.editTextLat);
        longitude = (EditText)findViewById(R.id.editTextLong);
        res = (TextView)findViewById(R.id.textViewResult);

        location.setOnClickListener(this);
        map.setOnClickListener(this);
        voice.setOnClickListener(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int orientation = display.getRotation();
        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180)
        {
            pos=0;
            Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show();
        }
        else
        {
            pos=1;
            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v){

        Intent intent = new Intent(this, MapsActivity.class);
        Intent intentV = new Intent(this, VoiceActivity.class);

        switch(v.getId()){

            case R.id.butLoc:

                l = new Location();
                String.valueOf(l.execute(latitude.getText().toString(), longitude.getText().toString()));   // Parámetros que recibe doInBackground
                break;

            case R.id.butMap:

                Handler handler = new Handler();

                if (pos==0) {
                    Toast toast = Toast.makeText(this, "Looking in the map", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 750);
                    toast.show();
                } else if (pos==1){
                    Toast toast = Toast.makeText(this, "Looking in the map", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 30, 400);
                    toast.show();
                }
                //res.setText("mapa");

                Integer lon = Integer.parseInt(longitude.getText().toString());
                Integer lat = Integer.parseInt(latitude.getText().toString());

                if (lon == null || lat == null) {
                    Toast aviso = Toast.makeText(getApplicationContext(), "Nedd enter data", Toast.LENGTH_SHORT);
                    aviso.setGravity(Gravity.TOP, 0, 0);
                    aviso.show();
                } else {
                    intent.putExtra("longitud", lon);
                    intent.putExtra("latitud", lat);
                    intent.putExtra("call", 1);
                    intent.putExtra("zone",zone);
                    startActivity(intent);
                }
                break;

            case R.id.butCallVoice:

                Toast aviso = Toast.makeText(getApplicationContext(), "Voice", Toast.LENGTH_SHORT);
                aviso.show();

                startActivity(intentV);


            default:
                break;
        }
    }


    public class Location extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String pos = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

            //http://maps.googleapis.com/maps/api/geocode/json?lating=38.4045,-0.5295&sensor=false
            pos = pos + params[0];
            pos = pos + ",";
            pos = pos + params[1];
            pos = pos + "&sensor=false";

            URL url = null;
            String return_Dir = "ERROR ON THE APiURL";

            try{
                url = new URL(pos);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0" + " (Linux; Android 1.5; es-Es) Ejemplo HTTP");

                int answer = connection.getResponseCode();
                StringBuilder result = new StringBuilder();

                if (answer != -1){

                    //HttpURLConnection.HTTP_OK;
                    String dir="NO DATA";

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader read = new BufferedReader(new InputStreamReader(in));

                    String line;

                    while((line = read.readLine()) != null){
                        result.append(line);
                    }

                    JSONObject answerJ = new JSONObject(result.toString());
                    JSONArray resultJSON = answerJ.getJSONArray("results");


                    if (resultJSON.length()>0){
                        dir = resultJSON.getJSONObject(0).getString("formatted_address");    // dentro del results pasamos a Objeto la seccion formated_addres
                    }
                        zone = dir;
                        return_Dir = "Direction: " + dir;
                    }

                } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return return_Dir;
        }

        @Override
        protected void onCancelled(String aVoid) {

            super.onCancelled(aVoid);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            res.setText(aVoid);
            //super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            res.setText("");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
