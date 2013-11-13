package cap.mizzou.rmtrx.app.DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static cap.mizzou.rmtrx.app.DataAccess.UserSQLHelper.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/8/13
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResidentDataSource {
    private SQLiteDatabase database;
    private UserSQLHelper sqlHelper;
    private String[] allColumns = {COLUMN_ID,COLUMN_USER_ID,COLUMN_EMAIL,COLUMN_FIRST_NAME,COLUMN_LAST_NAME};


    public ResidentDataSource(Context context) {
        sqlHelper= new UserSQLHelper(context);
    }

    public void open() {
        database =sqlHelper.getWritableDatabase();
    }
    public void close() {
        sqlHelper.close();
    }

    public Resident addResident(String userID,String email, String firstName, String lastName) {
        ContentValues values=new ContentValues();
        values.put( COLUMN_USER_ID, userID);
        values.put( COLUMN_EMAIL, email);
        values.put( COLUMN_FIRST_NAME, firstName);
        values.put( COLUMN_LAST_NAME, lastName);

        long insertID=database.insert(TABLE_RESIDENTS,null,values);

        Cursor cursor=database.query(TABLE_RESIDENTS,allColumns,COLUMN_ID + "=" + insertID,null,null,null,null);
        cursor.moveToFirst();
        Resident newResident=cursorToResident(cursor);
        cursor.close();
        return newResident;
    }

    public List<Resident> getAllResidents() {
            List<Resident> residents=new ArrayList<Resident>();
            Cursor cursor=database.query(TABLE_RESIDENTS,allColumns,null,null,null,null,null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                Resident resident = cursorToResident(cursor);
                residents.add(resident);
                cursor.moveToNext();
            }
        cursor.close();
        return residents;
    }
    private Resident cursorToResident(Cursor cursor) {
        Resident resident=new Resident();
        resident.setId(cursor.getLong(0));
        resident.setUserID(cursor.getString(1));
        resident.setEmail(cursor.getString(2));
        resident.setFirstName(cursor.getString(3));
        resident.setLastName(cursor.getString(4));
        return resident;
    }
}
