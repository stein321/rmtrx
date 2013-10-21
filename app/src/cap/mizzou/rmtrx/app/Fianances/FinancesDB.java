package cap.mizzou.rmtrx.app.Fianances;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.content.*;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/20/13
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FinancesDB {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public final static String RmName="Name";
    public final static String TransactionAmount="Amount";
    public final static String CreditTable="Credits"; // name of credit table
    public final static String ChargeTable="Charges"; // name of charge table

    public final static String CreditID="";
    public final static String ChargeID="";



    public FinancesDB(Context context){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createCreditRecord(String id, String name, String amount){
        ContentValues values = new ContentValues();
        values.put(CreditID, id);
        values.put(RmName, name);
        values.put(TransactionAmount, amount);
        return database.insert(CreditTable, null, values);
    }

    public long createChargeRecord(String id, String name, String amount){
        ContentValues values = new ContentValues();
        values.put(ChargeID, id);
        values.put(RmName, name);
        values.put(TransactionAmount, amount);
        return database.insert(ChargeTable, null, values);
    }


    public void selectCreditRecord() {
        String[] cols = new String[] {CreditID, RmName, TransactionAmount};
        Cursor mCursor = database.query(true, CreditTable,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();


        }
        //return mCursor; // iterate to get each value.
    }





    public class DatabaseHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "DBName";

        private static final int DATABASE_VERSION = 2;


        // Database creation sql statement
        private static final String DATABASE_CREATE_CredTable ="create table Credits( _id integer primary key,name text not null, amount text not null);";

        private static final String DATABASE_CREATE_CharTable ="create table Charges( _id integer primary key,name text not null, amount text not null);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Method is called during creation of the database
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE_CredTable);
            database.execSQL(DATABASE_CREATE_CharTable);
        }

        // Method is called during an upgrade of the database,
        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion,
                              int newVersion) {
            Log.w(DatabaseHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            database.execSQL("DROP TABLE IF EXISTS Credits");
            onCreate(database);
        }

    }
}
