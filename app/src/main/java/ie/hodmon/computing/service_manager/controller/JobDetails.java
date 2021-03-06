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
import android.widget.ImageView;
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
    private TextView jobDetailsProductDescription;
    private TextView jobDetailsReportedFault;
    private RadioGroup jobProgress;
    private RadioButton travelling;
    private RadioButton onSite;
    private RadioButton beginJob;
    private RadioButton jobComplete;

    private ImageView jobHistoryLauncher;



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
        jobDetailsProductDescription = (TextView) findViewById(R.id.job_details_product_description);
        jobDetailsReportedFault = (TextView) findViewById(R.id.job_details_fault);
        jobDetailsPhone = (TextView) findViewById(R.id.job_details_phone_number);
        travelling = (RadioButton) findViewById(R.id.rb_travelling);
        jobProgress = (RadioGroup) findViewById(R.id.rg_job_progress);
        jobHistoryLauncher=(ImageView)findViewById(R.id.access_job_history);

        onSite = (RadioButton) findViewById(R.id.rb_on_site);
        beginJob = (RadioButton) findViewById(R.id.rb_start_job);
        jobComplete = (RadioButton) findViewById(R.id.rb_job_complete);

        new GetJob(this).execute("/jobs/" + jobId);


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
        intent.putExtra("id",""+jobToDisplay.getId());


        startActivity(intent);

    }

    public void productHistory(View view)
    {
        Intent jobHistoryIntent=new Intent(this,ProductHistory.class);
        jobHistoryIntent.putExtra("customer_product_name",""+jobToDisplay.getProduct().getDescription());
        jobHistoryIntent.putExtra("customer_product_id",""+jobToDisplay.getProduct().getId());
        finish();
        startActivity(jobHistoryIntent);

    }

    public void getDirections(View view) {

        LatLng jobLoc= convertStringToLatLng(jobToDisplay.getCustomer().getLat_lng());

        Uri gmmIntentUri = Uri.parse("google.navigation:q="+jobLoc.latitude+","+jobLoc.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }

        else
        {
            Toast.makeText(this,"There was a problem launching Google Maps",Toast.LENGTH_LONG).show();

        }

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
            if(jobToDisplay.getStatus().equals("not logged in"))
            {
                startActivity(new Intent(JobDetails.this,LoginScreen.class));
            }

            else {
                Log.v("REST", " RESULT : " + result.toString());
                jobDetailsName.setText(jobToDisplay.getCustomer().getName());
                jobDetailsStreet.setText(jobToDisplay.getCustomer().getStreet());
                jobDetailsTown.setText(jobToDisplay.getCustomer().getTown());
                jobDetailsProduct.setText(jobToDisplay.getManufacturer().getName() + " " + jobToDisplay.getProduct().getProduct_number());
                jobDetailsProductDescription.setText(jobToDisplay.getProduct().getDescription());
                jobDetailsReportedFault.setText(jobToDisplay.getReported_fault());
                jobDetailsPhone.setText(jobToDisplay.getCustomer().getPhone());


                if (jobToDisplay.getStatus().equals("allocated")) {

                    travelling.setEnabled(true);
                    onSite.setEnabled(false);
                    beginJob.setEnabled(false);
                    jobComplete.setEnabled(false);


                } else if (jobToDisplay.getStatus().equals("travelling")) {
                    travelling.setEnabled(false);
                    travelling.setChecked(true);
                    onSite.setEnabled(true);
                    beginJob.setEnabled(false);
                    jobComplete.setEnabled(false);


                } else if (jobToDisplay.getStatus().equals("on site")) {
                    travelling.setEnabled(false);
                    onSite.setEnabled(false);
                    onSite.setChecked(true);
                    beginJob.setEnabled(true);
                    jobComplete.setEnabled(false);



                } else if (jobToDisplay.getStatus().equals("job started")) {
                    travelling.setEnabled(false);
                    onSite.setEnabled(false);
                    beginJob.setEnabled(false);
                    beginJob.setChecked(true);
                    jobComplete.setEnabled(true);


                } else if (jobToDisplay.getStatus().equals("complete")) {
                    travelling.setEnabled(false);
                    onSite.setEnabled(false);
                    beginJob.setEnabled(false);
                    jobComplete.setEnabled(false);

                    jobComplete.setChecked(true);

                }


                jobProgress.setOnCheckedChangeListener(JobDetails.this);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        }
    }

    @Override
    public void onCheckedChanged (RadioGroup group,int checkedId) {



        Log.v("radiobuttons", "Group: " + group);
        if (checkedId == travelling.getId()) {
            jobToDisplay.setTravel_start(formatDate());
            jobToDisplay.setStatus("travelling");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);



        }

       else if (checkedId == onSite.getId()) {
            jobToDisplay.setTravel_end("2016-04-16 19:00:00");
            jobToDisplay.setStatus("on site");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);




        }

        else if (checkedId == beginJob.getId()) {
            jobToDisplay.setLabour_start("2016-04-16 19:00:00");
            jobToDisplay.setStatus("job started");
            new EditJob(this).execute("/jobs/"+jobToDisplay.getId(),jobToDisplay);

        }

        else if (checkedId == jobComplete.getId())
        {
            jobToDisplay.setLabour_end("2016-04-16 20:00:00");
            jobToDisplay.setStatus("complete");
            startActivityForResult(new Intent(this, CustomerSignOff.class), 0);


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
