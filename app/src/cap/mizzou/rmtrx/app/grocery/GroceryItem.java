package cap.mizzou.rmtrx.app.grocery;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

public class GroceryItem
{


		private GroceryItem() {}
		
	    public static final String TableName = "item";
		public static final Uri ContentUri = Uri.parse("content://" + GroceryActivity.AUTHORITY + "/" + TableName);
		public static final Uri CONTENT_LIST_URI= Uri.parse("content://" + GroceryActivity.AUTHORITY + "/" + GroceryList.TableName + TableName);
		public static final String ContentType = "vnd.android.cursor.item";

		public static final class Columns implements BaseColumns
        {
			public static final String ListID = "list_id";
            public static final String ServiceId="service_id";
			public static final String NAME = "name";
			public static final String IsChecked = "is_checked";
			public static final String CreatedDate = "created";
			public static final String ModifiedDate = "modified";
		}



    //Sort
    public enum Sort {
        name(Columns.NAME), age(Columns._ID), status_name(
                Columns.IsChecked + "," + name), status_age(
                Columns.IsChecked + "," + age);

        private String sortString;

        Sort(String sortString) {
            this.sortString = sortString;
        }

        @Override
        public String toString() {
            return sortString;
        }

        public String enumString() {
            return super.toString();
        }
    }

    public static final String DEFAULT_SORT_ORDER = Columns._ID;






    public static final String CREATE_STATEMENT =
        "CREATE TABLE " + TableName + " ("
        + Columns._ID + " INTEGER PRIMARY KEY,"
        + Columns.ListID + " INTEGER,"
        + Columns.ServiceId + " TEXT, "
        + Columns.NAME + " TEXT,"
        + Columns.IsChecked + " INTEGER,"
        + Columns.CreatedDate + " INTEGER,"
        + Columns.ModifiedDate + " INTEGER"
        + ");";

	    public static final String[] PROJECTION = new String[]
        {
            Columns._ID, // 0
            Columns.NAME, // 1
            Columns.IsChecked
	    };
		
        public static ContentValues contentValues(int listId, String name)
        {
            ContentValues values = new ContentValues();
            values.put(Columns.ListID, listId);
            values.put(Columns.NAME, name);
            return values;
        }
        
        public static ContentValues contentValues(Boolean isChecked)
        {
            ContentValues values = new ContentValues();
            values.put(Columns.IsChecked, isChecked ? 1 : 0);
            return values;
        }

	}
