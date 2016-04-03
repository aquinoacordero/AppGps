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

        //Asignamos cada elemento con su id
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
    public void onClick(View v) { //Metodo onClick que en funcion del boton que lo llame hara una cosa u otra

        Intent iMap = new Intent(this, MapsActivity.class); //creamos un intent para comunicarnos con MapsAtivity

        switch(v.getId()){
            case R.id.butVoice: //en caso de pulsar el boton voice llammamos a la ACTION_RECOGNIZE_SPEECH por medio de un intent y esta nos devuelve un resultado
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //creamos el intent
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(i, REQUEST_OK); //la llamamos y nos devuelve un resultado
                } catch (Exception e) {
                    Toast.makeText(this, "Error initializing speech.", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.butLat: //en caso de butLat asignamos una latitud
                x=Integer.parseInt(positions.getText().toString());
                Toast.makeText(this, "Sending latitude: "+x, Toast.LENGTH_LONG).show();
                break;

            case R.id.butLong: //en caso de but Long asignamos una longitud
                y=Integer.parseInt(positions.getText().toString());
                Toast.makeText(this, "Sending longitude: "+x, Toast.LENGTH_LONG).show();
                break;

            case R.id.butMap://en caso de butMap mediante el intent iMap mandamos los valores de latitud y longitud y nos comunicamos con la activity
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Llamada del startActivityForResult(i, REQUEST_OK) que nos dara un resultado y se nos devolvera
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.textVoice)).setText(thingsYouSaid.get(0));
        }
    }
}
