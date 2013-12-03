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
    private String[] allColumns = { BBMySQLiteHelper.COLUMN_ID, BBMySQLiteHelper.COLUMN_USERID,
            BBMySQLiteHelper.COLUMN_POST, BBMySQLiteHelper.COLUMN_POSTDETAILS };

    public BulletinBoardDb(Context context) {
        dbHelper = new BBMySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void drop(){
        dbHelper.onUpgrade(database, 2, 3);
    }

    public Post createPost(String userid, String title, String details) {
        ContentValues values = new ContentValues();
        values.put(BBMySQLiteHelper.COLUMN_USERID, userid);
        values.put(BBMySQLiteHelper.COLUMN_POST, title);
        values.put(BBMySQLiteHelper.COLUMN_POSTDETAILS, details);
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

    public void createLike(long postid, String name) {
        ContentValues values = new ContentValues();
        values.put(BBMySQLiteHelper.COLUMN_POSTID, postid);
        values.put(BBMySQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(BBMySQLiteHelper.TABLE_LIKES, null,
                values);

    }

    public void deletePost(Post post) {
        long id = post.getId();
        //System.out.println("Post deleted with id: " + id);
        database.delete(BBMySQLiteHelper.TABLE_POSTS, BBMySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
     //TODO
    public void deleteLike(Post post, String fname) {
        long id = post.getId();
        //System.out.println("Post deleted with id: " + id);
        database.delete(BBMySQLiteHelper.TABLE_LIKES, BBMySQLiteHelper.COLUMN_POSTID
                + " = " + id + " and " + BBMySQLiteHelper.COLUMN_NAME + " = " + fname, null);
    }

    public void updatePost(Post post){
        long id = post.getId();

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

    public List<String> getAlllikes(long postid) {
        List<String> likes = new ArrayList<String>();

        Cursor cursor = database.rawQuery("SELECT * FROM likes WHERE postid = " +postid, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            likes.add(cursorToLike(cursor));
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return likes;
    }


    public void editPost(ContentValues cv, long rowid){

        database.update(BBMySQLiteHelper.TABLE_POSTS, cv, "_id=" + rowid, null);
    }


    private Post cursorToPost(Cursor cursor) {
        Post comment = new Post();
        comment.setId(cursor.getLong(0));
        comment.setUserId(cursor.getString(1));
        comment.setPostTitle(cursor.getString(2));
        comment.setPostDetails(cursor.getString(3));

        return comment;
    }

    private String cursorToLike(Cursor cursor) {
       return cursor.getString(2);
    }
}