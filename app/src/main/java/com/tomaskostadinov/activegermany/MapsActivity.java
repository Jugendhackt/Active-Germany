package com.tomaskostadinov.activegermany;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(new MarkerOptions().position(new LatLng(52.51121, 13.41719)).title("Kochen ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.kochen)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(52.52581, 13.36019)).title("Joggen ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.sport)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(52.55526, 13.45432)).title("Lernen ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.help)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.10256, 8.619293)).title("Lasertag ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.games)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(47.564666, 10.92041)).title("Wandern ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.gruppe)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(52.59237, 13.41774)).title("Film Abend ").snippet("ist hier")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.cam)));

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(52.51121, 13.41719));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(9);

        mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);

    }
}
