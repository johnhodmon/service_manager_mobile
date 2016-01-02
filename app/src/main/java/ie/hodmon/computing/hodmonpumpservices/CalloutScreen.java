package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalloutScreen extends ClassForCommonAttributes implements AdapterView.OnItemClickListener {

    private ListView calloutListView;
    private List<Callout> callouts;
    private ImageView mapButton;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_screen);

        Log.v("check email","email: "+engineerEmail);
        mapButton = (ImageView)findViewById(R.id.directions_icon);

        calloutListView=(ListView)findViewById(R.id.calloutListView);
        calloutListView.setOnItemClickListener(this);


        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate ="";
        if(notToday) {
            formattedDate=dateToShow;

        }

        else
        {
            formattedDate = df.format(c.getTime());
        }
        callouts=dbManager.getCallouts(engineerEmail,formattedDate);
        if (callouts.isEmpty())
        {
            mapButton.setVisibility(View.INVISIBLE);
        }
        CalloutsAdapter adapterForCalloutListView =new CalloutsAdapter(this,callouts);
        calloutListView.setAdapter(adapterForCalloutListView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_screen, menu);
        return true;
    }








    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Callout c=(Callout)parent.getItemAtPosition(position);
        idOfCalloutToDisplayInDetail=c.getId();
        startActivity(new Intent(this,CalloutDetails.class));
    }

    public void changeDate(View view)
    {

        startActivity(new Intent(this,PickDate.class));
    }

    public void callCustomer(View view)
    {
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();


        TextView phoneTextViewThisRow=(TextView)rowContainingButton.getChildAt(3);

        Uri number = Uri.parse("tel:" + phoneTextViewThisRow.getText().toString());
        Intent dial = new Intent(Intent.ACTION_CALL, number);
        startActivity(dial);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }


    public void map(View view)
    {
        Bundle args = new Bundle();
        Intent intent=new Intent(this,MapsActivity.class);

        for(Callout c: callouts)
        {
            args.putParcelable(c.getCustomerName(),c.getLatLng());
        }
        args.putInt("zoom",10);


        intent.putExtra("bundle",args);
        startActivity(intent);
    }
}

