package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.model.Photo;
import ie.hodmon.computing.service_manager.model.Video;

/**
 * Created by john on 22/02/16.
 */
public class ReportVideosAdapter extends ArrayAdapter<Video>
{
    private Context context;
    private List<Video> videoList;
    public ReportVideosAdapter(Context context,List<Video> videoList)
    {
        super(context, R.layout.row_report_videos,videoList);
        this.context=context;
        this.videoList=videoList;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater inflaterForReport =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewOfRow=inflaterForReport.inflate(R.layout.row_report_videos, parent, false);
        Video videoInThisRow=videoList.get(position);
        TextView videoIdThisRow=(TextView)viewOfRow.findViewById(R.id.video_id);
        TextView videoUrlThisRow=(TextView)viewOfRow.findViewById(R.id.video_url);
        TextView videoFileNameThisRow=(TextView)viewOfRow.findViewById(R.id.video_path);
        videoUrlThisRow.setText("" + videoInThisRow.getUrl());
        videoIdThisRow.setText("" + videoInThisRow.getId());
        videoFileNameThisRow.setText(videoInThisRow.getVideo_attachment_file_name());



          return viewOfRow;




    }

}
