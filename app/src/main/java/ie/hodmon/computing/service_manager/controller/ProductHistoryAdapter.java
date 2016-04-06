package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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

        View viewOfRow=inflaterForReport.inflate(R.layout.row_callout,parent,false);
        Job jobToShow = jobList.get(position);
        TextView dateInThisRow=(TextView)viewOfRow.findViewById(R.id.product_history_row_date);
        TextView faultInThisRow=(TextView)viewOfRow.findViewById(R.id.product_history_row_fault);





        if (!jobList.isEmpty()) {
            dateInThisRow.setText("" + jobToShow.getCreated_at());
            faultInThisRow.setText("" + jobToShow.getReported_fault());


        }




        return viewOfRow;

    }

}

