package cap.mizzou.rmtrx.app.grocery;

import android.app.Activity;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.R;

/**
 * Javadoc needed
 */
public class GroceryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Grocery List");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.article_view);
    }
}
