package cap.mizzou.rmtrx.app.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.Login.AuthenticationRequestInterface;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.core.ui.BaseFragmentActivity;
import com.google.gson.annotations.SerializedName;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Random;


public class HomeActivity extends BaseFragmentActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private SharedPreferences pref;
    public Boolean logged_in_status;


    protected boolean login_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Login");
        logged_in_status=pref.getBoolean("logged_in_status_yo",false);
        if(logged_in_status==true) {
            startIntent();
        }
    }
        //testing
    @Override
    public void finish() {
        // Prepare data intent
        Intent data = new Intent();
        // Activity finished ok, return the data
        setResult(5, data);
        super.finish();
    }
    public void startIntent() {
        Intent goToDashBoard=new Intent(this,DashboardActivity.class);
        startActivity(goToDashBoard);
    }
    public void sendLoginInfo(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //saved to shared preferences
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

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
                login_result=false;
            }
        }
        );
//        return login_result;
//        return true;//comment out
    }

    private void successfulLogin(Key key, User user) {
        String email=user.getEmail();
        String first_name=user.getFirstName();
        String last_name=user.getLastName();
        String authKey=key.getKey();

         SharedPreferences.Editor editor=pref.edit();
        editor.putString("email",email);
        editor.putString("first_name",first_name);
        editor.putString("last_name",last_name);
        editor.putString("auth_key", authKey);
        editor.putBoolean("logged_in_status_yo",true);
        editor.commit();
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

}
