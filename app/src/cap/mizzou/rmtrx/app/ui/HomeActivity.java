package cap.mizzou.rmtrx.app.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.Login.AuthenticationRequestInterface;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.UserInfo;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.google.gson.annotations.SerializedName;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Random;


public class HomeActivity extends BaseFragmentActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public Boolean logged_in_status;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=getApplicationContext();
        userInfo =new UserInfo(context);
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Login");
        logged_in_status= userInfo.isLoggedInStatus();
        if(logged_in_status==true) {
            startIntent();
        }
    }
    public void startIntent() {
        Intent goToDashBoard=new Intent(this,DashboardActivity.class);
        startActivity(goToDashBoard);
    }
    public void sendLoginInfo(View view) {
        //grabs text from form
        EditText login_name_text = (EditText) findViewById(R.id.login_name);
        EditText p_word_text = (EditText) findViewById(R.id.p_word);

        //turns it into a string
        String login_to_add = login_name_text.getText().toString();
        String p_word_to_add = p_word_text.getText().toString();
        //stores string as key valued pairs
        userInfo.setUsername(login_to_add);
        userInfo.setPassword(p_word_to_add);

        //hard coded login info, change to server call
        checkLoginCredentials(login_to_add, p_word_to_add);   //should set login_result to true or false

    }
    public void checkLoginCredentials(String username, String password) {

        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        AuthenticationRequestInterface ri = restAdapter.create(AuthenticationRequestInterface.class);


        ri.login(username, password, new Callback<ResponseObject>() {


            @Override
            public void success(ResponseObject authResponse, Response response) {
                successfulLogin(authResponse.key,authResponse.getUser());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
                incorrectLogin();
            }
        }
        );
//        return login_result;
//        return true;//comment out
    }

    private void incorrectLogin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Wrong login").create().show();
    }

    private void successfulLogin(Key key, User user) {
        String email=user.getEmail();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        String authKey=key.getKey();

//        SharedPreferences.Editor editor=pref.edit();

        userInfo.setEmail(email);
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setAuthKey(authKey);
        userInfo.setLoggedInStatus(true);
        userInfo.commit();
        startIntent();
    }


    public void register(View view) {
        Intent myIntent = new Intent(this, RegistrationActivity.class);
        startActivity(myIntent);
    }
    public class User {
        @SerializedName("_id")
        String id;
        String email;
        String password;
        String firstName;
        String lastName;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
    public class Key {
        @SerializedName("_id")
        String id;
        String key;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
    public class ResponseObject {
        Key key;
        User user;

        public Key getResponse() {
            return key;
        }

        public void setResponse(Key key) {
            this.key = key;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }


    }
    //Todo:move this to the residence info page
    private String generateCode() {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int maxLength = alphabet.length();

        String code = "";

        Random r = new Random();

        for (int i = 0; i < 6; i++) {
              code = code.concat(String.valueOf(alphabet.charAt(r.nextInt(maxLength))));
        }

        return code;
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
