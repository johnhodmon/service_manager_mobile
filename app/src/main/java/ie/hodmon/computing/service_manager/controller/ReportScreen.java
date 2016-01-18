package ie.hodmon.computing.service_manager.controller;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.JobPart;


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

    }















}
