package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;


public class JobDetails extends ClassForCommonAttributes implements RadioGroup.OnCheckedChangeListener {


    private TextView jobDetailsName;
    private TextView jobDetailsStreet;
    private TextView jobDetailsTown;
    private TextView jobDetailsPhone;
    private TextView jobDetailsProduct;
    private TextView jobDetailsReportedFault;
    private RadioGroup jobProgress;
    private RadioButton travelling;
    private RadioButton onSite;
    private RadioButton beginJob;
    private RadioButton jobComplete;
    private RadioButton returnVisit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Intent i = getIntent();
        String jobId = i.getStringExtra("id");
        jobDetailsName = (TextView) findViewById(R.id.job_details_customer_name);
        jobDetailsStreet = (TextView) findViewById(R.id.job_details_street);
        jobDetailsTown = (TextView) findViewById(R.id.job_details_town);
        jobDetailsProduct = (TextView) findViewById(R.id.job_details_product_name);
        jobDetailsReportedFault = (TextView) findViewById(R.id.job_details_fault);
        jobDetailsPhone = (TextView) findViewById(R.id.job_details_phone_number);
        travelling = (RadioButton) findViewById(R.id.rb_travelling);
        jobProgress = (RadioGroup) findViewById(R.id.rg_job_progress);

        onSite = (RadioButton) findViewById(R.id.rb_on_site);
        beginJob = (RadioButton) findViewById(R.id.rb_start_job);
        jobComplete = (RadioButton) findViewById(R.id.rb_job_complete);
        returnVisit = (RadioButton) findViewById(R.id.rb_return_visit);
        new GetJob(this).execute("/jobs/" + jobId);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_details, menu);
        return true;
    }


    public void dialNumber(View view) {
        Uri number = Uri.parse("tel:" + jobDetailsPhone.getText().toString());
        Intent dial = new Intent(Intent.ACTION_CALL, number);
        startActivity(dial);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void openReport(View view) {
        Intent intent = new Intent(this, ReportScreen.class);


        startActivity(intent);

    }

    public void map(View view) {


        LatLng jobLoc = convertStringToLatLng(jobToDisplay.getCustomer().getLat_lng());
        Bundle args = new Bundle();
        Intent intent = new Intent(this, MapsActivity.class);
        args.putParcelable(jobToDisplay.getCustomer().getName(), jobLoc);
        args.putInt("zoom", 13);
        intent.putExtra("bundle", args);
        startActivity(intent);
    }

    public void getDirections(View view) {

        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    private class GetJob extends AsyncTask<String, Void, Job> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetJob(Context context) {
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
            } catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Job result) {
            super.onPostExecute(result);

            jobToDisplay = result;
            jobDetailsName.setText(jobToDisplay.getCustomer().getName());
            jobDetailsStreet.setText(jobToDisplay.getCustomer().getStreet());
            jobDetailsTown.setText(jobToDisplay.getCustomer().getTown());
            jobDetailsProduct.setText(jobToDisplay.getManufacturer().getName() + " " + jobToDisplay.getProduct().getProduct_number());
            jobDetailsReportedFault.setText(jobToDisplay.getReported_fault());
            jobDetailsPhone.setText(jobToDisplay.getCustomer().getPhone());


            if(jobToDisplay.getStatus().equals("allocated"))
            {

                travelling.setEnabled(true);
                onSite.setEnabled(false);
                beginJob.setEnabled(false);
                jobComplete.setEnabled(false);
                returnVisit.setEnabled(false);

            }

            else if(jobToDisplay.getStatus().equals("travelling"))
            {
                travelling.setEnabled(false);
                travelling.setChecked(true);
                onSite.setEnabled(true);
                beginJob.setEnabled(false);
                jobComplete.setEnabled(false);
                returnVisit.setEnabled(false);

            }

           else if(jobToDisplay.getStatus().equals("on site"))
            {
                travelling.setEnabled(false);
                onSite.setEnabled(false);
                onSite.setChecked(true);
                beginJob.setEnabled(true);
                jobComplete.setEnabled(false);
                returnVisit.setEnabled(false);


            }

            else if(jobToDisplay.getStatus().equals("job started"))
            {
                travelling.setEnabled(false);
                onSite.setEnabled(false);
                beginJob.setEnabled(false);
                beginJob.setChecked(true);
                jobComplete.setEnabled(true);
                returnVisit.setEnabled(true);

            }

            else if(jobToDisplay.getStatus().equals("complete"))
            {
                travelling.setEnabled(false);
                onSite.setEnabled(false);
                beginJob.setEnabled(false);
                jobComplete.setEnabled(false);
                returnVisit.setEnabled(false);
                jobComplete.setChecked(true);

            }

            else if(jobToDisplay.getStatus().equals("return required"))
            {
                travelling.setEnabled(false);
                onSite.setEnabled(false);
                beginJob.setEnabled(false);
                jobComplete.setEnabled(false);
                returnVisit.setEnabled(false);
                returnVisit.setChecked(true);

            }


            jobProgress.setOnCheckedChangeListener(JobDetails.this);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {



        Log.v("radiobuttons", "Group: " + group);
        if (checkedId == travelling.getId()) {
            jobToDisplay.setTravel_start(formatDate());
            jobToDisplay.setStatus("travelling");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);



        }

       else if (checkedId == onSite.getId()) {
            jobToDisplay.setTravel_end("2016-02-09 11:00:00");
            jobToDisplay.setStatus("on site");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);




        }

        else if (checkedId == beginJob.getId()) {
            jobToDisplay.setLabour_start("2016-02-09 11:00:00");
            jobToDisplay.setStatus("job started");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);

        }

        else if (checkedId == jobComplete.getId())
        {
            jobToDisplay.setLabour_end("2016-02-09 12:00:00");
            jobToDisplay.setStatus("complete");
            startActivityForResult(new Intent(this, CustomerSignOff.class), 0);


        }

        else if (checkedId == returnVisit.getId())
        {
            jobToDisplay.setLabour_end(formatDate());
            jobToDisplay.setStatus("return required");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);

        }







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {


                   Log.v("SIGNATURE","SIG LENGTH" +data.getByteArrayExtra("byteArray").length);
            String encodedBinary= Base64.encodeToString(data.getByteArrayExtra("byteArray"), Base64.DEFAULT);
                    jobToDisplay.setCust_sig(encodedBinary);
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);

        }
    }



    private String formatDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        Log.v("radiobuttons", "date being formatted " + dateFormat.format(date));
        return dateFormat.format(date);

    }

    private class EditJob extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public EditJob(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Updating job ....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Putting job");

                ConnectionAPI.editJob((String) params[0], (Job) params[1]);

            }

            catch(Exception e)
            {
                Log.v("REST","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(JobDetails.this, "Job Updated ", Toast.LENGTH_SHORT).show();
            if (dialog.isShowing())
                dialog.dismiss();

            new GetJob(JobDetails.this).execute("/jobs/" + jobToDisplay.getId());

        }
    }

}
