package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Customer;
import ie.hodmon.computing.service_manager.model.CustomerProduct;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Product;
import ie.hodmon.computing.service_manager.push_notifications.QuickstartPreferences;
import ie.hodmon.computing.service_manager.push_notifications.RegistrationIntentService;


public class JobScreen extends ClassForCommonAttributes implements AdapterView.OnItemClickListener
{

    private ListView calloutListView;
    private List<Job> jobs;
    private ImageView mapButton;
    private List<Customer>customers;
    private List<CustomerProduct>customerproducts;
    private List<Product>products;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_screen);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

            }
        };

        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        Log.v("check email", "email: " + engineerEmail);
        mapButton = (ImageView)findViewById(R.id.directions_icon);

        calloutListView=(ListView)findViewById(R.id.calloutListView);
        calloutListView.setOnItemClickListener(this);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.job_screen_swipe_refresh_layout);


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
       new GetJobs(this).execute("/jobs.json");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetJobs(JobScreen.this).execute("/jobs");
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_screen, menu);
        return true;
    }




    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Job c=(Job)parent.getItemAtPosition(position);

        Intent intent = new Intent(this, JobDetails.class);

        intent.putExtra("id",""+c.getId());
        startActivity(intent);
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

        for(Job j: jobs)
        {






            LatLng latLng=  convertStringToLatLng(j.getCustomer().getLat_lng());
            args.putParcelable(j.getCustomer().getName(),latLng);
        }
        args.putInt("zoom",10);


        intent.putExtra("bundle",args);
        startActivity(intent);
    }




    private class GetJobs extends AsyncTask<String, Void, List<Job>> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetJobs(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Jobs");
            this.dialog.show();
        }

        @Override
        protected List<Job> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Jobs");
                return (List<Job>) ConnectionAPI.getJobs((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Job> result) {
            super.onPostExecute(result);

           jobs=result;
            if (jobs.isEmpty())
            {
                mapButton.setVisibility(View.INVISIBLE);
            }
            JobsAdapter adapterForCalloutListView =new JobsAdapter(JobScreen.this, jobs);
            calloutListView.setAdapter(adapterForCalloutListView);
            mSwipeRefreshLayout.setRefreshing(false);
            if (dialog.isShowing())
            {
               dialog.dismiss();
            }

        }
    }



}

