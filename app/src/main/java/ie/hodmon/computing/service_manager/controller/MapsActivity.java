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


    private List<LatLng> coordinates=new ArrayList<LatLng>();
    private int zoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        for (String key : bundle.keySet()) {
            if(!key.equals("zoom")) {
                coordinates.add((LatLng) bundle.get(key));
            }

            else
            {
                zoom=bundle.getInt(key);
            }

        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        map.setMyLocationEnabled(true);
        for (LatLng l:coordinates)
        {
            map.addMarker(new MarkerOptions().position(l).title("Job Here"));

        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates.get(0), zoom));
    }
}

