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
import cap.mizzou.rmtrx.app.DataAccess.DatabaseHydrator;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.Login.AuthenticationRequestInterface;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.grocery.GroceryDB;
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
        userInfo = new UserInfo(this);
        setContentView(R.layout.activity_login);
        if(userInfo.isLoggedIn()) {
            hydrateDatabase(userInfo.getResidenceId());
//            goToDashBoard();
        }
        restAdapter=new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();
    }
    public void goToDashBoard() {
        Intent goToDashBoard=new Intent(this, DashboardActivity.class);
//        createDBs();
        startActivity(goToDashBoard);
    }

    private void createDBs() {
        GroceryDB groceryDB=new GroceryDB();
        boolean yes=groceryDB.onCreate();
    }

    public void sendLoginInfo(View view) {
        EditText userName = (EditText) findViewById(R.id.login_name);
        EditText password = (EditText) findViewById(R.id.p_word);
        checkLoginCredentials(userName.getText().toString(), password.getText().toString());
    }

    public void checkLoginCredentials(String username, String password) {
        AuthenticationRequestInterface ri = restAdapter.create(AuthenticationRequestInterface.class);
        ri.login(username, password, new Callback<ResponseObject>() {

            @Override
            public void success(ResponseObject authResponse, Response response) {
                successfulLogin(authResponse.getResponse(), authResponse.getUser(), authResponse.getResidence());
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

    public void hydrateDatabase(String residenceId) {
        DatabaseHydrator hydrator = new DatabaseHydrator(this);
        hydrator.UpdateDatabase(residenceId);
    }

    private void successfulLogin(Key key, User user, Residence residence) {
        setUserInfo(key, user, residence);
        hydrateDatabase(residence.getId());
        goToDashBoard();
    }

    private void setUserInfo(Key key, User user, Residence residence) {
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setAuthKey(key.getKey());
        userInfo.setLoggedIn(true);
        userInfo.setId(user.getId());
        userInfo.setResidenceId(residence.getId());
        userInfo.setResidenceName(residence.getName());

        userInfo.setGroceryListLastUpdate(residence.getGroceryListLastUpdate());
    }


    private void grabAllUsersInResidenceAndStoreInfoInDb(Residence residence) {
        ResidentDataSource data=new ResidentDataSource(this);

        User[] users=residence.getUsers();
        for(User user : users) {
            data.open();
            data.addResident(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
            data.close();
        }
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
