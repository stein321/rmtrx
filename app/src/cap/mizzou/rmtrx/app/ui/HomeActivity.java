package cap.mizzou.rmtrx.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import cap.mizzou.rmtrx.app.Login.LoginActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.squareup.otto.Subscribe;

public class HomeActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merge_empty);
        getSupportActionBar().setTitle("Dashboard");

        //check to see if login flag is step, if not login
        //if(loginSet then skip this shit
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("Value1", "This value one for ActivityTwo ");
        i.putExtra("Value2", "This value two ActivityTwo");
        // Set the request code to any code you like, you can identify the
        // callback via this code
        startActivityForResult(i, 3);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 5 && requestCode == 3) {
            Log.d("Returned_statement", "You did it");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Subscribe
    public void onActivitySelected(ActivitySelectedEvent event) {
        Intent intent = new Intent(this, event.activity.activityClass);
        startActivity(intent);
    }

}
