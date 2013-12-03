package cap.mizzou.rmtrx.app.Finances;

import Models.Transaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;

import java.util.ArrayList;
import java.util.List;

import static cap.mizzou.rmtrx.app.Finances.FinanceMySQLiteHelper.*;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 4:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceDb {

    // Database fields
    private SQLiteDatabase database;
    private FinanceMySQLiteHelper dbHelper;


    private ResidentDataSource data;



    public FinanceDb(Context context) {
        dbHelper = new FinanceMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createTransaction(String from, String to,String description, double amount) {
        ContentValues values = new ContentValues();
//
        values.put(COLUMN_SERVICE_ID,"");
        values.put(COLUMN_DESCRIPTION,description);
        values.put(COLUMN_AMOUNT,amount);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_FROM,from);

        long insertId = database.insert(TABLE_TRANSACTIONS, null, values);
        Cursor cursor = database.query(TABLE_TRANSACTIONS, null, COLUMN_LOCAL_ID + " = " + insertId, null,null, null, COLUMN_DATE);
        int index=cursor.getColumnIndex(COLUMN_AMOUNT);
        cursor.moveToFirst();
        String id=cursor.getString(index);

        cursor.close();
    }

    public void createTransaction(String from, String to,String description, double amount, String serviceId, int dateAdded) {
        ContentValues values = new ContentValues();
//
        values.put(COLUMN_SERVICE_ID,"");
        values.put(COLUMN_DESCRIPTION,description);
        values.put(COLUMN_AMOUNT,amount);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_FROM,from);
        values.put(COLUMN_SERVICE_ID, serviceId);
        values.put(COLUMN_DATE, dateAdded);

        long insertId = database.insert(TABLE_TRANSACTIONS, null, values);
        Cursor cursor = database.query(TABLE_TRANSACTIONS, null, COLUMN_LOCAL_ID + " = " + insertId, null,null, null, COLUMN_DATE);
        int index=cursor.getColumnIndex(COLUMN_AMOUNT);
        cursor.moveToFirst();
        String id=cursor.getString(index);

        cursor.close();
    }
    public List<Transaction> getAllTransactions(String fromUser,Context context) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String queryToRun= COLUMN_FROM + " = ? " + "AND" + COLUMN_TO + " = ?" ;
        Cursor cursor = database.query(TABLE_TRANSACTIONS,null, null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Transaction transaction  = cursorToTransaction(cursor,context);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return transactions;
    }
    public double amountOwed(String fromId,String toId) {
        double sum=0;
        double amount;
        String[] columns={COLUMN_AMOUNT};
        String[] selectionArgs={fromId,toId};
        String queryCalled=COLUMN_FROM + " = ? " + " AND " + COLUMN_TO + " = ? ";
        Cursor cursor = database.query(TABLE_TRANSACTIONS,null,queryCalled,selectionArgs,null,null,null);

        int index=cursor.getColumnIndex(COLUMN_AMOUNT);
        if(cursor==null) {
            return 0;
        }
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String number=cursor.getString(index);
            amount=Double.parseDouble(number);
            sum=sum+amount;
            cursor.moveToNext();
        }

        return sum;

    }



    public void updateTransaction(int localId, String serviceId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_ID,serviceId);

        long insertId=database.update(TABLE_TRANSACTIONS,values,COLUMN_LOCAL_ID + "=" + localId,null);

    }

    public void deleteTransaction(Transaction transaction) {
        long id = transaction.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(TABLE_TRANSACTIONS, COLUMN_LOCAL_ID
                + " = " + id, null);
    }



    private Transaction cursorToTransaction(Cursor cursor,Context context) {

        setData(new ResidentDataSource(context));
        getData().open();

        String id=cursor.getString(cursor.getColumnIndex(COLUMN_TO));
        String name=data.findResident(id).getFirstName();

        Transaction transaction = new Transaction();
        transaction.setName(name);
        transaction.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_LOCAL_ID)));
        transaction.setServiceId(cursor.getString(cursor.getColumnIndex(COLUMN_SERVICE_ID)));
        transaction.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT)));
        transaction.setToUser(cursor.getString(cursor.getColumnIndex(COLUMN_TO)));
        transaction.setFromUser(cursor.getString(cursor.getColumnIndex(COLUMN_FROM)));
        transaction.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        return transaction;
    }
    public ResidentDataSource getData() {
        return data;
    }

    public void setData(ResidentDataSource data) {
        this.data = data;
    }

}
//private long id;
//private String serviceId;
//private String description;
//private double amount;
//private String toUser;
//private String fromUser;
//private String date;
