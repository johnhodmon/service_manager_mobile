package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.JobPart;

/**
 * Created by John on 22/02/2015.
 */
public class SparesOrderAdapter extends ArrayAdapter<JobPart>

{
    private Context sparesOrderItemAdapterContext;
    public List<JobPart> jobPartList;

    public SparesOrderAdapter(Context sparesOrderItemAdapterContext,List<JobPart> jobPartList)
    {
        super(sparesOrderItemAdapterContext, R.layout.row_spares_order, jobPartList);
        this.sparesOrderItemAdapterContext=sparesOrderItemAdapterContext;
        this.jobPartList = jobPartList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)sparesOrderItemAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_spares_order,parent,false);
        JobPart jobPartToShow = jobPartList.get(position);

        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowDescription);
        TextView quantityThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowQuantity);
        ImageView deleteButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_delete_symbol);
        ImageView editButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_edit_symbol);
        Spinner spinnerForQuantity=(Spinner) viewOfRow.findViewById(R.id.spinnerForQuantity);
        spinnerForQuantity.setVisibility(View.INVISIBLE);




        if (!jobPartList.isEmpty()) {



            descriptionInThisRow.setText("" + jobPartToShow.getPartDescription());
            quantityThisRow.setText("" + jobPartToShow.getQuantity());
        }




        return viewOfRow;

    }
}
