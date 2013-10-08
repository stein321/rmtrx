package cap.mizzou.rmtrx.app.grocery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "TaskDB";

    private static final String TABLE_TASKS = "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_STATUS = "status";

    public TaskDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASKNAME+ " TEXT, "
                + KEY_STATUS + " INTEGER)";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    //ADD TASK
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_STATUS, task.getStatus());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }


    //READ TASK
    public java.util.List<Task> getAllTasks() {
        java.util.List<Task> taskList = new java.util.ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping and adding
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setStatus(cursor.getInt(2));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        // return list
        return taskList;
    }



    //UPDATE TASK
    public void updateTask(Task task) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_STATUS, task.getStatus());
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }
}
