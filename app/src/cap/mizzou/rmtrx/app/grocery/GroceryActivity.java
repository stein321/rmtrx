package cap.mizzou.rmtrx.app.grocery;

import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;

/**
 * Javadoc needed
 */
public class GroceryActivity extends BaseFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Grocery List");
        setContentView(R.layout.activity_main);
    }
}
