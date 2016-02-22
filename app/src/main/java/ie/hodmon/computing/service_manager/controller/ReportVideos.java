package ie.hodmon.computing.service_manager.controller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Photo;
import ie.hodmon.computing.service_manager.model.Video;

public class ReportVideos extends ClassForCommonAttributes {

    private List<Video> listOfVideos;
    private ListView reportVideoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listOfVideos =new ArrayList<Video>();
        setContentView(R.layout.activity_report_videos);
        reportVideoListView=(ListView)findViewById(R.id.report_video_list_view);
        Log.v("check picture list", "" + listOfVideos.size());
        ReportVideosAdapter riv=new ReportVideosAdapter(this, listOfVideos);
        reportVideoListView.setAdapter(riv);
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
        try
        {

            Intent recordVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (recordVideoIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(recordVideoIntent, 1);
            }

        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Uri videoUri = data.getData();
                Log.v("VIDEO", "URI: " + videoUri);
                InputStream is = getContentResolver().openInputStream(videoUri);
                byte[] bytes=getBytes(is);
                String encodedBinary= Base64.encodeToString(bytes, Base64.DEFAULT);
                Log.v("VIDEO", "BYTES: " + bytes.length);
                Video video=new Video(jobToDisplay.getId(),encodedBinary);
                new AddVideo(this).execute("/videos",video);



            }
        }

            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
    }



    public void deleteVideo(View view)
    {
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();

        TextView tw=(TextView)rowContainingButton.getChildAt(2);
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
            this.dialog.setMessage("Adding Video....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Posting video");

                ConnectionAPI.addVideo((String) params[0], (Video) params[1]);

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

}
