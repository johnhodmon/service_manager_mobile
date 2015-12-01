package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalloutForm extends ClassForCommonAttributes implements AdapterView.OnItemSelectedListener {

    private EditText calloutFormDate;
    private EditText calloutFormName;
    private EditText calloutFormStreet;
    private EditText calloutFormTown;
    private EditText calloutFormCounty;
    private EditText calloutFormPhone;
    private EditText calloutFormReportedFault;
    private Spinner calloutFormPumpSelectionSpinner;
    private List<String>listOfPumps=new ArrayList<String>();
    private Callout calloutToAdd;
    private String pumpSelected;
    private Button editCalloutButton;
    private Button addCalloutButton;
    private Callout calloutToEdit;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calloutToEdit=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);
        setContentView(R.layout.activity_callout_form);
        calloutFormDate=(EditText)findViewById(R.id.calloutFormDate);
        calloutFormName=(EditText)findViewById(R.id.calloutFormName);
        calloutFormStreet=(EditText)findViewById(R.id.calloutFormStreet);
        calloutFormTown=(EditText)findViewById(R.id.calloutFormTown);
        calloutFormCounty=(EditText)findViewById(R.id.calloutFormCounty);
        calloutFormPhone=(EditText)findViewById(R.id.calloutFormPhone);
        calloutFormReportedFault=(EditText)findViewById(R.id.calloutFormReportedFault);
        calloutFormPumpSelectionSpinner=(Spinner)findViewById((R.id.calloutFormPumpSelectionSpinner));
        calloutFormPumpSelectionSpinner.setOnItemSelectedListener(this);
        editCalloutButton=(Button)findViewById(R.id.editCalloutButton);
        addCalloutButton=(Button)findViewById(R.id.calloutFormAddButton);
        listOfPumps=dbManager.getPumps();
        ArrayAdapter<String> adapterForSpinner= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfPumps);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calloutFormPumpSelectionSpinner.setAdapter(adapterForSpinner);
        calloutToAdd=new Callout();


        if (formCalledFromEdit)
        {
            editCalloutButton.setVisibility(View.VISIBLE);
            addCalloutButton.setVisibility(View.INVISIBLE);

            calloutFormDate.setText(calloutToEdit.getDate());
            calloutFormName.setText(calloutToEdit.getCustomerName());
            calloutFormStreet.setText(calloutToEdit.getStreet());
            calloutFormTown.setText(calloutToEdit.getTown());
            calloutFormCounty.setText(calloutToEdit.getCounty());
            calloutFormPhone.setText(calloutToEdit.getPhoneNumber());
            calloutFormReportedFault.setText(calloutToEdit.getReportedFault());


            for (int i=0;i<calloutFormPumpSelectionSpinner.getAdapter().getCount();i++)
            {
               String s= (String)calloutFormPumpSelectionSpinner.getItemAtPosition(i);

                if (s.equals(calloutToEdit.getPumpNumber()))
                {
                    calloutFormPumpSelectionSpinner.setSelection(i);
                }
            }
        }

        else
        {
            editCalloutButton.setVisibility(View.INVISIBLE);
            addCalloutButton.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_form, menu);
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

    public void addCallout(View view)
    {
        Pattern p=Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
        Matcher m=p.matcher(calloutFormDate.getText().toString());
        Pattern p2=Pattern.compile("^[0-9]{6,10}$");
        Matcher m2=p2.matcher(calloutFormPhone.getText().toString());


        if(!m.find())
        {
            Toast.makeText(this,"Date format must be dd/mm/yyyy",Toast.LENGTH_LONG).show();
        }

        else if (!m2.find())
        {
            Toast.makeText(this,"Check phone number ",Toast.LENGTH_LONG).show();
        }

        else {

            calloutToAdd.setDate(calloutFormDate.getText().toString());
            calloutToAdd.setCustomerName(calloutFormName.getText().toString());
            calloutToAdd.setStreet(calloutFormStreet.getText().toString());
            calloutToAdd.setTown(calloutFormTown.getText().toString());
            calloutToAdd.setCounty(calloutFormCounty.getText().toString());
            calloutToAdd.setPhoneNumber(calloutFormPhone.getText().toString());
            calloutToAdd.setPumpNumber(pumpSelected);
            calloutToAdd.setReportedFault(calloutFormReportedFault.getText().toString());
            dbManager.addCallout(calloutToAdd);
            finish();
            startActivity(new Intent(this, CalloutScreen.class));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        pumpSelected=(String)parent.getSelectedItem();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void editCallout(View view)
    {
        Pattern p=Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
        Matcher m=p.matcher(calloutFormDate.getText().toString());
        Pattern p2=Pattern.compile("^[0-9]{6,10}$");
        Matcher m2=p2.matcher(calloutFormPhone.getText().toString());


        if(!m.find())
        {
            Toast.makeText(this,"Date format must be dd/mm/yyyy",Toast.LENGTH_LONG).show();
        }

        else if (!m2.find())
        {
            Toast.makeText(this,"Check phone number ",Toast.LENGTH_LONG).show();
        }

        else {

            calloutToAdd.setDate(calloutFormDate.getText().toString());
            calloutToAdd.setCustomerName(calloutFormName.getText().toString());
            calloutToAdd.setStreet(calloutFormStreet.getText().toString());
            calloutToAdd.setTown(calloutFormTown.getText().toString());
            calloutToAdd.setCounty(calloutFormCounty.getText().toString());
            calloutToAdd.setPhoneNumber(calloutFormPhone.getText().toString());
            calloutToAdd.setPumpNumber(pumpSelected);
            calloutToAdd.setReportedFault(calloutFormReportedFault.getText().toString());
            dbManager.addCallout(calloutToAdd);
            finish();
            startActivity(new Intent(this, CalloutScreen.class));
        }

    }
}


