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
public class JobsAdapter extends ArrayAdapter<Job>
{

    private Context calloutAdapterContext;
    public List<Job> jobList;

    public JobsAdapter(Context calloutAdapterContext, List<Job> jobList)
    {
        super(calloutAdapterContext, R.layout.row_callout, jobList);
        this.calloutAdapterContext=calloutAdapterContext;
        this.jobList = jobList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater) calloutAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_callout,parent,false);
        Job jobToShow = jobList.get(position);
        TextView productNameInThisRow=(TextView)viewOfRow.findViewById(R.id.job_row_product_name);
        TextView calloutTownInThisRow=(TextView)viewOfRow.findViewById(R.id.calloutTown);
        TextView calloutPhoneInThisRow=(TextView)viewOfRow.findViewById(R.id.job_row_phone);




        if (!jobList.isEmpty()) {
            productNameInThisRow.setText("" + jobToShow.getCustomer_product_id());
            calloutTownInThisRow.setText("" + jobToShow.getReported_fault());
            calloutPhoneInThisRow.setText(""+ jobToShow.getStatus());

        }




        return viewOfRow;

    }

}

