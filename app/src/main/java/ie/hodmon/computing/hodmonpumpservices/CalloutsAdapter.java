package ie.hodmon.computing.hodmonpumpservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by John on 19/02/2015.
 */
public class CalloutsAdapter extends ArrayAdapter<Callout>
{

    private Context calloutAdapterContext;
    public List<Callout> calloutList;

    public CalloutsAdapter(Context calloutAdapterContext,List<Callout>calloutList)
    {
        super(calloutAdapterContext,R.layout.row_callout,calloutList);
        this.calloutAdapterContext=calloutAdapterContext;
        this.calloutList=calloutList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater) calloutAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_callout,parent,false);
        Callout calloutToShow=calloutList.get(position);
        TextView calloutNameInThisRow=(TextView)viewOfRow.findViewById(R.id.calloutName);
        TextView calloutTownInThisRow=(TextView)viewOfRow.findViewById(R.id.calloutTown);




        if (!calloutList.isEmpty()) {
            calloutNameInThisRow.setText("" + calloutToShow.getCustomerName());
            calloutTownInThisRow.setText("" + calloutToShow.getTown());

        }




        return viewOfRow;

    }

}

