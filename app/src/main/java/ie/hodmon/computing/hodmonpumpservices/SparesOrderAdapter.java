package ie.hodmon.computing.hodmonpumpservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by John on 22/02/2015.
 */
public class SparesOrderAdapter extends ArrayAdapter<SparesOrderItem>

{
    private Context sparesOrderItemAdapterContext;
    public List<SparesOrderItem> sparesOrderItemList;

    public SparesOrderAdapter(Context sparesOrderItemAdapterContext,List<SparesOrderItem>sparesOrderItemList)
    {
        super(sparesOrderItemAdapterContext,R.layout.row_spares_order,sparesOrderItemList);
        this.sparesOrderItemAdapterContext=sparesOrderItemAdapterContext;
        this.sparesOrderItemList =sparesOrderItemList;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)sparesOrderItemAdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewOfRow=inflaterForReport.inflate(R.layout.row_spares_order,parent,false);
        SparesOrderItem sparesOrderItemToShow= sparesOrderItemList.get(position);

        TextView descriptionInThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowDescription);
        TextView quantityThisRow=(TextView)viewOfRow.findViewById(R.id.sparesOrderRowQuantity);
        ImageView deleteButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_delete_symbol);
        ImageView editButtonThisRow=(ImageView)viewOfRow.findViewById(R.id.spares_row_edit_symbol);
        Spinner spinnerForQuantity=(Spinner) viewOfRow.findViewById(R.id.spinnerForQuantity);
        spinnerForQuantity.setVisibility(View.INVISIBLE);




        if (!sparesOrderItemList.isEmpty()) {



            descriptionInThisRow.setText("" + sparesOrderItemToShow.getPartDescription());
            quantityThisRow.setText("" + sparesOrderItemToShow.getQuantity());
        }




        return viewOfRow;

    }
}
