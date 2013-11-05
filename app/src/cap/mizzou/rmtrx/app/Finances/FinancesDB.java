package cap.mizzou.rmtrx.app.Finances;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


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
    private String[] allTransactionColumns = { DatabaseHelper.COLUMN_TRANSACTIONID, DatabaseHelper.COLUMN_USERID, DatabaseHelper.COLUMN_TRANSACTIONAMOUNT };



    public FinancesDB(Context context){
        dbHelper = new DatabaseHelper(context);
        // 1. get reference to writable/readable DB
        database = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createTransactionRecord(String userid, double amount) {



        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_USERID, userid);
        values.put(dbHelper.COLUMN_TRANSACTIONAMOUNT, amount);

        // 3. insert
        database.insert(dbHelper.TABLE_TRANSACTIONS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        database.close();
    }

    public List getTransactionRecords(String userid){

        List<Double> list=new ArrayList<Double>();
        //Create query to retrieve all transaction records for specified user_id
        String query= "SELECT * FROM " + dbHelper.TABLE_TRANSACTIONS + " WHERE " + dbHelper.COLUMN_USERID + "=" + userid + ";";
        Cursor  cursor = database.rawQuery(query,null);

        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                double amount = cursor.getDouble(cursor
                        .getColumnIndex(dbHelper.COLUMN_TRANSACTIONAMOUNT));

                list.add(amount);
                cursor.moveToNext();
            }
        }


    return list;}
    }






    class DatabaseHelper extends SQLiteOpenHelper{

        public static final String TABLE_TRANSACTIONS = "Transactions";
        public static final String COLUMN_TRANSACTIONID = "_id";
        public static final String COLUMN_USERID = "User ID";
        public static final String COLUMN_TRANSACTIONAMOUNT = "Transaction Amount";



        private static final String DATABASE_NAME = "Transaction.db";

        private static final int DATABASE_VERSION = 2;


        // Database creation sql statement
        private static final String DATABASE_CREATE_TRANSACTIONTABLE = "create table " + TABLE_TRANSACTIONS + "(" + COLUMN_TRANSACTIONID + " integer primary key autoincrement, " + COLUMN_USERID + " text not null, " + COLUMN_TRANSACTIONAMOUNT + " text not null);";



        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Method is called during creation of the database
        @Override
        public void onCreate(SQLiteDatabase database) {

            database.execSQL(DATABASE_CREATE_TRANSACTIONTABLE);
        }

        // Method is called during an upgrade of the database,
        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion,
                              int newVersion) {
            Log.w(DatabaseHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            database.execSQL("DROP TABLE IF EXISTS Transactions");
            this.onCreate(database);
        }

    }

