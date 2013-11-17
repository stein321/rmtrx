package cap.mizzou.rmtrx.app.grocery;

import android.content.*;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;



public class GroceryDB extends ContentProvider {
	
    private static final String TAG = "GroceryDB";
    private static final String DatabaseName = "grocery.db";
    private static final int DATABASE_VERSION = 5;
    private static HashMap <String, String> GroceryListProjectionMap;
    private static HashMap <String, String> GroceryItemProjectionMap;
    private static final int GetList = 1;
    private static final int GetListID = 2;
    private static final int GetItem = 3;
    private static final int GetItemID = 4;
    private static final UriMatcher groceryUriMatcher;



    @Override
    public String getType(Uri uri) {
        switch (groceryUriMatcher.match(uri)) {
        case GetList:
        case GetListID:
            return GroceryList.ContentType;

        case GetItem:
        case GetItemID:
            return GroceryItem.ContentType;


        }
        return null;
    }




    static {
        groceryUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        groceryUriMatcher.addURI(GroceryActivity.AUTHORITY, GroceryList.TableName, GetList);
        groceryUriMatcher.addURI(GroceryActivity.AUTHORITY, GroceryList.TableName + "/#", GetListID);
        groceryUriMatcher.addURI(GroceryActivity.AUTHORITY, GroceryItem.TableName, GetItem);
        groceryUriMatcher.addURI(GroceryActivity.AUTHORITY, GroceryItem.TableName + "/#", GetItemID);

        GroceryListProjectionMap = new HashMap<String, String>();
        GroceryListProjectionMap.put(GroceryList.Columns._ID, GroceryList.Columns._ID);
        GroceryListProjectionMap.put(GroceryList.Columns.NAME, GroceryList.Columns.NAME);
        GroceryListProjectionMap.put(GroceryList.Columns.DEFAULT, GroceryList.Columns.DEFAULT);
        GroceryListProjectionMap.put(GroceryList.Columns.CreatedDate, GroceryList.Columns.CreatedDate);
        GroceryListProjectionMap.put(GroceryList.Columns.ModifiedDate, GroceryList.Columns.ModifiedDate);
        GroceryListProjectionMap.put(GroceryList.Columns.SERVICE_ID, GroceryList.Columns.SERVICE_ID);

        GroceryItemProjectionMap = new HashMap<String, String>();
        GroceryItemProjectionMap.put(GroceryItem.Columns._ID, GroceryItem.Columns._ID);
        GroceryItemProjectionMap.put(GroceryItem.Columns.NAME, GroceryItem.Columns.NAME);
        GroceryItemProjectionMap.put(GroceryItem.Columns.IsChecked, GroceryItem.Columns.IsChecked);
        GroceryItemProjectionMap.put(GroceryItem.Columns.CreatedDate, GroceryItem.Columns.CreatedDate);
        GroceryItemProjectionMap.put(GroceryItem.Columns.ModifiedDate, GroceryItem.Columns.ModifiedDate);
        GroceryItemProjectionMap.put(GroceryItem.Columns.ServiceId,GroceryItem.Columns.ServiceId);
    }





    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DatabaseName, null, DATABASE_VERSION);}
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(GroceryList.CREATE_STATEMENT);
            db.execSQL(GroceryItem.CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + GroceryItem.TableName);
            db.execSQL("DROP TABLE IF EXISTS " + GroceryList.TableName);
            onCreate(db);
        }
    }



    private DatabaseHelper mOpenHelper;



    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String contentType = getType(uri);

        if(contentType == GroceryList.ContentType) {
            qb.setTables(GroceryList.TableName);
            qb.setProjectionMap(GroceryListProjectionMap);
            /*if (TextUtils.isEmpty(sortOrder)) {
            	sortOrder = GroceryList.DEFAULT_SORT_ORDER;
            }*/
        } else if(contentType == GroceryItem.ContentType) {
            qb.setTables(GroceryItem.TableName);
            qb.setProjectionMap(GroceryItemProjectionMap);
            /*if (TextUtils.isEmpty(sortOrder)) {
            	sortOrder = GroceryItem.DEFAULT_SORT_ORDER;
            }*/
        } else {}



        switch(groceryUriMatcher.match(uri)) {
        case GetListID:
            qb.appendWhere(GroceryList.Columns._ID + "=" + uri.getPathSegments().get(1));
            break;
        case GetItemID:
            qb.appendWhere(GroceryItem.Columns._ID + "=" + uri.getPathSegments().get(1));
            break;
        }

        // get database and run query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);


        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }




    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId;

        switch (groceryUriMatcher.match(uri)) {
        case GetList:

             //move items
	        if (!values.containsKey(GroceryList.Columns.CreatedDate)) {
	            values.put(GroceryList.Columns.CreatedDate, now);
	        }

	        if (!values.containsKey(GroceryList.Columns.ModifiedDate)) {
	            values.put(GroceryList.Columns.ModifiedDate, now);
	        }

	        if (!values.containsKey(GroceryList.Columns.NAME)) {
	            Resources r = Resources.getSystem();
	            values.put(GroceryList.Columns.NAME, r.getString(android.R.string.untitled));
	        }

	        rowId = db.insert(GroceryList.TableName, GroceryList.Columns.NAME, values);
	        if (rowId > 0) {
	            Uri noteUri = ContentUris.withAppendedId(GroceryList.ContentUri, rowId);
	            getContext().getContentResolver().notifyChange(noteUri, null);
	            return noteUri;
	        }
	        break;
        case GetItem:

	        if (!values.containsKey(GroceryItem.Columns.CreatedDate)) {
	            values.put(GroceryItem.Columns.CreatedDate, now);
	        }

	        if (!values.containsKey(GroceryItem.Columns.ModifiedDate)) {
	            values.put(GroceryItem.Columns.ModifiedDate, now);
	        }

	        if (!values.containsKey(GroceryItem.Columns.NAME)) {
	            Resources r = Resources.getSystem();
	            values.put(GroceryItem.Columns.NAME, r.getString(android.R.string.untitled));
	        }

            if (!values.containsKey(GroceryItem.Columns.IsChecked)) {
                values.put(GroceryItem.Columns.IsChecked, 0);
            }

	       	rowId = db.insert(GroceryItem.TableName, GroceryItem.Columns.NAME, values);
	        if (rowId > 0) {
	            Uri noteUri = ContentUris.withAppendedId(GroceryItem.ContentUri, rowId);
	            getContext().getContentResolver().notifyChange(noteUri, null);
	            return noteUri;
	        }
	        break;
        }

        throw new SQLException("Failed to insert row: " + uri);

    }






    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
        String id;
        switch (groceryUriMatcher.match(uri)) {
        case GetList:
            count = db.delete(GroceryList.TableName, where, whereArgs);
            break;

        case GetListID:
            id = uri.getPathSegments().get(1);
            count = db.delete(GroceryList.TableName, GroceryList.Columns._ID + "=" + id
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case GetItem:
            count = db.delete(GroceryItem.TableName, where, whereArgs);
            break;

        case GetItemID:
        	id = uri.getPathSegments().get(1);
            count = db.delete(GroceryItem.TableName, GroceryItem.Columns._ID + "=" + id
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;


        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }



    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count=0;
        String noteId;
        switch (groceryUriMatcher.match(uri)) {
        case GetList:
            count = db.update(GroceryList.TableName, values, where, whereArgs);
            break;

        case GetListID:
            noteId = uri.getPathSegments().get(1);
            count = db.update(GroceryList.TableName, values, GroceryList.Columns._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case GetItem:
            count = db.update(GroceryItem.TableName, values, where, whereArgs);
            break;

        case GetItemID:
            noteId = uri.getPathSegments().get(1);
            count = db.update(GroceryItem.TableName, values, GroceryItem.Columns._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;


        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;}

}
