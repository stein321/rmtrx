package cap.mizzou.rmtrx.app.SQLite;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/28/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import cap.mizzou.rmtrx.app.R;

import java.util.List;
import java.util.Random;

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_attempt);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment;
        int i = view.getId();
        if (i == R.id.add) {
            String[] comments = new String[]{"Cool", "Very nice", "Hate it"};
            int nextInt = new Random().nextInt(3);
            // Save the new comment to the database
            comment = datasource.createComment(comments[nextInt]);
            adapter.add(comment);

        } else if (i == R.id.delete) {
            if (getListAdapter().getCount() > 0) {
                comment = (Comment) getListAdapter().getItem(0);
                datasource.deleteComment(comment);
                adapter.remove(comment);
            }

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}