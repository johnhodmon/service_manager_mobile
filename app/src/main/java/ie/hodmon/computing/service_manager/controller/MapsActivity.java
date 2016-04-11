package ie.hodmon.computing.service_manager.controller;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private List<LatLng> coordinates = new ArrayList<LatLng>();
    private int zoom;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bundle = getIntent().getParcelableExtra("bundle");
        for (String key : bundle.keySet()) {
            if (key.equals("zoom")) {

                zoom = bundle.getInt(key);
            }


        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        map.setMyLocationEnabled(true);

        for (String key : bundle.keySet()) {
            if (!key.equals("zoom")) {
                coordinates.add((LatLng) bundle.get(key));
                map.addMarker(new MarkerOptions().position((LatLng) bundle.get(key)).title(key));

            }


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates.get(0), zoom));
        }
    }
}

