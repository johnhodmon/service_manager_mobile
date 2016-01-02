package ie.hodmon.computing.hodmonpumpservices;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Map;


public class PickDate extends ClassForCommonAttributes implements CalendarView.OnDateChangeListener {
    private CalendarView calendarView;
    private Button pickDateButton;
    private String  monthAsString;
    private String day;
    private String month;
    private String year;
    private Map<String,String> mapMonths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        pickDateButton=(Button)findViewById(R.id.pickDateButton);
        if(notToday)
        {
            calendarView.setDate(dateSelected);
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_date, menu);

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
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
    {
        month=month+1;
        this.day=dayOfMonth<10==true? "0"+dayOfMonth:""+dayOfMonth;
        this.month=month<10==true? "0"+month:""+month;
        this.year=""+year;



    }

    public void confirmDate(View view)
    {
        notToday=true;
        dateToShow = (day + "/" +month + "/"+year);
        finish();
        startActivity(new Intent(this,CalloutScreen.class));
        dateSelected=calendarView.getDate();
    }
}
