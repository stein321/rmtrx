package cap.mizzou.rmtrx.app.BulletinBoard;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 2:02 AM
 * To change this template use File | Settings | File Templates.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BBMySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERID= "userid";
    public static final String COLUMN_POST = "post";
    public static final String COLUMN_POSTDETAILS = "details";

    public static final String TABLE_LIKES = "likes";
    public static final String COLUMN_LIKEID = "_id";
    public static final String COLUMN_POSTID= "postid";
    public static final String COLUMN_NAME= "name";


    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATEPOSTTABLE = "create table "
            + TABLE_POSTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_USERID
            + " text not null, " + COLUMN_POST + " text not null, " + COLUMN_POSTDETAILS
            + " text not null);";

    private static final String DATABASE_CREATELIKETABLE = "create table "
            + TABLE_LIKES + "(" + COLUMN_LIKEID
            + " integer primary key autoincrement, " + COLUMN_POSTID
            + " integer not null, " + COLUMN_NAME
            + " text not null);";

    public BBMySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATEPOSTTABLE);
        database.execSQL(DATABASE_CREATELIKETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BBMySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        onCreate(db);
    }

}