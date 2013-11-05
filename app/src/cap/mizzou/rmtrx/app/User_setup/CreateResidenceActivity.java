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

    private String residenceName;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle info=getIntent().getExtras();
        setResidenceName(info.getString("name of residence to be created", "default res"));
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);
        createResidenceOnServer();
    }

    private void createResidenceOnServer() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.createResidence(getResidenceName(), userInfo.getId(), new Callback<Residence>() {
            @Override
            public void success(Residence residenceResponse, Response response) {
                goToDashboard();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //TODO:display an error
                //check if maybe not connected to the internet
            }
        });
    }
    private  void goToDashboard() {
        userInfo.setResidenceName(residenceName);
        userInfo.commit();
        Intent dashboard=new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }


    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }



}


