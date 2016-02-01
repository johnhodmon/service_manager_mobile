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
import ie.hodmon.computing.service_manager.model.PartList;

/**
 * Created by john on 01/02/16.
 */
public class PartListSpinnerAdapter extends ArrayAdapter<PartList>
{

        private Context partlistSpinnerContext;
        private List<PartList> partList;

        public PartListSpinnerAdapter (Context partlistSpinnerContext , List < PartList > partList)
        {
            super(partlistSpinnerContext, R.layout.part_list_for_spinner,partList);
            this.partlistSpinnerContext=partlistSpinnerContext;
            this.partList=partList;
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)partlistSpinnerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.part_list_for_spinner,parent,false);
        PartList partListToShow = partList.get(position);
        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_description);
        TextView idThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_id);




        if (!partList.isEmpty()) {



            descriptionInThisRow.setText("" + partListToShow.getDescription());
            idThisRow.setText("" + partListToShow.getId());
        }




        return viewOfRow;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflaterForReport =
                (LayoutInflater)partlistSpinnerContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewOfRow=inflaterForReport.inflate(R.layout.part_list_for_spinner,parent,false);
        PartList partListToShow = partList.get(position);
        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_description);
        TextView idThisRow=(TextView)viewOfRow.findViewById(R.id.part_list_spinner_id);
        if (!partList.isEmpty()) {



            descriptionInThisRow.setText("" + partListToShow.getDescription());
            idThisRow.setText("" + partListToShow.getId());
        }
        return viewOfRow;
    }
    }

