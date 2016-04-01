package com.example.admin.appgps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private  String city;
    int lon, lat;
    Button pos,hyb,ter,sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pos = (Button)findViewById(R.id.toast);
        hyb = (Button)findViewById(R.id.hybrid);
        ter = (Button)findViewById(R.id.terrain);
        sat = (Button)findViewById(R.id.satellite);

        hyb.setOnClickListener(this);
        ter.setOnClickListener(this);
        sat.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle valors=this.getIntent().getExtras();
        lon = valors.getInt("longitud");
        lat = valors.getInt("latitud");

    }

    public void botonDeAitor(View v){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, "Latitude: "+lat+" Longitude: "+lon, duration).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                break;


        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        city="Sidney";
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lon, lat);
        //LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+city));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
