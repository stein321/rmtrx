package cap.mizzou.rmtrx.app.Finances;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cap.mizzou.rmtrx.app.TestDbActivity.Comment;

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
    private String[] allCreditColumns = { DatabaseHelper.COLUMN_CREDITID, DatabaseHelper.COLUMN_CREDITROOMATE, DatabaseHelper.COLUMN_CREDITAMOUNT };
    private String[] allChargeColumns = { DatabaseHelper.COLUMN_CHARGEID, DatabaseHelper.COLUMN_CHARGEROOMATE, DatabaseHelper.COLUMN_CHARGEAMOUNT };


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

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createCreditRecord(String roommate, String amount) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CREDITROOMATE, roommate);
        values.put(DatabaseHelper.COLUMN_CREDITAMOUNT, amount);
        long insertId = database.insert(DatabaseHelper.TABLE_CREDITS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_CREDITS,
                allCreditColumns, DatabaseHelper.COLUMN_CREDITID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
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

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }


//    @Override
//    protected void onResume() {
//        datasource.open();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        datasource.close();
//        super.onPause();
//    }

    public class DatabaseHelper extends SQLiteOpenHelper{

        public static final String TABLE_CREDITS = "Credits";
        public static final String COLUMN_CREDITID = "_id";
        public static final String COLUMN_CREDITROOMATE = "Roomate";
        public static final String COLUMN_CREDITAMOUNT = "Amount";

        public static final String TABLE_CHARGES = "Credits";
        public static final String COLUMN_CHARGEID = "_id";
        public static final String COLUMN_CHARGEROOMATE = "Roomate";
        public static final String COLUMN_CHARGEAMOUNT = "Amount";

        private static final String DATABASE_NAME = "Credit.db";

        private static final int DATABASE_VERSION = 2;


        // Database creation sql statement
        private static final String DATABASE_CREATE_CREDITTABLE = "create table " + TABLE_CREDITS + "(" + COLUMN_CREDITID + " integer primary key autoincrement, " + COLUMN_CREDITROOMATE + " text not null, " + COLUMN_CREDITAMOUNT + " text not null);";

        private static final String DATABASE_CREATE_CHARGETABLE = "create table " + TABLE_CHARGES + "(" + COLUMN_CHARGEID + " integer primary key autoincrement, " + COLUMN_CHARGEROOMATE + " text not null, " + COLUMN_CHARGEAMOUNT + " text not null);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Method is called during creation of the database
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE_CREDITTABLE);
            database.execSQL(DATABASE_CREATE_CHARGETABLE);
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
