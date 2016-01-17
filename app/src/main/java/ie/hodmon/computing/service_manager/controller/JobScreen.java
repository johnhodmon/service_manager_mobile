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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Customer;
import ie.hodmon.computing.service_manager.model.CustomerProduct;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Product;


public class JobScreen extends ClassForCommonAttributes implements AdapterView.OnItemClickListener {

    private ListView calloutListView;
    private List<Job> jobs;
    private ImageView mapButton;
    private List<Customer>customers;
    private List<CustomerProduct>customerproducts;
    private List<Product>products;









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
       new GetJobs(this).execute("/jobs.json");
        new GetProducts(this).execute("/products.json");
        new GetCustomers(this).execute("/customers.json");
        new GetCustomerProducts(this).execute("/customerproducts.json");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_screen, menu);
        return true;
    }








    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Job c=(Job)parent.getItemAtPosition(position);
        idOfJobToDisplayInDetail =c.getId();
        startActivity(new Intent(this,JobDetails.class));
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

        /*for(Job c: jobs)
        {
            args.putParcelable(c.getCustomerName(),c.getLatLng());
        }*/
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

            if (dialog.isShowing())
            {
               dialog.dismiss();
            }

        }
    }


    private class GetCustomers extends AsyncTask<String, Void, List<Customer>> {


        protected Context context;

        public GetCustomers(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Customer> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Customers");
                return (List<Customer>) ConnectionAPI.getCustomers((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Customer> result) {
            super.onPostExecute(result);

            customers=result;


        }
    }


    private class GetCustomerProducts extends AsyncTask<String, Void, List<CustomerProduct>> {


        protected Context context;

        public GetCustomerProducts(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<CustomerProduct> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Customer Products");
                return (List<CustomerProduct>) ConnectionAPI.getCustomerProducts((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CustomerProduct> result) {
            super.onPostExecute(result);

            customerproducts=result;


        }
    }

    private class GetProducts extends AsyncTask<String, Void, List<Product>> {


        protected Context context;

        public GetProducts(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Product> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Customer Products");
                return (List<Product>) ConnectionAPI.getProducts((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> result) {
            super.onPostExecute(result);

            products=result;


        }
    }
}

