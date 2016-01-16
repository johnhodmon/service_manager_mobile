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






   /* private TextView reportText;
    private TextView engineerName;
    private TextView productName;
    private TextView fault;
    private ListView listOfSparesOrdersView;
    private Job jobToWhichReportBelongs;
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
        jobToWhichReportBelongs =dbManager.getSingleCallout(idOfJobToDisplayInDetail);
        listOfSparesOrdersView =(ListView)findViewById(R.id.listOfSparesOrders);
        reportText=(TextView)findViewById(R.id.reportText);
        reportText.setText(jobToWhichReportBelongs.getReportText());
        List<JobPart>listOfSparesOrderItemsToBeUsedForAdapter=dbManager.getSparesOrderForReport(idOfJobToDisplayInDetail);
        listOfPartsThisPump=dbManager.getListOfPartsThisPump(dbManager.getSingleCallout(idOfJobToDisplayInDetail).getPumpNumber().substring(0,8));
        String toRemove="";
        List <String> listToRemove=new ArrayList<String>();
        for (JobPart soi:listOfSparesOrderItemsToBeUsedForAdapter)
        {

            for(String s: listOfPartsThisPump)
            {

                if (s.equals(soi.getPartDescription()))
                {
                   listToRemove.add(s);
                }
            }

        }
        listOfPartsThisPump.removeAll(listToRemove);
        listOfPartsThisPump.removeAll(Arrays.asList("", null));
        partDescriptionSpinner=(Spinner)findViewById(R.id.report_part_spinner);
        String array[] = listOfPartsThisPump.toArray(new String[listOfPartsThisPump.size()]);
        ArrayAdapter<String> ad =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        partDescriptionSpinner.setAdapter(ad);
        orderPartQuantitySpinner=(Spinner)findViewById(R.id.report_order_part_quantity);
        String qty[]={"1","2","3","4","5","6","7","8","9"};
        ArrayAdapter<String> ad1 =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,qty);
        orderPartQuantitySpinner.setAdapter(ad1);

        SparesOrderAdapter adapterForListOfSparesOrderView=new SparesOrderAdapter(this,listOfSparesOrderItemsToBeUsedForAdapter);
        listOfSparesOrdersView.setAdapter(adapterForListOfSparesOrderView);
        listOfSparesOrdersView.setOnItemClickListener(this);




        editReport=(ImageView)findViewById(R.id.report_edit_report);
        addSpare=(ImageView)findViewById(R.id.report_add_part);


        productName=(TextView)findViewById(R.id.report_product_name);
        fault=(TextView)findViewById(R.id.report_fault);
        fault.setText(jobToWhichReportBelongs.getReportedFault());
        productName.setText(jobToWhichReportBelongs.getPumpNumber());
        System.out.println("id:" + jobToWhichReportBelongs.getId());
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



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        jobPartSelected =(JobPart)parent.getItemAtPosition(position);


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
        jobToWhichReportBelongs.setReportText(editText.getText().toString());
        reportText.setText(editText.getText().toString());
        dbManager.editCallout(jobToWhichReportBelongs);
    }

    public void addSpare(View view)
    {
        listOfSparesOrdersView.setVisibility(View.INVISIBLE);
        addSpare.setVisibility(View.INVISIBLE);
        orderPartQuantitySpinner.setVisibility(View.VISIBLE);
        partDescriptionSpinner.performClick();
        partDescriptionSpinner.setVisibility(View.VISIBLE);
        addPartSave.setVisibility(View.VISIBLE);

    }

    public void saveAddedPart(View view)
    {
        orderPartQuantitySpinner.setVisibility(View.INVISIBLE);
        partDescriptionSpinner.setVisibility(View.INVISIBLE);
        listOfSparesOrdersView.setVisibility(View.VISIBLE);
        addPartSave.setVisibility(View.INVISIBLE);
        addSpare.setVisibility(View.VISIBLE);
        String description=(String)partDescriptionSpinner.getSelectedItem();
        int qty=Integer.parseInt((String)orderPartQuantitySpinner.getSelectedItem());
        dbManager.addSparesOrderItem(new JobPart(idOfJobToDisplayInDetail,
                dbManager.getPartNumber(description), description, qty));
        finish();

        startActivity(new Intent(this, ReportScreen.class));

    }

    public void deleteSpare(View view)
    {



        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();


        TextView descriptionTextViewThisRow=(TextView)rowContainingButton.getChildAt(0);
        String description=descriptionTextViewThisRow.getText().toString();

        dbManager.deleteSparesOrderItem(description);
        finish();
        startActivity(new Intent(this,ReportScreen.class));

    }






    public void editSpare(View view)
    {

        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();

        quantitySpinner=(Spinner)rowContainingButton.getChildAt(2);
        quantitySpinner.setOnItemSelectedListener(this);
        TextView quantityTextView=(TextView)rowContainingButton.getChildAt(1);
        TextView descriptionTextView=(TextView)rowContainingButton.getChildAt(0);
        description=descriptionTextView.getText().toString();
        saveSymbol=(ImageView)rowContainingButton.getChildAt(5);

        deleteSpare=(ImageView)rowContainingButton.getChildAt(3);
        editSpare=(ImageView)view;



        if(quantitySpinner.getVisibility()==View.INVISIBLE) {

            quantitySpinner.setVisibility(View.VISIBLE);
            quantitySpinner.performClick();
            quantityTextView.setVisibility(View.INVISIBLE);
            deleteSpare.setVisibility(View.INVISIBLE);
            editSpare.setVisibility(View.INVISIBLE);
            //saveSymbol.setVisibility(View.VISIBLE);

        }

        else
        {
            quantitySpinner.setVisibility(View.INVISIBLE);
            quantityTextView.setVisibility(View.VISIBLE);
            deleteSpare.setVisibility(View.VISIBLE);
            editSpare.setVisibility(View.VISIBLE);
           // saveSymbol.setVisibility(View.INVISIBLE);


        }










    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(parent==quantitySpinner)
        {
            updateQuantity=(String)parent.getSelectedItem();
            dbManager.editSparePartsItem(idOfJobToDisplayInDetail,Integer.parseInt(updateQuantity),description);
            finish();
            startActivity(new Intent(this,ReportScreen.class));

        }

        else if( parent==partDescriptionSpinner)
        {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

*/
}
