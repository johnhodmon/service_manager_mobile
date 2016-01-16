package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.hodmon.computing.service_manager.R;

/**
 * Created by John on 2015-12-03.
 */
public class ReportImagesAdapter extends ArrayAdapter<ReportPhoto>
{

    private Context context;
    private List<ReportPhoto> imageList;
    public ReportImagesAdapter(Context context,List<ReportPhoto> imageList)
    {
        super(context, R.layout.row_report_pictures,imageList);
       this.context=context;
        this.imageList=imageList;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflaterForReport =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewOfRow=inflaterForReport.inflate(R.layout.row_report_pictures, parent, false);
        ReportPhoto photoInThisRow=imageList.get(position);
        byte[] byteInThisRow=photoInThisRow.getBlob();
        ImageView imageThisRow=(ImageView)viewOfRow.findViewById(R.id.report_image);
        TextView photoIdThisRow=(TextView)viewOfRow.findViewById(R.id.photo_id);
        photoIdThisRow.setText(""+photoInThisRow.getPhotoId());
        Bitmap bmp = BitmapFactory.decodeByteArray(byteInThisRow, 0, byteInThisRow.length);
        imageThisRow.setImageBitmap(bmp);





        return viewOfRow;

    }
}
