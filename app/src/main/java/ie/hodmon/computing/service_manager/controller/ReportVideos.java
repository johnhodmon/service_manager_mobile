package ie.hodmon.computing.service_manager.controller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.connection.ConnectionAPI;
import ie.hodmon.computing.service_manager.model.Photo;

public class ReportVideos extends ClassForCommonAttributes {

    private List<Photo> listOfVideos;
    private ListView reportVideoListView;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listOfVideos =new ArrayList<Photo>();
        setContentView(R.layout.activity_report_videos);
        reportVideoListView=(ListView)findViewById(R.id.report_video_list_view);
        Log.v("check picture list", "" + listOfVideos.size());
        ReportImagesAdapter ria=new ReportImagesAdapter(this, listOfVideos);
        reportVideoListView.setAdapter(ria);
        //new GetVideos(ReportVideos.this).execute("/videos?job_id=" + jobToDisplay.getId());
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
                Log.v("VIDEO", "BYTES: " + bytes.length);



            }
        }

            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
    }

    public String prepareImageForUpload(Bitmap scaledBitmap)
    {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String encodedBinary= Base64.encodeToString(byteArray,Base64.DEFAULT);
            return encodedBinary;
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;

    }

    public void deletePhoto(View view)
    {
        RelativeLayout rowContainingButton=(RelativeLayout)view.getParent();
        ImageView imageThisRow=(ImageView)rowContainingButton.getChildAt(0);
        TextView tw=(TextView)rowContainingButton.getChildAt(2);
        String photoId=tw.getText().toString();
        onDeletePhoto(photoId);
        Log.v("check photo delete", "photoId: " + photoId);

    }

    public void onDeletePhoto(final String photoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete This Photo?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setMessage("Are you sure?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new DeletePhoto(ReportVideos.this).execute("/photos/" + photoId);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void enlarge (View view)
    {

        ImageView imageThisRow=(ImageView)view;

        final Dialog dialog = new Dialog(this,R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.image_for_dialog);
        ImageView btnClose = (ImageView)dialog.findViewById(R.id.x_pic);
        ImageView img = (ImageView)dialog.findViewById(R.id.image_dialog);
        img.setImageBitmap(((BitmapDrawable) imageThisRow.getDrawable()).getBitmap());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private class GetVideos extends AsyncTask<String, Void, List<Photo>> {

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
            this.dialog.setMessage("Retrieving Photos");
            this.dialog.show();
        }

        @Override
        protected List<Photo> doInBackground(String... params) {
            try {
                Log.v("REST", "Getting Photos");
                return (List<Photo>) ConnectionAPI.getPhotos((String) params[0]);
            }
            catch (Exception e) {
                Log.v("REST", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Photo> result) {
            super.onPostExecute(result);
            if(result!=null) {
                listOfVideos = result;

                ReportImagesAdapter ria = new ReportImagesAdapter(ReportVideos.this, listOfVideos);
                reportVideoListView.setAdapter(ria);
            }

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

        }
    }

    private class DeletePhoto extends AsyncTask<String, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public DeletePhoto(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Photo");
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

            new GetVideos(ReportVideos.this).execute("/photos?job_id=" + jobToDisplay.getId());

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private class AddPhoto extends AsyncTask<Object, Void, String> {

        protected ProgressDialog dialog;
        protected Context context;

        public AddPhoto(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Adding Photo....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {

            String res = null;
            try {
                Log.v("REST", "Posting job part");

                ConnectionAPI.addPhoto((String) params[0], (Photo) params[1]);

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
                Log.v("REST", "Post Photo result: "+result);
            }
            Toast.makeText(ReportVideos.this,"Photo uploaded", Toast.LENGTH_LONG).show();
            new GetVideos(ReportVideos.this).execute("/photos?job_id=" + jobToDisplay.getId());
            if (dialog.isShowing())
                dialog.dismiss();

        }
    }

}
