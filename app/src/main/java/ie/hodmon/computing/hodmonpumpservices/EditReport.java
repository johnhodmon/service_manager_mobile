package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditReport extends ClassForCommonAttributes
{
    private Button editReportSaveButton;
    private Callout calloutToWhichReportBelongs;
    private EditText editReportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);
        editReportSaveButton=(Button)findViewById(R.id.editReportSaveButton);
        calloutToWhichReportBelongs=dbManager.getSingleCallout(idOfCalloutToDisplayInDetail);
        editReportText=(EditText)findViewById(R.id.editReportText);
        editReportText.setText(calloutToWhichReportBelongs.getReportText());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_report, menu);
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

    public void save(View view)
    {
        calloutToWhichReportBelongs.setReportText(editReportText.getText().toString());
        dbManager.editCallout(calloutToWhichReportBelongs);
        finish();
        startActivity(new Intent(this,Report.class));
    }
}
