package ie.hodmon.computing.service_manager.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Job;


public class JobDetails extends ClassForCommonAttributes {



  /*  private TextView jobDetailsName;
    private TextView jobDetailsStreet;
    private TextView jobDetailsTown;
    private TextView jobDetailsPhone;
    private TextView jobDetailsPump;
    private TextView jobDetailsReportedFault;
    private Job jobToDisplayInDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        jobDetailsName =(TextView)findViewById(R.id.job_details_customer_name);
        jobDetailsStreet=(TextView)findViewById(R.id.job_details_street);
        jobDetailsTown=(TextView)findViewById(R.id.job_details_town);
        jobDetailsPump=(TextView)findViewById(R.id.job_details_product_name);
        jobDetailsReportedFault=(TextView)findViewById(R.id.job_details_fault);
        jobToDisplayInDetail =dbManager.getSinglejob(idOfJobToDisplayInDetail);
        jobDetailsPhone=(TextView)findViewById(R.id.job_details_phone_number);
        jobDetailsName.setText(jobToDisplayInDetail.getCustomerName());
        jobDetailsStreet.setText(jobToDisplayInDetail.getStreet());
        jobDetailsTown.setText(jobToDisplayInDetail.getTown());
        jobDetailsPump.setText(jobToDisplayInDetail.getPumpNumber());
        jobDetailsReportedFault.setText(jobToDisplayInDetail.getReportedFault());
        jobDetailsPhone.setText(jobToDisplayInDetail.getPhoneNumber());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_details, menu);
        return true;
    }



    public void dialNumber(View view)
    {
        Uri number = Uri.parse("tel:" + jobDetailsPhone.getText().toString());
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
        startActivity(new Intent(this, ReportScreen.class));

    }

    public void map(View view)
    {
        Job c=dbManager.getSinglejob(idOfJobToDisplayInDetail);

        LatLng jobLoc=c.getLatLng();
        Bundle args = new Bundle();
        Intent intent=new Intent(this,MapsActivity.class);
        args.putParcelable(c.getCustomerName(),jobLoc);
        args.putInt("zoom",13);
       intent.putExtra("bundle",args);
        startActivity(intent);
    }*/

}
