package ie.hodmon.computing.service_manager.controller;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;


import java.util.List;

import ie.hodmon.computing.service_manager.model.Job;


/**
 * Created by John on 19/02/2015.
 */
public class ClassForCommonAttributes extends AppCompatActivity

{



    public static int idOfJobToDisplayInDetail;
    public static String dateToShow;
    public static boolean notToday;
    public static String engineerEmail;
    public static long dateSelected;
    public static List<Job>jobs;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }
}





