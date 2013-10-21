package cap.mizzou.rmtrx.app.BulletinBoard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;
import android.content.*;


/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 10/12/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayBBPost extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.bulletin_board_post_detail);


        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
    }
}
