package cap.mizzou.rmtrx.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.squareup.otto.Subscribe;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/11/13
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashboardActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_empty);
//        getActionBar().setTitle("Dashboard");
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment homeFragment = fragmentManager.findFragmentByTag(HomeFragment.TAG);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();

            fragmentManager.beginTransaction()
                    .add(android.R.id.content, homeFragment, HomeFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Subscribe//Don't delete this. this is important
    public void onActivitySelected(ActivitySelectedEvent event) {
        Intent intent = new Intent(this, event.activity.activityClass);
        startActivity(intent);
    }
}
