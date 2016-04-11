package ie.hodmon.computing.service_manager.controller;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Job;


/**
 * Created by John on 19/02/2015.
 */
public class ClassForCommonAttributes extends AppCompatActivity

{




    public static Job jobToDisplay;
    public static String dateToShow;
    public static boolean notToday;
    public static String engineerEmail;
    public static long dateSelected;
    public static List<Job>jobs;
    public static boolean loggedIn;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }



    public LatLng convertStringToLatLng(String s)
    {
        String split[]=s.split(",");
        double lat=Double.parseDouble(split[0]);
        double lng=Double.parseDouble(split[1]);
        return new LatLng(lat,lng);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this,"Logout pressed",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}





