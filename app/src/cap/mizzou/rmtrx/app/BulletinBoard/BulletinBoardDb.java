package cap.mizzou.rmtrx.app.BulletinBoard;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cap.mizzou.rmtrx.app.BulletinBoard.BBMySQLiteHelper;
import cap.mizzou.rmtrx.app.BulletinBoard.Post;
import cap.mizzou.rmtrx.app.TestDbActivity.Comment;
import cap.mizzou.rmtrx.app.TestDbActivity.MySQLiteHelper;

public class BulletinBoardDb {

    // Database fields
    private SQLiteDatabase database;
    private BBMySQLiteHelper dbHelper;
    private String[] allColumns = { BBMySQLiteHelper.COLUMN_ID,
            BBMySQLiteHelper.COLUMN_POST };

    public BulletinBoardDb(Context context) {
        dbHelper = new BBMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Post createPost(String post) {
        ContentValues values = new ContentValues();
        values.put(BBMySQLiteHelper.COLUMN_POST, post);
        long insertId = database.insert(BBMySQLiteHelper.TABLE_POSTS, null,
                values);
        Cursor cursor = database.query(BBMySQLiteHelper.TABLE_POSTS,
                allColumns, BBMySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Post newPost = cursorToPost(cursor);
        cursor.close();
        return newPost;
    }

    public void deletePost(Post post) {
        long id = post.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(BBMySQLiteHelper.TABLE_POSTS, BBMySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Post> getAllPosts() {
        List<Post> comments = new ArrayList<Post>();

        Cursor cursor = database.query(BBMySQLiteHelper.TABLE_POSTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Post comment = cursorToPost(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Post cursorToPost(Cursor cursor) {
        Post comment = new Post();
        comment.setId(cursor.getLong(0));
        comment.setPost(cursor.getString(1));
        return comment;
    }
}