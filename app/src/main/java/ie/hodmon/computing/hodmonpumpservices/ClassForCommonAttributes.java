package ie.hodmon.computing.hodmonpumpservices;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by John on 19/02/2015.
 */
public class ClassForCommonAttributes extends ActionBarActivity

{

    public static Map<String,String> mapOfEmailsToPasswords;
    public static Map<String,String> mapOfEmailsToNames;
    public DatabaseManagement dbManager=new DatabaseManagement(this);
    public static int idOfCalloutToDisplayInDetail;
    public static boolean formCalledFromEdit;
    public static String dateToShow;
    public static boolean notToday;
    public static String emailAddressEntered;
    public static long dateSelected;



    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            dbManager.open();
            Log.v("service", "Service App Started");
            mapOfEmailsToPasswords=new HashMap<String,String>();
            mapOfEmailsToNames=new HashMap<String,String>();
            mapOfEmailsToPasswords.put("johnhodmon@gmail.com","p");
            mapOfEmailsToNames.put("johnhodmon@gmail.com","John Hodmon");
        }



        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(dbManager.getCallouts("01/12/2015").isEmpty())
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
        dbManager.addPump(new Pump("05015000","Piranha S17"));
        dbManager.addPump(new Pump("01135002","Robusta 202"));
        dbManager.addPump(new Pump("05065001","MF 254"));
        dbManager.addPump(new Pump("06085002","AS16"));
        dbManager.addCallout(new Callout("01/12/2015","Bernard Grant","5, The Heights","Rosslare","Wexford","0872565871","06085002: AS16",
                "Pump not running, sump overflowing",""));
        dbManager.addCallout(new Callout("01/12/2015","John Hodmon","Ballyhack","New Ross","Wexford","0852828731","05015000: Piranha S17",
                "MCB in fuse board tripping and won't reset",""));
        dbManager.addCallout(new Callout("01/12/2015","Seamus Cooney","11, Knackers Row","New Ross","Wexford","08798765432","05065001: MF254",
                "Pump making loud grinding noise",""));
        dbManager.addCallout(new Callout("01/12/2015","Peter Dempsey","11,Ballybeg","Waterford","","08612345678","01135002: Robusta 202",
                "Won't switch off, feels very hot",""));
        dbManager.addCallout(new Callout("01/12/2015","Mary Foley","13, Griffith Avenue","Dublin 9","","0864589712","05015000: Piranha S17",
                "Cellar Flooded,alarm sounding in control panel",""));
        dbManager.addCallout(new Callout("01/04/2015","Ciaran Quirke","11, Main Street","Gorey","Wexford","0539236589","01135002: Robusta 202",
                "Pump running but not pumping",""));
        dbManager.addCallout(new Callout("01/04/2015","Ben Breen","","Ballycanew","Wexford","0852829713","06085002: AS16",
                "Explosion from control panel",""));
        dbManager.addCallout(new Callout("01/04/2015","Johnny O'Grady","","Castlebridge","Wexford","0539125471","05065001: MF254",
                "Grinding noise",""));
        dbManager.addCallout(new Callout("01/04/2015","Noel Kinsella","6, High Street","Arthurstown","Wexford","051389571","05015000: Piranha S17",
                "Only one pump working on unit",""));
        dbManager.addCallout(new Callout("01/04/2015","Mike Hunt","1, Moby Drive","Glasnevin","Dublin 9","01999123","05065001: MF254",
                "Float cable broken",""));
        dbManager.addSparesOrderItem(new SparesOrderItem(1,"31005000","Motor Housing AS16",1));
        dbManager.addSparesOrderItem(new SparesOrderItem(1,"31055015","Volute AS16",1));
        dbManager.addSparesOrderItem(new SparesOrderItem(1, "31065003", "Wear Plate AS16", 1));
        dbManager.addPart(new Part("31005000","Motorhousing AS16"));
        dbManager.addPart(new Part("31055000","Volute AS16"));
        dbManager.addPart(new Part("31075000","Impeller AS16"));
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
        dbManager.addPartsList(new PartsList("31005000","06085002",1));
        dbManager.addPartsList(new PartsList("31055000","06085002",1));
        dbManager.addPartsList(new PartsList("31075000","06085002",1));
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