package cap.mizzou.rmtrx.app.ui;


import Models.Key;
import Models.Residence;
import Models.ResponseObject;
import Models.User;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.DataAccess.ResidenceDataInterface;
import cap.mizzou.rmtrx.app.Login.AuthenticationRequestInterface;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeActivity extends BaseFragmentActivity {
    private UserInfo userInfo;
    private RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=getApplicationContext();
        userInfo =new UserInfo(context);
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Login");
        if(userInfo.isLoggedIn()) {
            goToDashBoard();
        }
        restAdapter=new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
    }
    public void goToDashBoard() {
        Intent goToDashBoard=new Intent(this,DashboardActivity.class);
        startActivity(goToDashBoard);
    }
    public void sendLoginInfo(View view) {
        //grabs text from form
        EditText login_name_text = (EditText) findViewById(R.id.login_name);
        EditText p_word_text = (EditText) findViewById(R.id.p_word);

        String login_to_add = login_name_text.getText().toString();
        String p_word_to_add = p_word_text.getText().toString();
        checkLoginCredentials(login_to_add, p_word_to_add);
    }

    public void checkLoginCredentials(String username, String password) {



        AuthenticationRequestInterface ri = restAdapter.create(AuthenticationRequestInterface.class);


        ri.login(username, password, new Callback<ResponseObject>() {


            @Override
            public void success(ResponseObject authResponse, Response response) {
                successfulLogin(authResponse.getResponse(),authResponse.getUser());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                incorrectLogin();
            }
        }
        );
    }

    private void incorrectLogin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Wrong login").create().show();
    }

    private void successfulLogin(Key key, User user) {
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setAuthKey(key.getKey());
        userInfo.setLoggedIn(true);
        userInfo.setId(user.getId());
        userInfo.commit();
        grabAllUsersInResidenceAndStoreInfoInDb();

        goToDashBoard();
    }
    private void grabAllUsersInResidenceAndStoreInfoInDb() {
        ResidenceDataInterface ri = restAdapter.create(ResidenceDataInterface.class);

        ri.getResidence(userInfo.getId(), new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {
                saveResidenceToDb(residence);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                int x=2;
            }
        });



    }

    public void saveResidenceToDb(Residence residence) {
        userInfo.setResidenceId(residence.getId());
        userInfo.commit();

    }

    public void register(View view) {
        Intent myIntent = new Intent(this, RegistrationActivity.class);
        startActivity(myIntent);
    }



    private boolean isOnline()
    {
        Context context=getApplicationContext();
        try
        {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }
    private void showNotConnectedAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Not online").create().show();
    }

}
