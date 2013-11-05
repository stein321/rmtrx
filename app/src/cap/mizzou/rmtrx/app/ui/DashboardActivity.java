package cap.mizzou.rmtrx.app.ui;

import Models.Residence;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import cap.mizzou.rmtrx.app.DataAccess.ResidenceDataInterface;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.squareup.otto.Subscribe;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        getSupportActionBar().setTitle("Dashboard");

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment homeFragment = fragmentManager.findFragmentByTag(HomeFragment.TAG);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();

            fragmentManager.beginTransaction()
                    .add(android.R.id.content, homeFragment, HomeFragment.TAG)
                    .commit();
        }

        //Check if ResidenceId is set, and if not try and get it
        final SharedPreferences preferences =getApplicationContext().getSharedPreferences("MyPref", 0);
        String residenceId = preferences.getString("residence_id", "");

        if(residenceId == "") {
            String userId = preferences.getString("id", "");

            RestAdapter restAdapter = new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
            ResidenceDataInterface restInterface = restAdapter.create(ResidenceDataInterface.class);

            restInterface.getResidence(userId, new Callback<Residence>() {
                @Override
                public void success(Residence residence, Response response) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("residence_id", residence.getId());
                }

                @Override
                public void failure(RetrofitError retrofitError) {

                    int x = 1;

                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 5 && requestCode == 3) {
            Log.d("Returned_statement", "You did it");
        }
    }
    private boolean isLoginSet() {
        boolean result=false;
        SharedPreferences logged_in_status=getApplicationContext().getSharedPreferences("MyPref", 0);
        result = logged_in_status.getBoolean("logged_in_status_yo", false);
        return result;
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
