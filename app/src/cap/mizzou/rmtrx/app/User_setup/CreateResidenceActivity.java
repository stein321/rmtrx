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
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateResidenceActivity extends Activity {
    Residence residence;
    String user_id;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserId();
        createResidenceObject();

        createResidenceOnServer();
    }

    private void createResidenceOnServer() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
        ResidenceCreationInterface ri= restAdapter.create(ResidenceCreationInterface.class);

        ri.createResidence(residence.getName(),this.user_id, new Callback<Residence>() {
            @Override
            public void success(Residence residenceResponse, Response response) {
                String name=residenceResponse.getName();
                String id=residenceResponse.getId();
                SharedPreferences.Editor editor=pref.edit();

                editor.putString("residence_name",name);
                editor.putString("residence_id", id);
                editor.commit();
                 //TODO:create table to store users associated with the residence that it gets sent from the server
                goToDashboard();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
             //TODO:move this to another activity to send code when a code is inputed
        String name=pref.getString("residence_name",null);
        String id=pref.getString("residence_id",null);
//
//        ri.saveResidenceCode(id,"0000",new Callback<Code>() {
//            @Override
//            public void success(Code code, Response response) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
    }
    private  void goToDashboard() {
        Intent dashboard=new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }
    private void createResidenceObject() {
        residence=new Residence();
    }

    private void setUserId() {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        user_id=pref.getString("user_id",null);
    }

    public class CreateResidenceResponse {
        public Residence residence_response;


        public Residence getResidence() {
            return residence_response;
        }

        public void setResidence(Residence residence) {
            this.residence_response = residence;
        }


    }

}
