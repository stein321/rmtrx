package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;
import android.view.View;
import android.widget.*;


/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/12/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddBBPost extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.bulletin_board_add_post);
        }




    public void sendBBPost(View view) {
        // Sends Post Info

        Intent post = new Intent(this, BulletinBoardActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        post.putExtra(EXTRA_MESSAGE, message);
        startActivity(post);

    }





    public void cancel(View view) {
        // Cancels Add Option

         finish();
    }

}




