package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.CustomerProduct;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Report;


public class ProductHistory extends ClassForCommonAttributes implements AdapterView.OnItemClickListener
{

    private ListView jobListView;
    private List<Job> jobs;
    private TextView title;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_history);
        Log.v("check email", "email: " + engineerEmail);
        title=(TextView)findViewById(R.id.jobHistoryTitle);
        title.setText("Previous Jobs for this Product");
        jobListView =(ListView)findViewById(R.id.productHistoryListView);
        jobListView.setOnItemClickListener(this);
        Log.v("productHistory","getting jobs for "+getIntent().getStringExtra("customer_product_id"));
        new GetCustomerProduct(this).execute("/customer_products/"+getIntent().getStringExtra("customer_product_id"));




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

        Intent intent = new Intent(this, ReportScreen.class);

        intent.putExtra("id",""+c.getId());
        startActivity(intent);
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }







    private class GetCustomerProduct extends AsyncTask<String, Void, CustomerProduct> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetCustomerProduct(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving History");
            this.dialog.show();
        }

        @Override
        protected CustomerProduct doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Jobs");
                return (CustomerProduct) ConnectionAPI.getCustomerProduct((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(CustomerProduct result) {
            super.onPostExecute(result);

            jobs= Arrays.asList(result.getJobs());

            ProductHistoryAdapter adapterForCalloutListView =new ProductHistoryAdapter(ProductHistory.this, jobs);
            jobListView.setAdapter(adapterForCalloutListView);

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

        }
    }



}

