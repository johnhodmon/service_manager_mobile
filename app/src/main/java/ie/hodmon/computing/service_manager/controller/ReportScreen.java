package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.R;

import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.JobPartWithPartNumber;
import ie.hodmon.computing.service_manager.model.PartListWithPartNumber;
import ie.hodmon.computing.service_manager.model.Report;


public class ReportScreen extends ClassForCommonAttributes implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
{




    private TextView reportText;
    private TextView productName;
    private TextView fault;
    private ListView listOfJobPartsView;
    private ImageView editReport;
    private ImageView addJobPart;
    private Spinner orderPartQuantitySpinner;
    private ImageView editJobPart;
    private ImageView saveJobPartEdit;
    private ImageView deleteJobPart;
    private ImageView saveSymbol;
    private EditText editText;
    private ImageView addJobPartSave;
    private Spinner partDescriptionSpinner;
    private Spinner editJobPartQuantitySpinner;
    private PartsUsedAdapter adapterJobParts;
    private static final int SPEECH_REQUEST_CODE = 1;
     private PartListSpinnerAdapter  partDescriptionAdapter;
    private JobPart jobPartToPost;
    private String updatedQuantity;
    private ImageView barcodeIcon;
    private List<PartListWithPartNumber>partListsWithPartNumber;
    private List<JobPartWithPartNumber> jobPartsWithPartNumber;
    private List <JobPart>jobParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Integer[]quantity={1,2,3,4,5,6,7,8,9,10};
        addJobPart =(ImageView)findViewById(R.id.report_add_part);
        addJobPartSave =(ImageView)findViewById(R.id.report_add_part_save);
        Intent i=getIntent();
        jobParts=new ArrayList<JobPart>();
        jobPartsWithPartNumber=new ArrayList<JobPartWithPartNumber>();
        partListsWithPartNumber=new ArrayList<PartListWithPartNumber>();

        jobPartToPost=new JobPart();
        Intent intent=getIntent();
        intent.getExtras();
        barcodeIcon=(ImageView)findViewById(R.id.barcode_icon);




