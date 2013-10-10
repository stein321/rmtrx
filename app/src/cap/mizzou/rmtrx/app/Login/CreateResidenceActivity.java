package cap.mizzou.rmtrx.app.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/5/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateResidenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.join_residence_page);
        getActionBar().setTitle("Create Residence");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
