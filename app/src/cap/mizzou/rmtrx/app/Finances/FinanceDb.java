package cap.mizzou.rmtrx.app.Finances;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cap.mizzou.rmtrx.app.BulletinBoard.BBMySQLiteHelper;
import cap.mizzou.rmtrx.app.BulletinBoard.Post;
import cap.mizzou.rmtrx.app.Finances.Transaction;

import java.util.ArrayList;
import java.util.List;

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
    private String[] allColumns = { FinanceMySQLiteHelper.COLUMN_ID, FinanceMySQLiteHelper.COLUMN_USERID,
            FinanceMySQLiteHelper.COLUMN_AMOUNT, FinanceMySQLiteHelper.COLUMN_NATURE, FinanceMySQLiteHelper.COLUMN_DATE };

    public FinanceDb(Context context) {
        dbHelper = new FinanceMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void drop(){
        dbHelper.onUpgrade(database, 1, 2);
    }

    public Transaction createTransaction(String userid, double amount, String nature, String date) {
        ContentValues values = new ContentValues();
        values.put(FinanceMySQLiteHelper.COLUMN_USERID, userid);
        values.put(FinanceMySQLiteHelper.COLUMN_AMOUNT, amount);
        values.put(FinanceMySQLiteHelper.COLUMN_NATURE, nature);
        values.put(FinanceMySQLiteHelper.COLUMN_DATE, date);
       // values.put(FinanceMySQLiteHelper.COLUMN_FROMUSER, from);
        long insertId = database.insert(FinanceMySQLiteHelper.TABLE_TRANSACTIONS, null,
                values);
        Cursor cursor = database.query(FinanceMySQLiteHelper.TABLE_TRANSACTIONS,
                allColumns, FinanceMySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Transaction newTransaction = cursorToTransaction(cursor);
        cursor.close();
        return newTransaction;

    }

    public void deleteTransaction(Transaction transaction) {
        long id = transaction.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(FinanceMySQLiteHelper.TABLE_TRANSACTIONS, FinanceMySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Transaction> getAllTransactions(String userid) {
        List<Transaction> transactions = new ArrayList<Transaction>();

        Cursor cursor = database.rawQuery("SELECT * FROM transactions WHERE userid = ?", new String [] {String.valueOf(userid)});

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
        transaction.setNature(cursor.getString(3));
        transaction.setDate(cursor.getString(4));
       // transaction.setFrom(cursor.getString(5));
        return transaction;
    }
}

