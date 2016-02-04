package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.util.Log;
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
import ie.hodmon.computing.service_manager.model.PartList;
import ie.hodmon.computing.service_manager.model.PartListWithPartNumber;

/**
 * Created by john on 01/02/16.
 */
public class PartListSpinnerAdapter extends ArrayAdapter<PartListWithPartNumber>
{

        private Context partlistSpinnerContext;
        private List<PartListWithPartNumber> partListWithPartNumbers;

        public PartListSpinnerAdapter (Context partlistSpinnerContext , List <PartListWithPartNumber> partListWithPartNumbers)
        {
            super(partlistSpinnerContext, R.layout.part_list_for_spinner,partListWithPartNumbers);
            this.partlistSpinnerContext=partlistSpinnerContext;
            this.partListWithPartNumbers=partListWithPartNumbers;
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)partlistSpinnerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.part_list_for_spinner,parent,false);
        PartListWithPartNumber partListToShow = partListWithPartNumbers.get(position);
        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_description);





        if (!partListWithPartNumbers.isEmpty()) {



            descriptionInThisRow.setText("" + partListToShow.getDescription());

            //Log.v("spinner", "adapter setting description: " + partListToShow.getDescription());
            //Log.v("spinner","adapter setting id: "+partListToShow.getPart_id());
        }

        return viewOfRow;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflaterForReport =
                (LayoutInflater)partlistSpinnerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewOfRow=inflaterForReport.inflate(R.layout.part_list_for_spinner,parent,false);
        PartListWithPartNumber partListToShow = partListWithPartNumbers.get(position);
        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_description);

        if (!partListWithPartNumbers.isEmpty()) {



            descriptionInThisRow.setText("" + partListToShow.getDescription());

           // Log.v("spinner", "adapter setting dropdown description: " + partListToShow.getDescription());
            //Log.v("spinner", "adapter setting dropdown id: " + partListToShow.getPart_id());
        }
        return viewOfRow;
    }
    }

