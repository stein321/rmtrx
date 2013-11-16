package cap.mizzou.rmtrx.app.grocery;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

public final class GroceryList {


	private GroceryList() {}

	public static final String TableName = "list";
	public static final Uri ContentUri = Uri.parse("content://" + GroceryActivity.AUTHORITY + "/" + TableName);
	public static final String ContentType = "vnd.android.cursor.dir/";

    public static final class Columns implements BaseColumns {
		private Columns() {}

		public static final String NAME = "name";
		public static final String DEFAULT = "is_default";
		public static final String CreatedDate = "created";
		public static final String ModifiedDate = "modified";
        public static final String SERVICE_ID = "service_id";
    }
    
	public static final String DEFAULT_SORT_ORDER = Columns.DEFAULT + " DESC, " + Columns.NAME;
    
	public static final String CREATE_STATEMENT = 
		"CREATE TABLE " + TableName + " ("
		+ Columns._ID + " INTEGER PRIMARY KEY,"
        + Columns.SERVICE_ID + " TEXT, "
		+ Columns.NAME + " TEXT,"
		+ Columns.DEFAULT + " INTEGER,"
		+ Columns.CreatedDate + " INTEGER,"
		+ Columns.ModifiedDate + " INTEGER"
		+ ");";
	
	public static final String[] PROJECTION = new String[] {
			Columns._ID, // 0
			Columns.NAME, // 1
	};
	
	public static ContentValues contentValues(boolean isDefault)
	{
		ContentValues values = new ContentValues();
		values.put(Columns.DEFAULT, isDefault);
		return values;
	}
	
	public static ContentValues contentValues(String name)
	{
		return contentValues(name, true);
	}	
	
   public static ContentValues contentValues(String name, Boolean isDefault) {
        ContentValues values = new ContentValues();
        values.put(Columns.NAME, name);
        values.put(Columns.DEFAULT, isDefault ? 1 : 0);
        return values;
    }
}
