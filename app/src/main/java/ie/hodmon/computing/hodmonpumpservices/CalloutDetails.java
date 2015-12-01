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

import org.w3c.dom.Text;


public class CalloutDetails extends ClassForCommonAttributes {


    private TextView calloutDetailsDate;
    private TextView calloutDetailsName;
    private TextView calloutDetailsStreet;
    private TextView calloutDetailsTown;
    private TextView calloutDetailsCounty;
    private TextView calloutDetailsPhone;
    private TextView calloutDetailsPump;
    private TextView calloutDetailsReportedFault;
    private Callout calloutToDisplayInDetail;
    private Button diallerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_details);
        calloutDetailsDate=(TextView)findViewById(R.id.calloutDetailsDate);
        calloutDetailsName=(TextView)findViewById(R.id.calloutDetailsName);
        calloutDetailsStreet=(TextView)findViewById(R.id.calloutDetailsStreet);
        calloutDetailsTown=(TextView)findViewById(R.id.calloutDetailsTown);
        calloutDetailsCounty=(TextView)findViewById(R.id.calloutDetailsCounty);
        calloutDetailsPump=(TextView)findViewById(R.id.calloutDetailsPump);
        calloutDetailsReportedFault=(TextView)findViewById(R.id.calloutDetailsReportedFault);
        diallerButton=(Button)findViewById(R.id.diallerButton);
        calloutToDisplayInDetail=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);

        calloutDetailsDate.setText(calloutToDisplayInDetail.getDate());
        calloutDetailsName.setText(calloutToDisplayInDetail.getCustomerName());
        calloutDetailsStreet.setText(calloutToDisplayInDetail.getStreet());
        calloutDetailsTown.setText(calloutToDisplayInDetail.getTown());
        calloutDetailsCounty.setText(calloutToDisplayInDetail.getCounty());
        diallerButton.setText(calloutToDisplayInDetail.getPhoneNumber());
        calloutDetailsPump.setText(calloutToDisplayInDetail.getPumpNumber());
        calloutDetailsReportedFault.setText(calloutToDisplayInDetail.getReportedFault());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


         if(id==R.id.action_edit_report)
        {
            formCalledFromEdit=true;
            startActivity(new Intent(this,CalloutForm.class));


        }

        else if(id==R.id.action_report)
        {

            startActivity(new Intent(this,Report.class));


        }

        return super.onOptionsItemSelected(item);
    }

    public void dialNumber(View view)
    {
        Uri number = Uri.parse("tel:" + diallerButton.getText().toString());
        Intent dial = new Intent(Intent.ACTION_CALL, number);
        startActivity(dial);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }
}
