package cap.mizzou.rmtrx.app.User_setup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/5/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinResidenceActivity extends Activity {
    public SharedPreferences residence_info;

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
    }

    public void onClick(View view) {
        //store information in shared preferences


    }
}
