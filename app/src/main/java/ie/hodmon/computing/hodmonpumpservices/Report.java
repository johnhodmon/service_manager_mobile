package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Report extends ClassForCommonAttributes implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {






    private TextView reportText;
    private TextView engineerName;
    private TextView productName;
    private TextView fault;
    private ListView listOfSparesOrdersView;
    private Callout calloutToWhichReportBelongs;
    private ImageView editReport;
    private ImageView addSpare;
    private Spinner quantitySpinner;
    private List<String> listOfPartsThisPump;
    private String partSelected;
    private SparesOrderItem sparesOrderItemSelected;
    private int quantitySelected;
    private String updateQuantity;
    private ImageView deleteSpare;
    private ImageView editSpare;
    private ImageView saveSymbol;
    private String description;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        calloutToWhichReportBelongs=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);
        listOfSparesOrdersView =(ListView)findViewById(R.id.listOfSparesOrders);
        reportText=(TextView)findViewById(R.id.reportText);
        reportText.setText(calloutToWhichReportBelongs.getReportText());

        List<SparesOrderItem>listOfSparesOrderItemsToBeUsedForAdapter=dbManager.getSparesOrderForReport(idOfCalloutToDisplayInDetail);
        listOfPartsThisPump=dbManager.getListOfPartsThisPump(dbManager.getSingleCallout(idOfCalloutToDisplayInDetail).getPumpNumber().substring(0,8));

        String toRemove="";
        List <String> listToRemove=new ArrayList<String>();
        for (SparesOrderItem soi:listOfSparesOrderItemsToBeUsedForAdapter)
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
        SparesOrderAdapter adapterForListOfSparesOrderView=new SparesOrderAdapter(this,listOfSparesOrderItemsToBeUsedForAdapter);
        listOfSparesOrdersView.setAdapter(adapterForListOfSparesOrderView);
        listOfSparesOrdersView.setOnItemClickListener(this);

        ArrayAdapter<String> adapterForSparePartsSpinner= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfPartsThisPump);
        adapterForSparePartsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editReport=(ImageView)findViewById(R.id.report_edit_report);
        addSpare=(ImageView)findViewById(R.id.report_add_part);
        engineerName=(TextView)findViewById(R.id.report_service_engineer);
        engineerName.setText(mapOfEmailsToNames.get(emailAddressEntered));
        productName=(TextView)findViewById(R.id.report_product_name);
        fault=(TextView)findViewById(R.id.report_fault);
        fault.setText(calloutToWhichReportBelongs.getReportedFault());
        productName.setText(calloutToWhichReportBelongs.getPumpNumber());
        System.out.println("id:" + calloutToWhichReportBelongs.getId());



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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        sparesOrderItemSelected=(SparesOrderItem)parent.getItemAtPosition(position);


    }

    public void editReport(View view)
    {
        finish();
        startActivity(new Intent(this,EditReport.class));

    }

    public void addSpare(View view)
    {


        dbManager.addSparesOrderItem(new SparesOrderItem(idOfCalloutToDisplayInDetail,dbManager.getPartNumber(partSelected),partSelected,quantitySelected));
        finish();

            startActivity(new Intent(this,Report.class));
    }

    public void deleteSpare(View view)
    {



        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();


        TextView descriptionTextViewThisRow=(TextView)rowContainingButton.getChildAt(0);
        String description=descriptionTextViewThisRow.getText().toString();

        dbManager.deleteSparesOrderItem(description);
        finish();
        startActivity(new Intent(this,Report.class));

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
            dbManager.editSparePartsItem(idOfCalloutToDisplayInDetail,Integer.parseInt(updateQuantity),description);
            finish();
            startActivity(new Intent(this,Report.class));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
