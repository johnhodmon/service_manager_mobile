package ie.hodmon.computing.service_manager.controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.os.Bundle;
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
import java.util.List;

import ie.hodmon.computing.service_manager.R;

public class ReportPhotos extends ClassForCommonAttributes {

  /*  private List<ReportPhoto>listOfPhotos;
    private ListView reportPictureListView;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_photos);
        reportPictureListView=(ListView)findViewById(R.id.report_picture_list_view);
        listOfPhotos= dbManager.getReportImages(idOfJobToDisplayInDetail);
        Log.v("check picture list", "" + listOfPhotos.size());
        ReportImagesAdapter ria=new ReportImagesAdapter(this,listOfPhotos);
        reportPictureListView.setAdapter(ria);
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

    public void takePhoto(View view)
    {
        try
        {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(takePictureIntent, 2);
            }

        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if((requestCode == 2 && resultCode == RESULT_OK
                    && null != data))
            {
                Bundle extras = data.getExtras();
                Bitmap unscaledBitmap = (Bitmap) extras.get("data");

                ReportPhoto r=new ReportPhoto(idOfJobToDisplayInDetail +"_"+listOfPhotos.size(), idOfJobToDisplayInDetail,
                        prepareImageForDatabase(unscaledBitmap));
                dbManager.saveReportPhoto(r.getPhotoId(),r.getJobId(),r.getBlob());
                finish();
                startActivity(new Intent(this, ReportPhotos.class));


            }



        }

        catch (Exception e)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public byte[] prepareImageForDatabase(Bitmap scaledBitmap)
    {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
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
        Log.v("check photo delete", "photoId: " + photoId);
        Bitmap bitmap = ((BitmapDrawable)imageThisRow.getDrawable()).getBitmap();
        dbManager.deleteReportPhoto(photoId);
        reportPictureListView.refreshDrawableState();
        Log.v("check photo delete", "delete pressed");
        finish();
        startActivity(new Intent(this,ReportPhotos.class));
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
*/


}
