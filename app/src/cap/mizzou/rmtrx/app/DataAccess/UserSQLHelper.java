package cap.mizzou.rmtrx.app.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/8/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_RESIDENTS ="Residents";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID="userId";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRST_NAME="firstName";
    public static final String COLUMN_LAST_NAME="lastName";

    public static final String DATABASE_NAME = "roommates.db";
    private static final int DATABASE_VERSION = 1;

    public static final String AUTHORITY = "cap.mizzou.rmtrx.app.DataAccess.UserSQLHelper";
    public static final Uri ContentUri = Uri.parse("content://" + UserSQLHelper.AUTHORITY + "/" + TABLE_RESIDENTS);


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " +
            TABLE_RESIDENTS + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_USER_ID + " text not null, " +
            COLUMN_EMAIL + " text not null, " +
            COLUMN_FIRST_NAME + " text not null, " +
            COLUMN_LAST_NAME +   " text not null );";

    public UserSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserSQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESIDENTS);
        onCreate(db);
    }
}
