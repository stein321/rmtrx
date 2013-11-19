package cap.mizzou.rmtrx.app.Finances;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    private String[] allColumns = { COLUMN_LOCAL_ID, COLUMN_SERVICE_ID,COLUMN_TO,COLUMN_FROM,COLUMN_DATE };

    public FinanceDb(Context context) {
        dbHelper = new FinanceMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Transaction createTransaction(String from, String to,String description, double amount) {
        ContentValues values = new ContentValues();
//
        values.put(COLUMN_SERVICE_ID,"");
        values.put(COLUMN_DESCRIPTION,description);
        values.put(COLUMN_AMOUNT,amount);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_FROM,from);

        long insertId = database.insert(TABLE_TRANSACTIONS, null, values);
        Cursor cursor = database.query(TABLE_TRANSACTIONS,
                allColumns, COLUMN_LOCAL_ID + " = " + insertId, null,null, null, COLUMN_DATE);
        cursor.moveToFirst();
        Transaction newTransaction = cursorToTransaction(cursor);
        cursor.close();
        sendTractionToServer(from,to,description,amount);
        return newTransaction;

    }

    private void sendTractionToServer(String from, String to, String description, double amount) {
        //To change body of created methods use File | Settings | File Templates.
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

    public List<Transaction> getAllTransactions(String fromUser,String toUser) {
        List<Transaction> transactions = new ArrayList<Transaction>();
         String[] columns={COLUMN_AMOUNT};
        String[] selectionArgument={fromUser,toUser};
        String queryToRun= COLUMN_FROM + " = " + fromUser + " AND " + COLUMN_TO + " = " + toUser;
        Cursor cursor = database.query(TABLE_TRANSACTIONS,columns, null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction transaction  = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return transactions;
    }

    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction transaction = new Transaction();
        transaction.setId(cursor.getLong(0));
        transaction.setUserId(cursor.getString(1));
        transaction.setAmount(cursor.getDouble(2));
        return transaction;
    }
}

