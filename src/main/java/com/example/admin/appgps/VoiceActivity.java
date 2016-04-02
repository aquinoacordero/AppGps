package com.example.admin.appgps;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener{

    protected static final int REQUEST_OK = 1;
    TextView positions;
    Button voice, butLat, butLong, butMap;
    Integer x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        positions = (TextView)findViewById(R.id.textVoice);
        voice = (Button) findViewById(R.id.butVoice);
        butLat = (Button)findViewById(R.id.butLat);
        butLong = (Button)findViewById(R.id.butLong);
        butMap = (Button)findViewById(R.id.butMap);

        voice.setOnClickListener(this);
        butLat.setOnClickListener(this);
        butLong.setOnClickListener(this);
        butMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent iMap = new Intent(this, MapsActivity.class);

        switch(v.getId()){
            case R.id.butVoice:
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(i, REQUEST_OK);
                } catch (Exception e) {
                    Toast.makeText(this, "Error initializing speech.", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.butLat:
                x=Integer.parseInt(positions.getText().toString());
                Toast.makeText(this, "Sending latitude: "+x, Toast.LENGTH_LONG).show();
                break;

            case R.id.butLong:
                y=Integer.parseInt(positions.getText().toString());
                Toast.makeText(this, "Sending longitude: "+x, Toast.LENGTH_LONG).show();
                break;

            case R.id.butMap:
                iMap.putExtra("call", 2);
                iMap.putExtra("latV",x);
                iMap.putExtra("lonV",y);
                startActivity(iMap);
                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.textVoice)).setText(thingsYouSaid.get(0));
        }
    }
}