        ArrayAdapter<Integer> editQtyArrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,quantity);

      //  Log.v("REST","job parts"+jobToDisplay.getJob_parts()[0]);

        listOfJobPartsView =(ListView)findViewById(R.id.listOfSparesOrders);
        reportText=(TextView)findViewById(R.id.reportText);
        reportText.setText(jobToDisplay.getReport().getEngineer_report());
        editReport=(ImageView)findViewById(R.id.report_edit_report);
        addJobPart =(ImageView)findViewById(R.id.report_add_part);
        partDescriptionSpinner=(Spinner)findViewById(R.id.report_part_spinner);
        orderPartQuantitySpinner=(Spinner)findViewById(R.id.report_order_part_quantity);

        ArrayAdapter<Integer> quantityArrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,quantity);
        orderPartQuantitySpinner.setAdapter(quantityArrayAdapter);
        partDescriptionAdapter=new PartListSpinnerAdapter(this,partListsWithPartNumber);
        partDescriptionSpinner.setAdapter(partDescriptionAdapter);
        partDescriptionSpinner.setOnItemSelectedListener(this);
        orderPartQuantitySpinner.setOnItemSelectedListener(this);
        productName=(TextView)findViewById(R.id.report_product_name);
        fault=(TextView)findViewById(R.id.report_fault);
        fault.setText(jobToDisplay.getReported_fault());
        productName.setText(jobToDisplay.getManufacturer().getName() + " " + jobToDisplay.getProduct().getProduct_number());
        System.out.println("id:" + jobToDisplay.getId());
        editText=(EditText)findViewById(R.id.report_edit_text);
        saveSymbol=(ImageView)findViewById(R.id.report_save);
        adapterJobParts =new PartsUsedAdapter(ReportScreen.this,jobPartsWithPartNumber);
        listOfJobPartsView.setAdapter(adapterJobParts);
        new GetJob(this).execute("/jobs/"+jobToDisplay.getId());



    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }

    public void addScannedPart()
    {
        if(!partListsWithPartNumber.isEmpty())
        {
            int partId=0;
            for (PartListWithPartNumber pl: partListsWithPartNumber)
            {
                Log.v("scanner","part number:"+pl.getPart_number());
                if (pl.getPart_number().equals(getIntent().getStringExtra("barcode")))
                {
                    partId=pl.getPart_id();
                }
            }

            if(partId!=0)
            {
                jobPartToPost.setPart_id(partId);

                jobPartToPost.setJob_id(jobToDisplay.getId());
                jobPartToPost.setQuantity(1);
                Log.v("scanner","job id:"+jobToDisplay.getId());

                new addJobPart(this).execute("/jobs/" + jobToDisplay.getId(), jobPartToPost);
            }

            else
            {
                Toast.makeText(this,"Part number not found in database",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Part list not yet loaded, check internet connection",Toast.LENGTH_LONG).show();
        }

        getIntent().removeExtra("barcode");


    }

    public void deleteJobPart(View view)
    {
        View row=(View)view.getParent();
        ListView lv=(ListView)row.getParent();
        JobPartWithPartNumber jpp=jobPartsWithPartNumber.get(lv.getPositionForView(row));
        onDeleteJobPart(jpp);

    }

    public void onDeleteJobPart(final JobPartWithPartNumber jp) {
        final String jpId = ""+jp.getJobPartId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete This Part from Parts Used?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setMessage("Are you sure?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new DeleteJobPart(ReportScreen.this).execute("/job_parts/"+jpId);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

public void addJobPart (View view)
{
    addJobPart.setVisibility(View.INVISIBLE);
    barcodeIcon.setVisibility(View.INVISIBLE);
    addJobPartSave.setVisibility(View.VISIBLE);
    partDescriptionSpinner.setVisibility(View.VISIBLE);
    orderPartQuantitySpinner.setVisibility(View.VISIBLE);
    listOfJobPartsView.setVisibility(View.INVISIBLE);
    partDescriptionSpinner.performClick();


}

    public void saveJobPart(View view)
    {
        addJobPart.setVisibility(View.VISIBLE);
        addJobPartSave.setVisibility(View.INVISIBLE);
        partDescriptionSpinner.setVisibility(View.INVISIBLE);
        orderPartQuantitySpinner.setVisibility(View.INVISIBLE);
        listOfJobPartsView.setVisibility(View.VISIBLE);
        barcodeIcon.setVisibility(View.VISIBLE);
        new addJobPart(this).execute("/jobs/" + jobToDisplay.getId(), jobPartToPost);

    }

    public void editJobPart(View view) {
        RelativeLayout rowContainingButton = (RelativeLayout) view.getParent();
        ListView lv=(ListView)rowContainingButton.getParent();
        JobPartWithPartNumber jpp=jobPartsWithPartNumber.get(lv.getPositionForView(rowContainingButton));
        editJobPartQuantitySpinner = (Spinner) rowContainingButton.getChildAt(2);
        editJobPartQuantitySpinner.setOnItemSelectedListener(this);
        TextView quantityTextView = (TextView) rowContainingButton.getChildAt(1);
        TextView descriptionTextView = (TextView) rowContainingButton.getChildAt(0);
        saveJobPartEdit = (ImageView) rowContainingButton.getChildAt(5);

        deleteJobPart = (ImageView) rowContainingButton.getChildAt(3);
        editJobPart = (ImageView) rowContainingButton.getChildAt(4);

        JobPart jp=new JobPart();
        for(JobPart j:jobParts)
        {
            if(j.getId()==jpp.getJobPartId())
            {
                jp=j;
            }
        }


        if (editJobPartQuantitySpinner.getVisibility() == View.INVISIBLE) {

            editJobPartQuantitySpinner.setVisibility(View.VISIBLE);
            editJobPartQuantitySpinner.performClick();
            quantityTextView.setVisibility(View.INVISIBLE);
            deleteJobPart.setVisibility(View.INVISIBLE);
            editJobPart.setVisibility(View.INVISIBLE);
            saveJobPartEdit.setVisibility(View.VISIBLE);

        }
        else
        {
            editJobPartQuantitySpinner.setVisibility(View.INVISIBLE);
            quantityTextView.setVisibility(View.VISIBLE);
            deleteJobPart.setVisibility(View.VISIBLE);
            editJobPart.setVisibility(View.VISIBLE);
            saveJobPartEdit.setVisibility(View.INVISIBLE);
            jp.setQuantity(Integer.parseInt(updatedQuantity));
            new editJobPartQuanity(this).execute("/job_parts",jp);

        }
    }



    public void openPhotographs(View view) {
        startActivity(new Intent(this, ReportPhotos.class));
    }

    public void editReport(View view)
    {
        editText.setVisibility(View.VISIBLE);
        editReport.setVisibility(View.INVISIBLE);
        saveSymbol.setVisibility(View.VISIBLE);
        reportText.setVisibility(View.INVISIBLE);
        editText.setText(reportText.getText().toString());
        editText.hasFocus();
    }

    public void scanBarcode(View view)
    {
        startActivity(new Intent(this,BarcodeScanner.class));
    }

    public void saveReport(View view)
    {
        editReport.setVisibility(View.VISIBLE);
        saveSymbol.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        reportText.setVisibility(View.VISIBLE);
        reportText.setText(editText.getText().toString());
        jobToDisplay.getReport().setEngineer_report(reportText.getText().toString());
        new UpdateReport(this).execute("/reports/" + jobToDisplay.getReport().getId(), jobToDisplay.getReport());


    }

    public void recordReport(View view)
    {
            displaySpeechRecognizer();
    }




    private class UpdateReport extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public UpdateReport(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Updating report....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Putting report");

                ConnectionAPI.editReport((String) params[0], (Report) params[1]);

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
            Toast.makeText(ReportScreen.this,"Report Updated", Toast.LENGTH_LONG).show();
            if (dialog.isShowing())
                dialog.dismiss();

        }
    }

    private class editJobPartQuanity extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public editJobPartQuanity(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Updating job part....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Putting jobpart");

                ConnectionAPI.editJobPartQuantity((String) params[0], (JobPart) params[1]);

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
            Toast.makeText(ReportScreen.this,"Job Parts Updated", Toast.LENGTH_LONG).show();
            if (dialog.isShowing())
                dialog.dismiss();

            new GetJob(ReportScreen.this).execute("/jobs/"+jobToDisplay.getId());

        }
    }

    private class addJobPart extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public addJobPart(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Adding Job Part....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Posting job part");

                ConnectionAPI.addJobPart((String) params[0], (JobPart) params[1]);

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
            if(result!=null) {
                Log.v("REST", "Post JobPart result: "+result);
            }
            Toast.makeText(ReportScreen.this,"Job Part Added", Toast.LENGTH_LONG).show();
            new GetJob(ReportScreen.this).execute("/jobs/"+jobToDisplay.getId());
            if (dialog.isShowing())
                dialog.dismiss();

        }
    }





    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            reportText.setText(spokenText);
            editReport(editReport);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }








    private class DeleteJobPart extends AsyncTask<String, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public DeleteJobPart(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Part from Job");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return (String) ConnectionAPI.delete((String) params[0]);
            } catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            String s = result;
            Log.v("REST", "DELETE REQUEST : " + s);

            new GetJob(ReportScreen.this).execute("/jobs/"+jobToDisplay.getId());

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }


    private class GetJob extends AsyncTask<String, Void,Job> {


        protected Context context;

        public GetJob(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
            jobPartsWithPartNumber.clear();
            partDescriptionAdapter.notifyDataSetChanged();
            adapterJobParts.notifyDataSetChanged();
            partListsWithPartNumber.clear();

            jobToDisplay = result;

            Log.v("fill_lists","job parts length: "+jobToDisplay.getJob_parts().length);
            Log.v("fill_lists","job parts with numbers length: "+ jobToDisplay.getJob_parts_with_part_numbers().length);
            Log.v("fill_lists","part list with nums length: "+  jobToDisplay.getPart_lists_with_part_numbers().length);



            if (jobToDisplay.getJob_parts().length != 0)
            {
                for(JobPart jp:jobToDisplay.getJob_parts())
                {
                    jobParts.add(jp);
                }

            }


                if (jobToDisplay.getPart_lists_with_part_numbers().length != 0) {

                    Log.v("fill_lists","attempting to fill partlist with numbers list");
                    for (PartListWithPartNumber plp : jobToDisplay.getPart_lists_with_part_numbers()) {
                        partListsWithPartNumber.add(plp);
                        Log.v("fill_lists","adding part with id to partlists: "+plp.getPart_id());
                    }

                    partDescriptionAdapter.notifyDataSetChanged();

                }

                if (jobToDisplay.getJob_parts_with_part_numbers().length != 0)
                {
                    Log.v("fill_lists","attempting to fill jobpart with numbers list");
                    for (JobPartWithPartNumber jpp : jobToDisplay.getJob_parts_with_part_numbers()) {
                        jobPartsWithPartNumber.add(jpp);
                        Log.v("fill_lists", "adding jobpart partlists. jobpartid:: " + jpp.getJobPartId());
                    }

                    adapterJobParts.notifyDataSetChanged();

                }


                if (getIntent().getStringExtra("barcode")!=null) {
                addScannedPart();

            }



        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {


        if(parent==orderPartQuantitySpinner)
        {
            jobPartToPost.setQuantity((int) parent.getItemAtPosition(position));
            Log.v("spinner","qty: "+parent.getItemAtPosition(position));

        }

        else if((parent==partDescriptionSpinner))
        {

            Log.v("spinner","description: "+parent.getItemAtPosition(position));

            PartListWithPartNumber plp=(PartListWithPartNumber)parent.getItemAtPosition(position);
            Log.v("spinner","desc: "+plp.getDescription());
            Log.v("spinner","partId: "+plp.getPart_id());

            jobPartToPost.setPart_id(plp.getPart_id());


        }

        else if((parent==editJobPartQuantitySpinner))
        {
            Log.v("spinner", "updated quantity: " + (String) parent.getItemAtPosition(position));
            updatedQuantity=(String) parent.getItemAtPosition(position);
            //new GetJob(this).execute("jobs/"+jobToDisplay.getId());

        }



        jobPartToPost.setJob_id(jobToDisplay.getId());



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
