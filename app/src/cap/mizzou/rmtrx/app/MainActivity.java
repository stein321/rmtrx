package cap.mizzou.rmtrx.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/21/13
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 * Alec change.
 */
public class MainActivity extends BaseFragmentActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendLoginData(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
