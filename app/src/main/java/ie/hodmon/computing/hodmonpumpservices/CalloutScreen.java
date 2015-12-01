package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalloutScreen extends ClassForCommonAttributes implements AdapterView.OnItemClickListener {

    private ListView calloutListView;
    private TextView calloutDate;
    private TextView calloutScreenServiceMan;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callout_screen);
        calloutScreenServiceMan=(TextView)findViewById(R.id.calloutScreenServiceMan);


        calloutScreenServiceMan.setText(mapOfEmailsToNames.get(emailAddressEntered));
        calloutListView=(ListView)findViewById(R.id.calloutListView);
        calloutListView.setOnItemClickListener(this);
        Button changeDateButton=(Button)findViewById(R.id.changeDateButton);
        calloutDate=(TextView)findViewById(R.id.calloutDateLabel);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        if(notToday) {
            calloutDate.setText(dateToShow);

        }

        else
        {
            calloutDate.setText(formattedDate);
        }

        CalloutsAdapter adapterForCalloutListView =new CalloutsAdapter(this,dbManager.getCallouts(calloutDate.getText().toString()));
        calloutListView.setAdapter(adapterForCalloutListView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_callout_screen, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.addNewCalloutMenuItem)
        {
            startActivity(new Intent(this,CalloutForm.class
            ));
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Callout c=(Callout)parent.getItemAtPosition(position);
        idOfCalloutToDisplayInDetail=c.getId();
        startActivity(new Intent(this,CalloutDetails.class));
    }

    public void changeDate(View view)
    {

        startActivity(new Intent(this,PickDate.class));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }
}

