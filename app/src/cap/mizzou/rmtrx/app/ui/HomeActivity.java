package cap.mizzou.rmtrx.app.ui;

import android.os.Bundle;
import android.view.Menu;
import cap.mizzou.rmtrx.core.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;

public class HomeActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

}
