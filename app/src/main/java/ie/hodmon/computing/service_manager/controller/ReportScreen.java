package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.Part;
import ie.hodmon.computing.service_manager.model.Report;


public class ReportScreen extends ClassForCommonAttributes /*implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener*/ {






    private TextView reportText;
    private TextView engineerName;
    private TextView productName;
    private TextView fault;
    private ListView listOfJobPartsView;
    private ImageView editReport;
    private ImageView addSpare;
    private Spinner quantitySpinner;
    private Spinner orderPartQuantitySpinner;
    private List<String> listOfPartsThisPump;
    private String partSelected;
    private JobPart jobPartSelected;
    private int quantitySelected;
    private String updateQuantity;
    private ImageView deleteSpare;
    private ImageView editSpare;
    private ImageView saveSymbol;
    private String description;
    private EditText editText;
    private ImageView addPartSave;
    private ImageView recordReportIcon;
    private String galleryFilePath;
    private Spinner partDescriptionSpinner;
    private List<JobPart>jobParts;
    private int job_part_id;
    private int job_part_quantity;
    private int part_id;
    private static final int SPEECH_REQUEST_CODE = 1;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        addSpare=(ImageView)findViewById(R.id.report_add_part);
        addPartSave=(ImageView)findViewById(R.id.report_add_part_save);
        Intent i=getIntent();
        String jobId=i.getStringExtra("id");
        jobParts=new ArrayList<JobPart>();
        Log.v("REST","job parts"+jobToDisplay.getJob_parts()[0]);

        listOfJobPartsView =(ListView)findViewById(R.id.listOfSparesOrders);
        reportText=(TextView)findViewById(R.id.reportText);
        reportText.setText(jobToDisplay.getReport().getEngineer_report());
        editReport=(ImageView)findViewById(R.id.report_edit_report);
        addSpare=(ImageView)findViewById(R.id.report_add_part);


        productName=(TextView)findViewById(R.id.report_product_name);
        fault=(TextView)findViewById(R.id.report_fault);
        fault.setText(jobToDisplay.getReported_fault());
        productName.setText(jobToDisplay.getManufacturer().getName()+" "+jobToDisplay.getProduct().getProduct_number());
        System.out.println("id:" + jobToDisplay.getId());
        editText=(EditText)findViewById(R.id.report_edit_text);
        saveSymbol=(ImageView)findViewById(R.id.report_save);
        recordReportIcon=(ImageView)findViewById(R.id.record_report_icon);
        if(jobToDisplay.getJob_parts().length!=0)
        {
            for (JobPart jp:jobToDisplay.getJob_parts())
            {
                part_id=jp.getPart_id();
                job_part_id=jp.getId();
                job_part_quantity=jp.getQuantity();
                new GetPart(this).execute("/parts/" + job_part_id);
                PartsUsedAdapter adapterJobParts =new PartsUsedAdapter(this,jobParts);
                listOfJobPartsView.setAdapter(adapterJobParts);
            }
        }


    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
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

    public void saveReport(View view)
    {
        editReport.setVisibility(View.VISIBLE);
        saveSymbol.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        reportText.setVisibility(View.VISIBLE);
        reportText.setText(editText.getText().toString());
        jobToDisplay.getReport().setEngineer_report(reportText.getText().toString());
        new UpdateReport(this).execute("/reports/" + jobToDisplay.getReport().getId() + ".json", jobToDisplay.getReport());


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



    private class GetPart extends AsyncTask<String, Void,Part> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetPart(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Part doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Job");
                return (Part) ConnectionAPI.getPart((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Part result) {
            super.onPostExecute(result);
            Part p=result;
           jobParts.add(new JobPart(job_part_id,jobToDisplay.getId(),part_id,job_part_quantity,p.getPart_number(),p.getDescription()));
            listOfJobPartsView.refreshDrawableState();



        }
    }



}
