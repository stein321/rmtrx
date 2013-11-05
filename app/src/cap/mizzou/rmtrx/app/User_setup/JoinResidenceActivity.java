package cap.mizzou.rmtrx.app.User_setup;

import Models.Residence;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinResidenceActivity extends Activity {
    public SharedPreferences pref;
    private String code="This is a code";
    private String residence_id="This is a residence id";
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUserId();
        joinResidenceOnServer();
    }
    public void joinResidenceOnServer() {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String user_id=pref.getString("user_id","hello");

        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.joinResidenceWithCode("abcde",user_id, new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
                goToDashboard();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
    private void setUserId() {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        this.user_id=pref.getString("user_id","hello");
    }
    private void goToDashboard() {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }
}
