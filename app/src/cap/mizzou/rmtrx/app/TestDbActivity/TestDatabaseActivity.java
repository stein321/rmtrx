package cap.mizzou.rmtrx.app.TestDbActivity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;

import java.util.List;

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attempt);
        getActionBar().setTitle("Add/Delete Items");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        this.getListView().setClickable(true);
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
                Comment comment = null;
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(position);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                return;
            }});
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment;
        int i = view.getId();
        if (i == R.id.add) {
            EditText text = (EditText) findViewById(R.id.add_item);
            String item_to_add = text.getText().toString();
            // Save the new comment to the database
            comment = datasource.createComment(item_to_add);
            if(comment.getComment().equals("mike"))
                comment.setComment("crossed off");
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