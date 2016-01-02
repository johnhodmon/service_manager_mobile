package ie.hodmon.computing.hodmonpumpservices;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by John on 19/02/2015.
 */
public class DatabaseCreator extends SQLiteOpenHelper

{

    public static final String TABLE_CALLOUT = "TableCallout";
    public static final String TABLE_PICTURE = "TablePicture";
    public static final String PICTURES_COLUMN_PICTURE_ID = "picturesId";
      public static final String PICTURES_COLUMN_JOB_ID = "jobId";
    public static final String PICTURES_COLUMN_BLOB = "blob";
    public static final String PICTURES_COLUMN_BIG_BLOB = "bigBlob";
    public static final String TABLE_PUMP= "TablePump";
    public static final String TABLE_PART = "TablePart";
    public static final String TABLE_PARTS_LIST = "TablePartsList";
    public static final String TABLE_SPARES_ORDER_ITEM = "TableSparesOrderItem";
    public static final String CALLOUT_COLUMN_ID = "calloutId";
    public static final String CALLOUT_COLUMN_ENGINEER_EMAIL = "email";
    public static final String CALLOUT_COLUMN_DATE = "calloutDate";
    public static final String CALLOUT_COLUMN_CUSTOMER_NAME = "name";
    public static final String CALLOUT_COLUMN_STREET = "street";
    public static final String CALLOUT_COLUMN_TOWN = "town";
    public static final String CALLOUT_COLUMN_COUNTY = "county";
    public static final String CALLOUT_COLUMN_PHONE = "phone";
    public static final String CALLOUT_COLUMN_LAT_LNG = "latLng";
    public static final String CALLOUT_COLUMN_PUMP_NR = "pumpNumber";
    public static final String CALLOUT_COLUMN_REPORTED_FAULT = "reportedFault";
    public static final String CALLOUT_COLUMN_REPORT_TEXT = "reportText";
    public static final String PUMP_COLUMN_PUMP_NR = "pumpNr";
    public static final String PUMP_COLUMN_DESCRIPTION="description";
    public static final String PARTS_LIST_COLUMN_ID="id";
    public static final String PARTS_LIST_COLUMN_PUMP_NR="pumpNumber";
    public static final String PARTS_LIST_COLUMN_PART_NR="partNumber";
    public static final String PARTS_LIST_COLUMN_QUANTITY="quantity";
    public static final String SPARES_ORDER_ITEM_COLUMN_ID="id";
    public static final String SPARES_ORDER_ITEM_COLUMN_PART_NR="partNr";
    public static final String SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION="partDescription";
    public static final String SPARES_ORDER_ITEM_COLUMN_QUANTITY="quantity";
    public static final String PART_COLUMN_PART_NR="partNr";
    public static final String PART_COLUMN_DESCRIPTION="description";











    public static final String DATABASE_NAME = "PumpServicesDB.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE_TABLE_CALLOUT = "CREATE TABLE "
            + TABLE_CALLOUT + " ( " + CALLOUT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CALLOUT_COLUMN_ENGINEER_EMAIL + " TEXT NOT NULL, "
            + CALLOUT_COLUMN_DATE + " TEXT NOT NULL, "
            + CALLOUT_COLUMN_CUSTOMER_NAME + " TEXT NOT NULL,"
            + CALLOUT_COLUMN_STREET + " TEXT NOT NULL,"
            + CALLOUT_COLUMN_TOWN + " TEXT NOT NULL,"
            + CALLOUT_COLUMN_COUNTY + " TEXT NOT NULL,"
            + CALLOUT_COLUMN_PHONE + " TEXT NOT NULL,"
            + CALLOUT_COLUMN_REPORTED_FAULT + " TEXT,"
            + CALLOUT_COLUMN_REPORT_TEXT + " TEXT,"
            + CALLOUT_COLUMN_PUMP_NR + " TEXT ,"
            + CALLOUT_COLUMN_LAT_LNG + " TEXT );";

    public static final String DATABASE_CREATE_TABLE_PICTURE = "CREATE TABLE "
            + TABLE_PICTURE + " ( " + PICTURES_COLUMN_PICTURE_ID + "  TEXT PRIMARY KEY, "
            + PICTURES_COLUMN_JOB_ID+ " INTEGER ,"
            + PICTURES_COLUMN_BLOB+ " BLOB );";
          ;

    public static final String DATABASE_CREATE_TABLE_PUMP = "CREATE TABLE "
            + TABLE_PUMP + " ( " + PUMP_COLUMN_PUMP_NR + " TEXT PRIMARY KEY, "
            + PUMP_COLUMN_DESCRIPTION + " TEXT );";

    public static final String DATABASE_CREATE_TABLE_SPARES_ORDER_ITEM = "CREATE TABLE "
            + TABLE_SPARES_ORDER_ITEM + " ( " + SPARES_ORDER_ITEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CALLOUT_COLUMN_ID + " INTEGER , "
            + SPARES_ORDER_ITEM_COLUMN_PART_NR + " TEXT , "
            + SPARES_ORDER_ITEM_COLUMN_PART_DESCRIPTION + " TEXT , "
            + PARTS_LIST_COLUMN_QUANTITY + " INTEGER  );";

  public static final String DATABASE_CREATE_TABLE_PARTS_LIST = "CREATE TABLE "
            + TABLE_PARTS_LIST + " ( "  + PARTS_LIST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PARTS_LIST_COLUMN_PART_NR + " TEXT ,"
            + PARTS_LIST_COLUMN_PUMP_NR + " TEXT , "
            + PARTS_LIST_COLUMN_QUANTITY + " INTEGER NOT NULL );";

    public static final String DATABASE_CREATE_TABLE_PART = "CREATE TABLE "
            + TABLE_PART + " ( " + PART_COLUMN_PART_NR + " TEXT PRIMARY KEY, "
            + PART_COLUMN_DESCRIPTION + " TEXT );";








    public DatabaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_TABLE_CALLOUT);
         db.execSQL(DATABASE_CREATE_TABLE_PUMP);
        db.execSQL(DATABASE_CREATE_TABLE_PART);
       db.execSQL(DATABASE_CREATE_TABLE_PARTS_LIST);
        db.execSQL(DATABASE_CREATE_TABLE_SPARES_ORDER_ITEM);
        db.execSQL(DATABASE_CREATE_TABLE_PICTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseCreator.class.getName(), "upgrading database from " + oldVersion + " to " + newVersion +
                ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUMP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPARES_ORDER_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURE);
        onCreate(db);
    }



}

