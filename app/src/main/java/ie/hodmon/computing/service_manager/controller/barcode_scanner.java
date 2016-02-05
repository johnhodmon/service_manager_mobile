package ie.hodmon.computing.service_manager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import ie.hodmon.computing.service_manager.barcode_scanner.BarcodeGraphic;
import ie.hodmon.computing.service_manager.barcode_scanner.CameraSourcePreview;
import ie.hodmon.computing.service_manager.barcode_scanner.GraphicOverlay;
import ie.hodmon.computing.service_manager.R;

public class barcode_scanner extends ClassForCommonAttributes {


    public CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;
    private  BarcodeTrackerFactory barcodeFactory;
    private BarcodeDetector barcodeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.overlay);
        barcodeDetector = new BarcodeDetector.Builder(this).build();
        barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());
        mCameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        if(!barcodeDetector.isOperational()){
            Toast.makeText(getApplicationContext(), "Could not set up the detector!", Toast.LENGTH_SHORT).show();
            return;
        }







    }


        class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
            private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

            BarcodeTrackerFactory(GraphicOverlay<BarcodeGraphic> barcodeGraphicOverlay) {
                mGraphicOverlay = barcodeGraphicOverlay;
            }

            @Override
            public Tracker<Barcode> create(Barcode barcode) {
                BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
                return new BarcodeGraphicTracker(mGraphicOverlay, graphic);
            }

        }



    //starting the preview
    private void startCameraSource() {
        try {
            mPreview.start(mCameraSource, mGraphicOverlay);
        } catch (IOException e) {
            mCameraSource.release();
            mCameraSource = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource(); //start
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop(); //stop
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCameraSource.release(); //release the resources
    }

class BarcodeGraphicTracker extends Tracker<Barcode> {
    private GraphicOverlay<BarcodeGraphic> mOverlay;
    private BarcodeGraphic mGraphic;

    BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> overlay, BarcodeGraphic graphic) {
        mOverlay = overlay;
        mGraphic = graphic;

    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {
        mGraphic.setId(id);
    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item)
    {
        mOverlay.add(mGraphic);
        mGraphic.updateItem(item);

        Intent intent=new Intent(barcode_scanner.this,ReportScreen.class);
        intent.putExtra("barcode",item.rawValue);
        finish();
        startActivity(intent);

    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        mOverlay.remove(mGraphic);
    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {
        mOverlay.remove(mGraphic);
    }
}

}




