package ie.hodmon.computing.service_manager.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
       ImageView videoPreview=(ImageView)viewOfRow.findViewById(R.id.video_preview);
        
        Video videoInThisRow=videoList.get(position);

        String url=videoInThisRow.getUrl();
       String address = "https://whispering-gorge-59927.herokuapp.com" + url;
        Log.v("video", "address" + address);
        try {
            Bitmap bmp= new GetVideoPreview().execute(address).get();

            videoPreview.setImageBitmap(bmp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        TextView videoIdThisRow=(TextView)viewOfRow.findViewById(R.id.video_id);
        TextView videoFileNameThisRow=(TextView)viewOfRow.findViewById(R.id.video_file_name);
        videoFileNameThisRow.setText(videoInThisRow.getVideoLocation());


        videoIdThisRow.setText("" + videoInThisRow.getId());




          return viewOfRow;




    }

    private class GetVideoPreview extends AsyncTask<String, Void, Bitmap> {




        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

        }
    }


}
