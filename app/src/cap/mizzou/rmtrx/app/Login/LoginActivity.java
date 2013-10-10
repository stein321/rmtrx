package cap.mizzou.rmtrx.app.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;
import com.google.gson.annotations.SerializedName;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/21/13
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 * Ben adds a change
 */
public class LoginActivity extends FragmentActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private SharedPreferences logged_in_status;

    protected boolean login_result;
    private String login_name;  //just for printing to log
    private String password; //just for printing to log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SharedPreferences user_name = getSharedPreferences();
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Login");

    }

    @Override
    public void finish() {
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("returnKey1", "Swinging on a star. ");
        data.putExtra("returnKey2", "You could be better then you are. ");
        // Activity finished ok, return the data
        setResult(5, data);
        super.finish();
    }

    public void sendLoginInfo(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //saved to shared preferences
        this.logged_in_status = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = this.logged_in_status.edit();

        //grabs text from form
        EditText login_name_text = (EditText) findViewById(R.id.login_name);
        EditText p_word_text = (EditText) findViewById(R.id.p_word);

        //turns it into a string
        String login_to_add = login_name_text.getText().toString();
        String p_word_to_add = p_word_text.getText().toString();

//        //stores string as key valued pairs
        editor.putString("login_name", login_to_add);
        editor.putString("p_word", p_word_to_add);

        //hard coded login info, change to server call
        checkLoginCredentials(login_to_add, p_word_to_add);   //should set login_result to true or false
        if (login_result) {
            editor.putBoolean("logged_in_status_yo", true);
            editor.commit();
//            alertDialogBuilder
//                    .setMessage("Bro has logged in successfully")
//                    .setCancelable(true);
            finish();
        } else {
            alertDialogBuilder
                    .setMessage("Username and/password incorrect")
                    .setCancelable(true);
        }
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        //grabs values out of memory for debugging purposes
        boolean status = logged_in_status.getBoolean("logged_in_status_yo", false);
        password = logged_in_status.getString("p_word", null);
        String User = logged_in_status.getString("login_name", null);
        //prints values out in the debugger screen
        Log.d("test-login", String.valueOf(status));
        Log.d("test-pass", password);
        Log.d("test-user", User);


    }

    public void checkLoginCredentials(String username, String password) {

        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        AuthenticationRequestInterface ri = restAdapter.create(AuthenticationRequestInterface.class);


        ri.login(username, password, new Callback<AuthResponse>() {


            @Override
            public void success(AuthResponse authResponse, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
                   login_result=true;
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
                login_result=false;
            }
        }
        );
//        return login_result;
//        return true;//comment out

    }


    public void register(View view) {
        Intent myIntent = new Intent(this, RegistrationActivity.class);
        startActivity(myIntent);
    }

    public class AuthResponse {
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
}
