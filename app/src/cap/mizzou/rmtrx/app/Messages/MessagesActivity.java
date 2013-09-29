package cap.mizzou.rmtrx.app.Messages;

import android.app.Activity;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/16/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_empty);
        getActionBar().setTitle("Messages");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
