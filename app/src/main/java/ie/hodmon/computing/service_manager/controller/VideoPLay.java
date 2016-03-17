package ie.hodmon.computing.service_manager.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import ie.hodmon.computing.service_manager.R;
import ie.hodmon.computing.service_manager.controller.ClassForCommonAttributes;

public class VideoPLay extends ClassForCommonAttributes {
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        MediaController vidControl = new MediaController(this);
        vv=(VideoView)findViewById(R.id.myVideo);
        vidControl.setAnchorView(vv);
        vv.setMediaController(vidControl);
        Uri vidUri = Uri.parse("http://192.168.1.103/test_video");
        vv.setVideoURI(vidUri);
        vv.start();



    }



}