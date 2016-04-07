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

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Job;


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
        title.setText("Previous Jobs for " + getIntent().getStringExtra("customer_product_name"));
        jobListView =(ListView)findViewById(R.id.productHistoryListView);
        jobListView.setOnItemClickListener(this);

        new GetJobs(this).execute("/jobs?customer_product_id=" + getIntent().getStringExtra("customer_product_id"));




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

        Intent intent = new Intent(this, JobDetails.class);

        intent.putExtra("id",""+c.getId());
        startActivity(intent);
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

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

            ProductHistoryAdapter adapterForCalloutListView =new ProductHistoryAdapter(ProductHistory.this, jobs);
            jobListView.setAdapter(adapterForCalloutListView);

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

        }
    }



}

