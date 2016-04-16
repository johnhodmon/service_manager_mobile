package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Job;

/**
 * Created by John on 19/02/2015.
 */
public class ProductHistoryAdapter extends ArrayAdapter<Job>
{

    private Context productHistoryAdapterContext;
    public List<Job> jobList;

    public ProductHistoryAdapter(Context productHistoryAdapterContext, List<Job> jobList)
    {
        super(productHistoryAdapterContext, R.layout.row_callout, jobList);
        this.productHistoryAdapterContext =productHistoryAdapterContext;
        this.jobList = jobList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater) productHistoryAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_product_history,parent,false);
        Job jobToShow = jobList.get(position);
        TextView dateInThisRow=(TextView)viewOfRow.findViewById(R.id.product_history_row_date);
        TextView faultInThisRow=(TextView)viewOfRow.findViewById(R.id.product_history_row_fault);





        if (!jobList.isEmpty()) {
            dateInThisRow.setText(formatDate(jobToShow.getAllocation_date()));
            faultInThisRow.setText("" + jobToShow.getReported_fault());


        }




        return viewOfRow;

    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());

        Log.v("radiobuttons", "date being formatted " + dateFormat.format(date));
        return dateFormat.format(date);

    }

}

