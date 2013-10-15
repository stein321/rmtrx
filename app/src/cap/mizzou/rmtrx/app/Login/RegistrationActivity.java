package cap.mizzou.rmtrx.app.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.CreateResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.JoinResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.ResidenceInteractionInterface;
import cap.mizzou.rmtrx.app.User_setup.UserCreationInterface;
import cap.mizzou.rmtrx.app.ui.HomeActivity;
import com.google.gson.annotations.SerializedName;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/4/13
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private String first_name;
    private String email;
    private String password;
    private String confirm_password;
    private String last_name;
    private String api_key;
    private String user_id;
    private String name_of_residence;
    private SharedPreferences logged_in_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        getActionBar().setTitle("Register");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void onClick(View view) {
        this.first_name = ((EditText) findViewById(R.id.firstName)).getText().toString();
        this.last_name = ((EditText) findViewById(R.id.lastName)).getText().toString();
        this.email = ((EditText) findViewById(R.id.email)).getText().toString();
        this.password = ((EditText) findViewById((R.id.password))).getText().toString();
        this.confirm_password = ((EditText) findViewById(R.id.confirm_password)).getText().toString();
        this.name_of_residence = ((EditText)findViewById(R.id.name_of_residence)).getText().toString();
        int i = view.getId();

        if (this.validateForm()) {
              sendUserInfoToServerToCreateUser();
              sendResidenceInfoToServerToCreateResidence();
        }
        if(i == R.id.createResidence) {
            createResidence(view);
        }
    }

    private void sendResidenceInfoToServerToCreateResidence() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        ResidenceInteractionInterface ri = restAdapter.create(ResidenceInteractionInterface.class);

        ri.createResidence(this.name_of_residence, this.user_id, new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {
                //To change body of implemented methods use File | Settings | File Templates.
                //add all info from Residence
                int x=1;
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //To change body of implemented methods use File | Settings | File Templates.
                int x=1;
            }
        });
    }

    private void sendUserInfoToServerToCreateUser() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        UserCreationInterface ri = restAdapter.create(UserCreationInterface.class);

        ri.createUser(this.first_name, this.last_name, this.email, this.password, new Callback<CreateUserResponse>() {
            @Override
            public void success(CreateUserResponse userAndKey, Response response) {
                int x = 1;

                setFirst_name(userAndKey.user.firstName);
                setLast_name(userAndKey.user.lastName);
                setEmail(userAndKey.user.email);
                setApi_key(userAndKey.key.getKey());
                setUser_id((userAndKey.user.getId()));
                //make class to store SharedPreferences
                 SharedPreferences createUser=getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor= createUser.edit();
                editor.putString("first_name",getFirst_name());
                editor.putString("last_name",getLast_name());
                editor.putString("email",getEmail());
                editor.putString("api_key",getApi_key());
                editor.putBoolean("logged_in_status_yo",true);
                editor.commit();
                //s
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                int x = 1;
            }
        });


    }
    public void joinResidence(View view) {
        if (this.validateForm()) {
            this.storeDataInSharedPreference();
            Intent myIntent = new Intent(this, JoinResidenceActivity.class);
            myIntent.putExtra(EXTRA_MESSAGE, this.first_name);
            startActivity(myIntent);
        }
    }

    public void createResidence(View view) {
        if (this.checkPassword()) {
            Intent myIntent = new Intent(this, CreateResidenceActivity.class);
            startActivity(myIntent);
        }
    }

    public void storeDataInSharedPreference() {
        //saving data to shared preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        //take attributes and store them in shared preferences
        editor.putString("name", first_name);
        editor.putString("email", email);
        editor.putBoolean("logged_in_status_yo", true); //set to logged in
        editor.commit();

        //test
        Log.d("test-name_first_last", pref.getString("name", null));
        Log.d("test-email", pref.getString("email", null));

        return;
    }

    public boolean validateForm() {
        boolean result = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        if (this.first_name.equals("")) {
            alertDialogBuilder
                    .setMessage("Name is empty")
                    .setCancelable(true);
            result = false;
        } else if (this.email.equals("")) {
            alertDialogBuilder
                    .setMessage("Email is empty")
                    .setCancelable(true);
            result = false;
        } else if (this.password.equals("")) {
            alertDialogBuilder
                    .setMessage("Password is empty")
                    .setCancelable(true);
            result = false;

        } else if (this.confirm_password.equals("")) {
            alertDialogBuilder
                    .setMessage("Confirmation Password is empty")
                    .setCancelable(true);
            result = false;
        } else if (!this.checkPassword()) {
            alertDialogBuilder
                    .setMessage("Passwords Don't Match")
                    .setCancelable(true);
            result = false;
        }

        //create alert box if needed and print it
        if (result == false) {
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        return result;
    }


    public boolean checkPassword() {             //validates that password i
        Boolean result = false;

        if (this.password.equals(this.confirm_password)) {
            result = true;
        }

        return result;
    }
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String key) {
        this.api_key = key;
    }
    public String getName_of_residence() {
        return name_of_residence;
    }

    public void setName_of_residence(String name_of_residence) {
        this.name_of_residence = name_of_residence;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public class CreateUserResponse {
        User user;
        HomeActivity.AuthResponse key;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public HomeActivity.AuthResponse getKey() {
            return key;
        }

        public void setKey(HomeActivity.AuthResponse key) {
            this.key = key;
        }
    }
    public class Residence {
        String name;
        String id;
        String[] users;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String[] getUsers() {
            return users;
        }

        public void setUsers(String[] users) {
            this.users = users;
        }
    }

}
