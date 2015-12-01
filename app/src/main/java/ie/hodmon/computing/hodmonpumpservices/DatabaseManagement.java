package ie.hodmon.computing.hodmonpumpservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 19/02/2015.
 */
public class DatabaseManagement

{


    private SQLiteDatabase pumpServicesDatabase;
    private DatabaseCreator databaseCreator;


    public SQLiteDatabase getPumpServicesDatabase() {
        return pumpServicesDatabase;
    }

    public DatabaseManagement(Context context)
    {
        databaseCreator=new DatabaseCreator(context);



    }





    public void open()throws SQLException
    {
        pumpServicesDatabase=databaseCreator.getWritableDatabase();

        Log.v("service",pumpServicesDatabase.toString());
    }

    public void close()
    {
        pumpServicesDatabase.close();
    }

    public Callout getSingleCallout(int id)
    {
        Callout calloutToReturn=new Callout();
        Cursor cursor=pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_CALLOUT+" WHERE "+DatabaseCreator.CALLOUT_COLUMN_ID+" ="+id, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            calloutToReturn = convertCursorToCallout(cursor);
        }

        cursor.close();
        return calloutToReturn;
    }

    public void editCallout(Callout c)
{

    ContentValues values=new ContentValues();
    values.put(DatabaseCreator.CALLOUT_COLUMN_DATE,c.getDate());
    values.put(DatabaseCreator.CALLOUT_COLUMN_CUSTOMER_NAME,c.getCustomerName());
    values.put(DatabaseCreator.CALLOUT_COLUMN_STREET,c.getStreet());
    values.put(DatabaseCreator.CALLOUT_COLUMN_TOWN,c.getTown());
    values.put(DatabaseCreator.CALLOUT_COLUMN_COUNTY,c.getCounty());
    values.put(DatabaseCreator.CALLOUT_COLUMN_PHONE,c.getPhoneNumber());
    values.put(DatabaseCreator.CALLOUT_COLUMN_PUMP_NR ,c.getPumpNumber());
    values.put(DatabaseCreator.CALLOUT_COLUMN_REPORTED_FAULT ,c.getReportedFault());
    values.put(DatabaseCreator.CALLOUT_COLUMN_REPORT_TEXT ,c.getReportText());


    pumpServicesDatabase.update(DatabaseCreator.TABLE_CALLOUT,values,DatabaseCreator.CALLOUT_COLUMN_ID+"="+c.getId(),null);
}

    public void editSparePartsItem(int calloutId,int quantity,String description)
    {

        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_QUANTITY,quantity);


        pumpServicesDatabase.update(DatabaseCreator.TABLE_SPARES_ORDER_ITEM,values,DatabaseCreator.CALLOUT_COLUMN_ID+"="+calloutId+" AND "
                +DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION+"='"+description+"'",null);
    }






    public List<Callout>getCallouts(String date)
    {
        List<Callout> calloutList=new ArrayList<Callout>();



        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_CALLOUT+
                " WHERE "+DatabaseCreator.CALLOUT_COLUMN_DATE+"='"+date+"'", null);



        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            calloutList.add(convertCursorToCallout(cursor));
            cursor.moveToNext();

        }
        cursor.close();
        return calloutList;

    }


    public void addCallout(Callout c)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.CALLOUT_COLUMN_DATE,c.getDate());
        values.put(DatabaseCreator.CALLOUT_COLUMN_CUSTOMER_NAME,c.getCustomerName());
        values.put(DatabaseCreator.CALLOUT_COLUMN_STREET,c.getStreet());
        values.put(DatabaseCreator.CALLOUT_COLUMN_TOWN,c.getTown());
        values.put(DatabaseCreator.CALLOUT_COLUMN_COUNTY,c.getCounty());
        values.put(DatabaseCreator.CALLOUT_COLUMN_PHONE,c.getPhoneNumber());
        values.put(DatabaseCreator.CALLOUT_COLUMN_PUMP_NR ,c.getPumpNumber());
        values.put(DatabaseCreator.CALLOUT_COLUMN_REPORTED_FAULT ,c.getReportedFault());
        pumpServicesDatabase.insert(DatabaseCreator.TABLE_CALLOUT, null, values);
    }

    public void addPart(Part p)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.PART_COLUMN_PART_NR,p.getPartNumber());
        values.put(DatabaseCreator.PART_COLUMN_DESCRIPTION, p.getPartDescription());

        pumpServicesDatabase.insert(DatabaseCreator.TABLE_PART, null, values);
    }

    public void addPartsList(PartsList pl)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.PARTS_LIST_COLUMN_PART_NR,pl.getPartNumber());
        values.put(DatabaseCreator.PARTS_LIST_COLUMN_PUMP_NR,pl.getPumpNumber());
        values.put(DatabaseCreator.PARTS_LIST_COLUMN_QUANTITY,pl.getQuantity());

        pumpServicesDatabase.insert(DatabaseCreator.TABLE_PARTS_LIST, null, values);
    }

    public List<String>getListOfPartsThisPump(String pumpNumber)
    {
        List<String>listOfPartsThisPump=new ArrayList<String>();

        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT "+DatabaseCreator.PARTS_LIST_COLUMN_PART_NR+" FROM " + DatabaseCreator.TABLE_PARTS_LIST+" WHERE "+ DatabaseCreator.CALLOUT_COLUMN_PUMP_NR+"='"+pumpNumber+"'" , null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            listOfPartsThisPump.add(getPartDescription(cursor.getString(0)));
             cursor.moveToNext();

        }
        cursor.close();
        return listOfPartsThisPump;
    }

    public String getPartDescription(String partNumber)
{
    String partDescription="";
    Cursor cursor=pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_PART + " WHERE " + DatabaseCreator.PART_COLUMN_PART_NR + "="+partNumber, null);
    cursor.moveToFirst();
    if (!cursor.isAfterLast()) {

        partDescription=cursor.getString(1);
    }
    cursor.close();

    return partDescription;
}

    public String getPartNumber(String partDescription)
    {
        String partNumber="";
        Cursor cursor=pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_PART + " WHERE " + DatabaseCreator.PART_COLUMN_DESCRIPTION + "='"+partDescription+"'", null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            partNumber=cursor.getString(0);
        }
        cursor.close();

        return partDescription;
    }



    private Callout convertCursorToCallout(Cursor cursor)
    {
        Callout calloutToReturn=new Callout();
        calloutToReturn.setId(cursor.getInt(0));
        calloutToReturn.setDate(cursor.getString(1));
        calloutToReturn.setCustomerName(cursor.getString(2));
        calloutToReturn.setStreet(cursor.getString(3));
        calloutToReturn.setTown(cursor.getString(4));
        calloutToReturn.setCounty(cursor.getString(5));
        calloutToReturn.setPhoneNumber(cursor.getString(6));
        calloutToReturn.setPumpNumber(cursor.getString(9));
        calloutToReturn.setReportedFault(cursor.getString(7));
        calloutToReturn.setReportText(cursor.getString(8));



        return calloutToReturn;
    }




    public List<String>getPumps()
    {
        List<String> pumpList=new ArrayList<String>();
        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_PUMP, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            pumpList.add(convertCursorToPump(cursor).getPumpNr()+": "+convertCursorToPump(cursor).getPumpDescription());
            cursor.moveToNext();
        }
        cursor.close();
        return pumpList;

    }

    public void addPump(Pump p)
    {        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.PUMP_COLUMN_PUMP_NR,p.getPumpNr());
        values.put(DatabaseCreator.PART_COLUMN_DESCRIPTION, p.getPumpDescription());


        pumpServicesDatabase.insert(DatabaseCreator.TABLE_PUMP, null, values);
    }

    private Pump convertCursorToPump(Cursor cursor)
    {
        Pump pumpToReturn=new Pump();
        pumpToReturn.setPumpNr(cursor.getString(0));
        pumpToReturn.setPumpDescription(cursor.getString(1));
        return pumpToReturn;
    }


    public List<SparesOrderItem>getSparesOrderForReport(int id) {
        List<SparesOrderItem> listOfSparesOrderItems = new ArrayList<SparesOrderItem>();
        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_SPARES_ORDER_ITEM + " WHERE " + DatabaseCreator.CALLOUT_COLUMN_ID + " =" + id, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            listOfSparesOrderItems.add(convertCursorToSparesOrderItem(cursor));
            cursor.moveToNext();

        }
        cursor.close();
        return listOfSparesOrderItems;
    }

    public void addSparesOrderItem(SparesOrderItem so)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.CALLOUT_COLUMN_ID,so.getCalloutId());
        values.put(DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_PART_NR,so.getPartNumber());
        values.put(DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION,so.getPartDescription());
        values.put(DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_QUANTITY,so.getQuantity());

        pumpServicesDatabase.insert(DatabaseCreator.TABLE_SPARES_ORDER_ITEM, null, values);
    }

    public void deleteSparesOrderItem(String description)
    {
        pumpServicesDatabase.delete(DatabaseCreator.TABLE_SPARES_ORDER_ITEM,DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION+"='"+description+"'",null);
    }

    private SparesOrderItem convertCursorToSparesOrderItem(Cursor cursor)
    {
        SparesOrderItem sparesOrderItemToReturn=new SparesOrderItem();
        sparesOrderItemToReturn.setCalloutId(cursor.getInt(1));
        sparesOrderItemToReturn.setPartNumber(cursor.getString(2));
        sparesOrderItemToReturn.setPartDescription(cursor.getString(3));
        sparesOrderItemToReturn.setQuantity(cursor.getInt(4));
        return sparesOrderItemToReturn;
    }






}

