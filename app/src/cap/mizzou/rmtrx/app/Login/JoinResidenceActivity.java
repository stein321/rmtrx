package cap.mizzou.rmtrx.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/5/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinResidenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra(RegistrationActivity.EXTRA_MESSAGE);
        setContentView(R.layout.join_residence_page);
        getActionBar().setTitle("Join Residence");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = (TextView) findViewById(R.id.change);
        textView.setText(name);

// Create the text view
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(name);
//
//        // Set the text view as the activity layout
//        setContentView(textView);
    }
}
