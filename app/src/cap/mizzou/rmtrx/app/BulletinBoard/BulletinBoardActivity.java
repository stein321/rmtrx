package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/12/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BulletinBoardActivity extends ListActivity {


    private BulletinBoardDb datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);
        getActionBar().setTitle("Bulletin Board");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new BulletinBoardDb(this);
        datasource.open();

        List<Post> values = datasource.getAllPosts();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Post> adapter = (ArrayAdapter<Post>) getListAdapter();
        Post comment;
        int i = view.getId();
        if (i == R.id.addPost) {
            EditText text = (EditText) findViewById(R.id.add_post);
            String newpost = text.getText().toString();
            // Save the new comment to the database
            comment = datasource.createPost(newpost);

            adapter.add(comment);

        } else if (i == R.id.deletePost) {
            if (getListAdapter().getCount() > 0) {
                comment = (Post) getListAdapter().getItem(0);
                datasource.deletePost(comment);
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





