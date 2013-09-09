package cap.mizzou.rmtrx.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.squareup.otto.Subscribe;

public class HomeActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

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
        return true;
    }

    @Subscribe
    public void onActivitySelected(ActivitySelectedEvent event) {
        Intent intent = new Intent(this, event.activity.activityClass);
        startActivity(intent);
    }

}
