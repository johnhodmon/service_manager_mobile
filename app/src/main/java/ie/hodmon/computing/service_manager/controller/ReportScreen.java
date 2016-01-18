package ie.hodmon.computing.service_manager.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.Report;


public class ReportScreen extends ClassForCommonAttributes /*implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener*/ {






    private TextView reportText;
    private TextView engineerName;
    private TextView productName;
    private TextView fault;
    private ListView listOfSparesOrdersView;
  
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
    private String galleryFilePath;
    private Spinner partDescriptionSpinner;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        addSpare=(ImageView)findViewById(R.id.report_add_part);
        addPartSave=(ImageView)findViewById(R.id.report_add_part_save);
        Intent i=getIntent();
        String jobId=i.getStringExtra("id");

     

        listOfSparesOrdersView =(ListView)findViewById(R.id.listOfSparesOrders);
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



    public void openPhotographs(View view)
    {
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
        new UpdateReport(this).execute("/reports/" + jobToDisplay.getReport().getId()+ ".json", jobToDisplay.getReport());


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













}
