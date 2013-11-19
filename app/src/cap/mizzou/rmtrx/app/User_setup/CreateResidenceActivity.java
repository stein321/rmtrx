package cap.mizzou.rmtrx.app.User_setup;

import Models.Residence;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.ui.DashboardActivity;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        Bundle info=getIntent().getExtras();
        goToDashboard();
    }


    private  void goToDashboard() {

        Intent dashboard=new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }



}


