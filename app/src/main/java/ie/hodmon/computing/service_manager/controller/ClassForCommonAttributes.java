package ie.hodmon.computing.service_manager.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;

import ie.hodmon.computing.service_manager.db.DatabaseManagement;
import ie.hodmon.computing.service_manager.model.PartsList;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Part;
import ie.hodmon.computing.service_manager.model.Product;

/**
 * Created by John on 19/02/2015.
 */
public class ClassForCommonAttributes extends ActionBarActivity

{


    public DatabaseManagement dbManager=new DatabaseManagement(this);
    public static int idOfCalloutToDisplayInDetail;
    public static String dateToShow;
    public static boolean notToday;
    public static String engineerEmail;
    public static long dateSelected;



    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            dbManager.open();
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "usLwRiTvH6M49iUJCLMpw2YHu8dQDdiTBIP2miMN", "oMK6P68M5pHr4bXss119mhHxEERxmB1IMo51xHEA");

            Log.v("service", "Service App Started");


        }



        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(dbManager.getCallouts("johnhodmon@gmail.com", "01/12/2015").isEmpty())
        {
            addData();
        }


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        dbManager.close();

    }

    public void addData()
    {
        dbManager.addPump(new Product("05015000","Piranha S17"));
        dbManager.addPump(new Product("01135002", "Robusta 202"));
        dbManager.addPump(new Product("05065001", "MF 254"));
        dbManager.addPump(new Product("06085002", "AS16"));

        dbManager.addCallout(new Job("johnhodmon@gmail.com", "01/12/2015", "Noel Kinsella", "6, High Street", "Arthurstown", "Wexford", "051389571", "05015000: Piranha S17",
                "Only one pump working on unit", "", new LatLng(52.2420291, -6.9601263)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com", "01/12/2015", "Bernard Grant", "5, The Heights", "Rosslare", "Wexford", "0872565871", "06085002: AS16",
                "Product not running, sump overflowing", "", new LatLng(52.2720398, -6.3979802)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com", "01/12/2015", "John Hodmon", "Ballyhack", "New Ross", "Wexford", "0852828731", "05015000: Piranha S17",
                "MCB in fuse board tripping and won't reset", "", new LatLng(52.2484872, -6.9729468)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com","01/12/2015","Seamus Cooney","11, Knackers Row","New Ross","Wexford","08798765432","05065001: MF254",
                "Product making loud grinding noise","",new LatLng(52.3946149,-6.9615096)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com","01/12/2015","Peter Dempsey","11,Ballybeg","Waterford","","08612345678","01135002: Robusta 202",
                "Won't switch off, feels very hot","",new LatLng(52.2420643,-7.1509622)));

        dbManager.addCallout(new Job("johnhodmon@gmail.com","02/12/2015","Ciaran Quirke","11, Main Street","Gorey","Wexford","0539236589","01135002: Robusta 202",
                "Product running but not pumping","",new LatLng(52.6757387,-6.2964909)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com","02/12/2015","Ben Breen","","Ballycanew","Wexford","0852829713","06085002: AS16",
                "Explosion from control panel","",new LatLng(52.6118702,-6.3211213)));
        dbManager.addCallout(new Job("johnhodmon@gmail.com","02/12/2015", "Johnny O'Grady", "", "Castlebridge", "Wexford", "0539125471", "05065001: MF254",
                "Grinding noise", "",new LatLng(52.3856753,-6.45683)));



        dbManager.addPart(new Part("31005000", "Motorhousing AS16"));
        dbManager.addPart(new Part("31055000", "Volute AS16"));
        dbManager.addPart(new Part("31075000", "Impeller AS16"));
        dbManager.addPart(new Part("35015000","Rotorshaft AS16"));
        dbManager.addPart(new Part("11120106","O_Ring 25x3"));
        dbManager.addPart(new Part("11200100","Screw hex M8x25"));
        dbManager.addPart(new Part("11560200","Bearing ID12 OD25"));
        dbManager.addPart(new Part("31005001","Motorhousing Robusta 202"));
        dbManager.addPart(new Part("31055001","Volute Robusta 202"));
        dbManager.addPart(new Part("31075001","Impeller Robusta 202"));
        dbManager.addPart(new Part("35015001","Rotorshaft Robusta 202"));
        dbManager.addPart(new Part("11120107","O_Ring 115x3"));
        dbManager.addPart(new Part("11560201","Bearing ID9 OD20"));
        dbManager.addPart(new Part("31005002","Motorhousing MF 254"));
        dbManager.addPart(new Part("31055002","Volute MF 254"));
        dbManager.addPart(new Part("31075002","Impeller MF 254"));
        dbManager.addPart(new Part("35015002","Rotorshaft MF 254"));
        dbManager.addPart(new Part("11120108","O_Ring 80x5"));
        dbManager.addPart(new Part("11560202","Bearing ID12 OD20"));
        dbManager.addPart(new Part("11200101","Screw hex M6x16"));
        dbManager.addPart(new Part("31005003","Motorhousing Piranha S17"));
        dbManager.addPart(new Part("31055003","Volute Piranha S17"));
        dbManager.addPart(new Part("31075003","Impeller Piranha S17"));
        dbManager.addPart(new Part("35015003","Rotorshaft Piranha S17"));
        dbManager.addPart(new Part("11120109","O_Ring 150x5"));
        dbManager.addPartsList(new PartsList("31005000", "06085002", 1));
        dbManager.addPartsList(new PartsList("31055000", "06085002", 1));
        dbManager.addPartsList(new PartsList("31075000", "06085002", 1));
        dbManager.addPartsList(new PartsList("35015000","06085002",1));
        dbManager.addPartsList(new PartsList("11120106","06085002",2));
        dbManager.addPartsList(new PartsList("11200100","06085002",8));
        dbManager.addPartsList(new PartsList("11560200","06085002",2));
        dbManager.addPartsList(new PartsList("31005001","01135002",1));
        dbManager.addPartsList(new PartsList("31055001","01135002",1));
        dbManager.addPartsList(new PartsList("31075001","01135002",1));
        dbManager.addPartsList(new PartsList("35015001","01135002",1));
        dbManager.addPartsList(new PartsList("11120107","01135002",2));
        dbManager.addPartsList(new PartsList("11560201","01135002",2));
        dbManager.addPartsList(new PartsList("31005002","05065001",1));
        dbManager.addPartsList(new PartsList("31055002","05065001",1));
        dbManager.addPartsList(new PartsList("31075002","05065001",1));
        dbManager.addPartsList(new PartsList("35015002","05065001",1));
        dbManager.addPartsList(new PartsList("11120108","05065001",2));
        dbManager.addPartsList(new PartsList("11200101","05065001",8));
        dbManager.addPartsList(new PartsList("11560202","05065001",2));
        dbManager.addPartsList(new PartsList("31005003","05015000",1));
        dbManager.addPartsList(new PartsList("31055003","05015000",1));
        dbManager.addPartsList(new PartsList("31075003","05015000",1));
        dbManager.addPartsList(new PartsList("35015003","05015000",1));
        dbManager.addPartsList(new PartsList("11120109","05015000",2));
        dbManager.addPartsList(new PartsList("11200100","05015000",8));
        dbManager.addPartsList(new PartsList("11560200","05015000",2));
      
    }



}