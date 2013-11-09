package cap.mizzou.rmtrx.app.User_setup;

import Models.Residence;
import android.app.Activity;
import android.content.Context;
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
    private UserInfo userInfo;
    private String residenceCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle info=getIntent().getExtras();
        setResidenceCode(info.getString("residence code", null));
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);

        joinResidenceOnServer();
    }
    public void joinResidenceOnServer() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.joinResidenceWithCode(getResidenceCode(),userInfo.getId(), new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {
                goToDashboard();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //TODO:print some sort of error such as nonexistent code
                //TODO: if user is created and doesn't successfully create/join a residence, need to figure out what's next
            }
        });
    }
    private void goToDashboard() {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

    public String getResidenceCode() {
        return residenceCode;
    }

    public void setResidenceCode(String residenceCode) {
        this.residenceCode = residenceCode;
    }

}
