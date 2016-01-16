package ie.hodmon.computing.service_manager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ie.hodmon.computing.service_manager.model.PartsList;
import ie.hodmon.computing.service_manager.controller.ReportPhoto;
import ie.hodmon.computing.service_manager.model.JobPart;
import ie.hodmon.computing.service_manager.model.Job;
import ie.hodmon.computing.service_manager.model.Part;
import ie.hodmon.computing.service_manager.model.Product;

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

    public Job getSingleCallout(int id)
    {
        Job jobToReturn =new Job();
        Cursor cursor=pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_CALLOUT+" WHERE "+DatabaseCreator.CALLOUT_COLUMN_ID+" ="+id, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            jobToReturn = convertCursorToCallout(cursor);
        }

        cursor.close();
        return jobToReturn;
    }


    public void saveReportPhoto(String photoId,int jobId,byte[] blob)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.PICTURES_COLUMN_PICTURE_ID,photoId);
        values.put(DatabaseCreator.PICTURES_COLUMN_JOB_ID,jobId);
        values.put(DatabaseCreator.PICTURES_COLUMN_BLOB, blob);


        pumpServicesDatabase.insert(DatabaseCreator.TABLE_PICTURE, null, values);
    }

    public void deleteReportPhoto(String photoId)
    {
        pumpServicesDatabase.delete(DatabaseCreator.TABLE_PICTURE, DatabaseCreator.PICTURES_COLUMN_PICTURE_ID + "='"+photoId+"'", null);
    }

    public List<ReportPhoto>getReportImages(int jobId)
    {

        List<ReportPhoto>photoList=new ArrayList<ReportPhoto>();

        Log.v("gallery select", "entering get report images");
        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_PICTURE+
                " WHERE "+DatabaseCreator.PICTURES_COLUMN_JOB_ID+"='"+jobId+"'", null);



        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Log.v("gallery select", "calling convert photo");
            photoList.add(convertCursorToReportPhoto(cursor));

            cursor.moveToNext();

        }


        cursor.close();
        return photoList;
    }

    public void editCallout(Job c)
{

    ContentValues values=new ContentValues();
    values.put(DatabaseCreator.CALLOUT_COLUMN_ENGINEER_EMAIL,c.getEngineerEmail());
    values.put(DatabaseCreator.CALLOUT_COLUMN_DATE,c.getDate());
    values.put(DatabaseCreator.CALLOUT_COLUMN_CUSTOMER_NAME,c.getCustomerName());
    values.put(DatabaseCreator.CALLOUT_COLUMN_STREET,c.getStreet());
    values.put(DatabaseCreator.CALLOUT_COLUMN_TOWN,c.getTown());
    values.put(DatabaseCreator.CALLOUT_COLUMN_COUNTY,c.getCounty());
    values.put(DatabaseCreator.CALLOUT_COLUMN_PHONE,c.getPhoneNumber());
    values.put(DatabaseCreator.CALLOUT_COLUMN_PUMP_NR ,c.getPumpNumber());
    values.put(DatabaseCreator.CALLOUT_COLUMN_REPORTED_FAULT ,c.getReportedFault());
    values.put(DatabaseCreator.CALLOUT_COLUMN_REPORT_TEXT, c.getReportText());
    LatLng latLng=c.getLatLng();
    String latLngAsString=latLng.latitude+","+latLng.longitude;
    Log.v("test lat long insert", latLngAsString);
    values.put(DatabaseCreator.CALLOUT_COLUMN_LAT_LNG, latLngAsString);


    pumpServicesDatabase.update(DatabaseCreator.TABLE_CALLOUT, values, DatabaseCreator.CALLOUT_COLUMN_ID + "=" + c.getId(), null);
}

    public void editSparePartsItem(int calloutId,int quantity,String description)
    {

        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_QUANTITY,quantity);


        pumpServicesDatabase.update(DatabaseCreator.TABLE_SPARES_ORDER_ITEM,values,DatabaseCreator.CALLOUT_COLUMN_ID+"="+calloutId+" AND "
                +DatabaseCreator.SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION+"='"+description+"'",null);
    }






    public List<Job>getCallouts(String engineerEmail, String date)
    {
        List<Job> jobList =new ArrayList<Job>();



        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_CALLOUT+
                " WHERE "+DatabaseCreator.CALLOUT_COLUMN_DATE+"='"+date+"' AND "+DatabaseCreator.CALLOUT_COLUMN_ENGINEER_EMAIL+"='"+engineerEmail+"'", null);



        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            jobList.add(convertCursorToCallout(cursor));
            cursor.moveToNext();

        }
        cursor.close();
        return jobList;

    }


    public void addCallout(Job c)
    {
        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.CALLOUT_COLUMN_ENGINEER_EMAIL,c.getEngineerEmail());
        values.put(DatabaseCreator.CALLOUT_COLUMN_DATE,c.getDate());
        values.put(DatabaseCreator.CALLOUT_COLUMN_CUSTOMER_NAME,c.getCustomerName());
        values.put(DatabaseCreator.CALLOUT_COLUMN_STREET,c.getStreet());
        values.put(DatabaseCreator.CALLOUT_COLUMN_TOWN,c.getTown());
        values.put(DatabaseCreator.CALLOUT_COLUMN_COUNTY,c.getCounty());
        values.put(DatabaseCreator.CALLOUT_COLUMN_PHONE,c.getPhoneNumber());
        values.put(DatabaseCreator.CALLOUT_COLUMN_PUMP_NR ,c.getPumpNumber());
        values.put(DatabaseCreator.CALLOUT_COLUMN_REPORTED_FAULT, c.getReportedFault());
        LatLng latLng=c.getLatLng();
        String latLngAsString=latLng.latitude+","+latLng.longitude;
        Log.v("test lat long insert",latLngAsString);
        values.put(DatabaseCreator.CALLOUT_COLUMN_LAT_LNG, latLngAsString);
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



    private Job convertCursorToCallout(Cursor cursor)
{
    Job jobToReturn =new Job();
    jobToReturn.setId(cursor.getInt(0));
    jobToReturn.setEngineerEmail(cursor.getString(1));
    jobToReturn.setDate(cursor.getString(2));
    jobToReturn.setCustomerName(cursor.getString(3));
    jobToReturn.setStreet(cursor.getString(4));
    jobToReturn.setTown(cursor.getString(5));
    jobToReturn.setCounty(cursor.getString(6));
    jobToReturn.setPhoneNumber(cursor.getString(7));
    jobToReturn.setPumpNumber(cursor.getString(10));
    jobToReturn.setReportedFault(cursor.getString(8));
    jobToReturn.setReportText(cursor.getString(9));
    String[] latlongArray = (cursor.getString(11).split(","));
    double latitude = Double.parseDouble(latlongArray[0]);
    double longitude = Double.parseDouble(latlongArray[1]);
    LatLng latLng=new LatLng(latitude,longitude);
    jobToReturn.setLatLng(latLng);



    return jobToReturn;
}


    private ReportPhoto convertCursorToReportPhoto(Cursor cursor)
    {
        Log.v("gallery select","convert cursor to photo");
        ReportPhoto photoToReturn=new ReportPhoto();

        photoToReturn.setPhotoId(cursor.getString(0));
        photoToReturn.setJobId(cursor.getInt(1));
        photoToReturn.setBlob(cursor.getBlob(2));




        return photoToReturn;
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

    public void addPump(Product p)
    {        ContentValues values=new ContentValues();
        values.put(DatabaseCreator.PUMP_COLUMN_PUMP_NR,p.getPumpNr());
        values.put(DatabaseCreator.PART_COLUMN_DESCRIPTION, p.getPumpDescription());


        pumpServicesDatabase.insert(DatabaseCreator.TABLE_PUMP, null, values);
    }

    private Product convertCursorToPump(Cursor cursor)
    {
        Product productToReturn =new Product();
        productToReturn.setPumpNr(cursor.getString(0));
        productToReturn.setPumpDescription(cursor.getString(1));
        return productToReturn;
    }


    public List<JobPart>getSparesOrderForReport(int id) {
        List<JobPart> listOfJobParts = new ArrayList<JobPart>();
        Cursor cursor = pumpServicesDatabase.rawQuery("SELECT * FROM " + DatabaseCreator.TABLE_SPARES_ORDER_ITEM + " WHERE " + DatabaseCreator.CALLOUT_COLUMN_ID + " =" + id, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            listOfJobParts.add(convertCursorToSparesOrderItem(cursor));
            cursor.moveToNext();

        }
        cursor.close();
        return listOfJobParts;
    }

    public void addSparesOrderItem(JobPart so)
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

    private JobPart convertCursorToSparesOrderItem(Cursor cursor)
    {
        JobPart jobPartToReturn =new JobPart();
        jobPartToReturn.setCalloutId(cursor.getInt(1));
        jobPartToReturn.setPartNumber(cursor.getString(2));
        jobPartToReturn.setPartDescription(cursor.getString(3));
        jobPartToReturn.setQuantity(cursor.getInt(4));
        return jobPartToReturn;
    }






}

