package cap.mizzou.rmtrx.app.Finances;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 3:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceMySQLiteHelper extends SQLiteOpenHelper{


        public static final String TABLE_TRANSACTIONS   = "transactions";
        public static final String COLUMN_LOCAL_ID      = "_id";
        public static final String COLUMN_SERVICE_ID    = "service_id";
        public static final String COLUMN_DESCRIPTION   = "description";
        public static final String COLUMN_AMOUNT        = "amount";
        public static final String COLUMN_TO            = "toUser";
        public static final String COLUMN_FROM          = "fromUser";
        public static final String COLUMN_DATE          = "date";

        private static final String DATABASE_NAME = "rmtrx.db";
        private static final int DATABASE_VERSION = 3;

        // Database creation sql statement
        private static final String DATABASE_CREATE = "create table "
                + TABLE_TRANSACTIONS    + "("
                + COLUMN_LOCAL_ID       + " integer primary key autoincrement, "
                + COLUMN_SERVICE_ID     + " text not null, "
                + COLUMN_DESCRIPTION    + " text not null, "
                + COLUMN_AMOUNT         + " real,          "
                + COLUMN_FROM           + " text not null, "
                + COLUMN_TO             + " text not null, "
                + COLUMN_DATE           + " default CURRENT_DATE)";

        public FinanceMySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(FinanceMySQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
            onCreate(db);
        }

    }

