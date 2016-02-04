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
import ie.hodmon.computing.service_manager.model.JobPartWithPartNumber;

/**
 * Created by John on 22/02/2015.
 */
public class PartsUsedAdapter extends ArrayAdapter<JobPartWithPartNumber>

{
   private Context sparesOrderItemAdapterContext;
    public List<JobPartWithPartNumber> jobPartList;

    public PartsUsedAdapter(Context sparesOrderItemAdapterContext, List<JobPartWithPartNumber> jobPartList)
    {
        super(sparesOrderItemAdapterContext, R.layout.row_parts_used, jobPartList);
        this.sparesOrderItemAdapterContext=sparesOrderItemAdapterContext;
        this.jobPartList = jobPartList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)sparesOrderItemAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_parts_used,parent,false);
        JobPartWithPartNumber jobPartToShow = jobPartList.get(position);

        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowDescription);
        TextView quantityThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowQuantity);
        ImageView deleteButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_delete_symbol);
        ImageView editButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_edit_symbol);
        Spinner spinnerForQuantity=(Spinner) viewOfRow.findViewById(R.id.editPartQtySpinner);
        spinnerForQuantity.setVisibility(View.INVISIBLE);




        if (!jobPartList.isEmpty()) {



            descriptionInThisRow.setText("" + jobPartToShow.getDescription());
            quantityThisRow.setText("" + jobPartToShow.getQuantity());
        }




        return viewOfRow;

    }
}
