package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Job;


public class JobDetails extends ClassForCommonAttributes {



    private TextView jobDetailsName;
    private TextView jobDetailsStreet;
    private TextView jobDetailsTown;
    private TextView jobDetailsPhone;
    private TextView jobDetailsProduct;
    private TextView jobDetailsReportedFault;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Intent i=getIntent();
        String jobId=i.getStringExtra("id");
        jobDetailsName =(TextView)findViewById(R.id.job_details_customer_name);
        jobDetailsStreet=(TextView)findViewById(R.id.job_details_street);
        jobDetailsTown=(TextView)findViewById(R.id.job_details_town);
        jobDetailsProduct =(TextView)findViewById(R.id.job_details_product_name);
        jobDetailsReportedFault=(TextView)findViewById(R.id.job_details_fault);
        jobDetailsPhone=(TextView)findViewById(R.id.job_details_phone_number);
        new GetJob(this).execute("/jobs/"+jobId);



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
        Intent intent = new Intent(this, ReportScreen.class);

      
        startActivity(intent);

    }

    public void map(View view)
    {


        LatLng jobLoc=convertStringToLatLng(jobToDisplay.getCustomer().getLat_lng());
        Bundle args = new Bundle();
        Intent intent=new Intent(this,MapsActivity.class);
        args.putParcelable(jobToDisplay.getCustomer().getName(),jobLoc);
        args.putInt("zoom",13);
       intent.putExtra("bundle",args);
        startActivity(intent);
    }

    private class GetJob extends AsyncTask<String, Void,Job> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetJob(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Job");
            this.dialog.show();
        }

        @Override
        protected Job doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Job");
                return (Job) ConnectionAPI.getJob((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Job result) {
            super.onPostExecute(result);

            jobToDisplay=result;
            jobDetailsName.setText(jobToDisplay.getCustomer().getName());
            jobDetailsStreet.setText(jobToDisplay.getCustomer().getStreet());
            jobDetailsTown.setText(jobToDisplay.getCustomer().getTown());
            jobDetailsProduct.setText(jobToDisplay.getManufacturer().getName()+" "+jobToDisplay.getProduct().getProduct_number());
            jobDetailsReportedFault.setText(jobToDisplay.getReported_fault());
            jobDetailsPhone.setText(jobToDisplay.getCustomer().getPhone());


            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

        }
    }

}
