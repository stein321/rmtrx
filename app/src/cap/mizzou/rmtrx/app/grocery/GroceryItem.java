package cap.mizzou.rmtrx.app.grocery;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

public class GroceryItem {


		private GroceryItem() {}
		
	    public static final String TableName = "item";
		public static final Uri ContentUri = Uri.parse("content://" + GroceryActivity.AUTHORITY + "/" + TableName);
		public static final Uri CONTENT_LIST_URI= Uri.parse("content://" + GroceryActivity.AUTHORITY + "/" + GroceryList.TableName + TableName);
		public static final String ContentType = "vnd.android.cursor.item";

		public static final class Columns implements BaseColumns {
			public static final String LIST_ID = "list_id";
            public static final String SERVICE_ID ="service_id";
			public static final String NAME = "name";
			public static final String IS_CHECKED = "is_checked";
			public static final String CREATED_DATE = "created";
			public static final String MODIFIED_DATE = "modified";
		}

    //Sort
    public enum Sort {
        name(Columns.NAME), age(Columns._ID), status_name(
                Columns.IS_CHECKED + "," + name), status_age(
                Columns.IS_CHECKED + "," + age);

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
        + Columns.LIST_ID + " INTEGER,"
        + Columns.SERVICE_ID + " TEXT, "
        + Columns.NAME + " TEXT,"
        + Columns.IS_CHECKED + " INTEGER,"
        + Columns.CREATED_DATE + " INTEGER,"
        + Columns.MODIFIED_DATE + " INTEGER"
        + ");";

	    public static final String[] PROJECTION = new String[] {
            Columns._ID, // 0
            Columns.NAME, // 1
            Columns.IS_CHECKED
	    };
		
        public static ContentValues contentValues(int listId, String name) {
            ContentValues values = new ContentValues();
            values.put(Columns.LIST_ID, listId);
            values.put(Columns.NAME, name);
            return values;
        }
        
        public static ContentValues contentValues(Boolean isChecked) {
            ContentValues values = new ContentValues();
            values.put(Columns.IS_CHECKED, isChecked ? 1 : 0);
            return values;
        }
	}
