package cap.mizzou.rmtrx.app.Login;

import Models.UserAndResidenceResponse;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.DataAccess.DatabaseHydrator;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.User_setup.CreateResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.JoinResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.UserCreationInterface;
import cap.mizzou.rmtrx.app.ui.DashboardActivity;
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

    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private String confirmPassword;
    private String nameOfResidence;
    private UserInfo user;
    private String radioButtonSelected;
    private String joinResidenceCode;
    private int viewId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        getActionBar().setTitle("Register");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Context context=getApplicationContext();
        user=new UserInfo(context);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        viewId =view.getId();


        TextView textToBeChanged=(TextView)findViewById(R.id.code_or_name_title);

        if(viewId == R.id.JoinRadioButton) {
            textToBeChanged.setText("Code of the residence you would like to join");
            radioButtonSelected="Join";
        }
        else if(viewId == R.id.CreateRadioButton) {
            textToBeChanged.setText("Name of Residence");
            radioButtonSelected="Create";

        }

    }
    public void onClick(View view) {
        setObjectProperties();
        if(validateForm()) {
            if(radioButtonSelected.equals("Join")) {
                setJoinResidenceCode(((EditText) findViewById(R.id.code_or_name)).getText().toString());
                createUserAndJoinResidence();
            }
            else if(radioButtonSelected.equals("Create")) {
                setNameOfResidence(((EditText) findViewById(R.id.code_or_name)).getText().toString());
                createUserAndResidence();
            }
        }
    }

    private void setObjectProperties() {
        setFirstName(((EditText) findViewById(R.id.firstName)).getText().toString());
        setLastName(((EditText) findViewById(R.id.lastName)).getText().toString());
        setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        setPassword(((EditText) findViewById((R.id.password))).getText().toString());
        setConfirmPassword(((EditText) findViewById(R.id.confirmPassword)).getText().toString());
    }

    private void createUserAndJoinResidence() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        UserCreationInterface ri = restAdapter.create(UserCreationInterface.class);

        ri.createUserAndJoinResidence(getFirstName(), getLastName(), getEmail(), getPassword(), getJoinResidenceCode(), new Callback<UserAndResidenceResponse>() {
            @Override
            public void success(UserAndResidenceResponse userAndResidenceAndKey, Response response) {
                saveUserLocallyAndLogin(userAndResidenceAndKey);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.print("Failed to create User");
            }
        });
    }
    private void createUserAndResidence() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        UserCreationInterface ri = restAdapter.create(UserCreationInterface.class);

        ri.createUserAndResidence(getFirstName(), getLastName(), getEmail(), getPassword(), getNameOfResidence(), new Callback<UserAndResidenceResponse>() {
            @Override
            public void success(UserAndResidenceResponse userAndResidenceAndKey, Response response) {
                saveUserLocallyAndLogin(userAndResidenceAndKey);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.print("Failed to create User");
            }
        });
    }

    private void saveUserLocallyAndLogin(UserAndResidenceResponse userInfoAndKey) {
        setUserInfo(userInfoAndKey);
        DatabaseHydrator hydrator = new DatabaseHydrator(this);
        hydrator.UpdateDatabase(userInfoAndKey.getResidence().getId());
        Intent dashboard=new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

    private void setUserInfo(UserAndResidenceResponse userInfoAndKey) {
        user.setFirstName(userInfoAndKey.getUser().getFirstName());
        user.setLastName(userInfoAndKey.getUser().getLastName());
        user.setEmail(userInfoAndKey.getUser().getEmail());
        user.setAuthKey(userInfoAndKey.getKey().getKey());
        user.setId(userInfoAndKey.getUser().getId());
        user.setResidenceName(userInfoAndKey.getResidence().getName());
        user.setResidenceId(userInfoAndKey.getResidence().getId());
        user.setLoggedIn(true);
    }

    public void joinResidence() {
            Intent joinResidenceIntent = new Intent(this, JoinResidenceActivity.class);
            joinResidenceIntent.putExtra("residence code",getJoinResidenceCode());
            startActivity(joinResidenceIntent);
    }

    public void createResidence() {
            Intent createResidence = new Intent(this, CreateResidenceActivity.class);
            startActivity(createResidence);
    }


    public boolean validateForm() {
        boolean result = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        if (getFirstName().equals("")) {
            alertDialogBuilder
                    .setMessage("Name is empty")
                    .setCancelable(true);
            result = false;
        }
        else if (getEmail().equals("")) {
            alertDialogBuilder
                    .setMessage("Email is empty")
                    .setCancelable(true);
            result = false;
        }
        else if (getPassword().equals("")) {
            alertDialogBuilder
                    .setMessage("Password is empty")
                    .setCancelable(true);
            result = false;

        }
        else if (getConfirmPassword().equals("")) {
            alertDialogBuilder
                    .setMessage("Confirmation Password is empty")
                    .setCancelable(true);
            result = false;
        }
        else if (!this.checkPassword()) {
            alertDialogBuilder
                    .setMessage("Passwords Don't Match")
                    .setCancelable(true);
            result = false;
        }

        //create alert box if needed and print it
        if (!result) {
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        return result;
    }


    public boolean checkPassword() {             //validates that password viewId
        Boolean result = false;

        if (this.password.equals(this.confirmPassword)) {
            result = true;
        }

        return result;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNameOfResidence() {
        return nameOfResidence;
    }

    public void setNameOfResidence(String nameOfResidence) {
        this.nameOfResidence = nameOfResidence;
    }

    public String getJoinResidenceCode() {
        return joinResidenceCode;
    }

    public void setJoinResidenceCode(String joinResidenceCode) {
        this.joinResidenceCode = joinResidenceCode;
    }
}
