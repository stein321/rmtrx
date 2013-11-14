package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;
import android.widget.*;
import java.util.List;
import android.content.Intent;
import android.app.*;
import android.content.DialogInterface;



/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/12/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BulletinBoardActivity extends ListActivity {


    private BulletinBoardDb datasource;
    private PostListAdapter postListAdapter;
    private Post currentpost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);
        getActionBar().setTitle("Bulletin Board");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        datasource = new BulletinBoardDb(this);
        datasource.open();



        List<Post> values = datasource.getAllPosts();

        postListAdapter = new PostListAdapter(getApplicationContext(), R.layout.post_list_item, values);

        setListAdapter(postListAdapter);



        this.getListView().setClickable(true);
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //show post details
            }});




    }

    //starts the addPost activity
    public void onClick(View view) {

        Intent addpost= new Intent(this, AddBBPostActivity.class);
        startActivity(addpost);
        }

    public void deletePost(View view){
        ImageButton button = (ImageButton) view;
        currentpost = (Post) button.getTag();
        datasource.deletePost(currentpost);

        postListAdapter.removePost(currentpost);
        postListAdapter.notifyDataSetChanged();

        //showDialog(1);
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


    private Dialog createDialogRemoveConfirm() {
        return new AlertDialog.Builder(getApplicationContext())
                //.setIcon(R.drawable.trashbin_icon)
                .setTitle("Remove?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        handleRemoveConfirm();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }


    protected void handleRemoveConfirm() {

        datasource.deletePost(currentpost);
    }

    @Override
    protected Dialog onCreateDialog(int i) {

                return createDialogRemoveConfirm();

        }

}





