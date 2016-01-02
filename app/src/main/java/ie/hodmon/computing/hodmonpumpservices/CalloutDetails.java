package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;


public class CalloutDetails extends ClassForCommonAttributes {



    private TextView calloutDetailsName;
    private TextView calloutDetailsStreet;
    private TextView calloutDetailsTown;
    private TextView calloutDetailsPhone;
    private TextView calloutDetailsPump;
    private TextView calloutDetailsReportedFault;
    private Callout calloutToDisplayInDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_details);
        calloutDetailsName=(TextView)findViewById(R.id.job_details_customer_name);
        calloutDetailsStreet=(TextView)findViewById(R.id.job_details_street);
        calloutDetailsTown=(TextView)findViewById(R.id.job_details_town);
        calloutDetailsPump=(TextView)findViewById(R.id.job_details_product_name);
        calloutDetailsReportedFault=(TextView)findViewById(R.id.job_details_fault);
        calloutToDisplayInDetail=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);
        calloutDetailsPhone=(TextView)findViewById(R.id.job_details_phone_number);
        calloutDetailsName.setText(calloutToDisplayInDetail.getCustomerName());
        calloutDetailsStreet.setText(calloutToDisplayInDetail.getStreet());
        calloutDetailsTown.setText(calloutToDisplayInDetail.getTown());
        calloutDetailsPump.setText(calloutToDisplayInDetail.getPumpNumber());
        calloutDetailsReportedFault.setText(calloutToDisplayInDetail.getReportedFault());
        calloutDetailsPhone.setText(calloutToDisplayInDetail.getPhoneNumber());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_details, menu);
        return true;
    }



    public void dialNumber(View view)
    {
        Uri number = Uri.parse("tel:" + calloutDetailsPhone.getText().toString());
        Intent dial = new Intent(Intent.ACTION_CALL, number);
        startActivity(dial);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    public void openReport(View view)
    {
        startActivity(new Intent(this, Report.class));

    }

    public void map(View view)
    {
        Callout c=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);

        LatLng jobLoc=c.getLatLng();
        Bundle args = new Bundle();
        Intent intent=new Intent(this,MapsActivity.class);
        args.putParcelable(c.getCustomerName(),jobLoc);
        args.putInt("zoom",13);
       intent.putExtra("bundle",args);
        startActivity(intent);
    }

}
