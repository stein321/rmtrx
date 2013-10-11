package cap.mizzou.rmtrx.app.Residence;

import android.app.Activity;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/10/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeeResidenceInfo extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residence_information);
        getActionBar().setTitle("Residence Information");

    }
    private void addValuesToPage() {

    }
}
