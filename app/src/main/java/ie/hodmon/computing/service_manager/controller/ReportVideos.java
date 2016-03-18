package ie.hodmon.computing.service_manager.controller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.connection.REST;
import ie.hodmon.computing.service_manager.model.Photo;
import ie.hodmon.computing.service_manager.model.Video;

public class ReportVideos extends ClassForCommonAttributes {

    private List<Video> listOfVideos;
    private ListView reportVideoListView;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listOfVideos =new ArrayList<Video>();
        setContentView(R.layout.activity_report_videos);
        reportVideoListView=(ListView)findViewById(R.id.report_video_list_view);
        Log.v("check picture list", "" + listOfVideos.size());
        new GetVideos(ReportVideos.this).execute("/videos?job_id=" + jobToDisplay.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void captureVideo(View view)
    {
        try {

            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE&& fileUri!=null) {
            if (resultCode == RESULT_OK) {

                Video v=new Video(jobToDisplay.getId());
                v.setLocalUri(fileUri);
                Log.v("VIDEO"," file uri"+fileUri);
                new AddVideo(this).execute("/videos",v);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        Log.v("VIDEO","media storage directory: "+mediaStorageDir);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }





    public void deleteVideo(View view)
    {
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();

        TextView tw=(TextView)rowContainingButton.getChildAt(4);
        String videoId=tw.getText().toString();
        onDeleteVideo(videoId);



    }

    public void onDeleteVideo(final String videoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete This Video?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setMessage("Are you sure?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new DeleteVideo(ReportVideos.this).execute("/videos/" + videoId);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



    private class GetVideos extends AsyncTask<String, Void, List<Video>> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetVideos(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Videos");
            this.dialog.show();
        }

        @Override
        protected List<Video> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Photos");
                return (List<Video>) ConnectionAPI.getVideos((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Video> result) {
            super.onPostExecute(result);
            if(result!=null) {
                listOfVideos = result;

                ReportVideosAdapter riv = new ReportVideosAdapter(ReportVideos.this, listOfVideos);
                reportVideoListView.setAdapter(riv);
            }

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

        }
    }

    private class DeleteVideo extends AsyncTask<String, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public DeleteVideo(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Video");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return (String) ConnectionAPI.delete((String) params[0]);
            } catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            String s = result;
            Log.v("REST", "DELETE REQUEST : " + s);

            new GetVideos(ReportVideos.this).execute("/videos?job_id=" + jobToDisplay.getId());

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private class AddVideo extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public AddVideo(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Uploading Video....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Posting video");

                REST.uploadVideo((String) params[0], (Video) params[1]);

            }

            catch(Exception e)
            {
                Log.v("REST","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null) {
                Log.v("REST", "Post Video result: "+result);
            }
            Toast.makeText(ReportVideos.this,"Video uploaded", Toast.LENGTH_LONG).show();
            new GetVideos(ReportVideos.this).execute("/videos?job_id=" + jobToDisplay.getId());
            if (dialog.isShowing())
                dialog.dismiss();

        }
    }

    public void playVideo(View view)
    {
        Log.v("VIDEO", "PLAY VIDEO ENTERED");
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();

        TextView tw=(TextView)rowContainingButton.getChildAt(3);
        String url=tw.getText().toString();
        Intent i=new Intent(this,VideoPLay.class);
        i.putExtra("url",url);
        startActivity(i);


    }

}
