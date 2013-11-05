package cap.mizzou.rmtrx.app.Login;

import Models.CreateUserResponse;
import Models.Residence;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cap.mizzou.rmtrx.app.R;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.User_setup.CreateResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.JoinResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.ResidenceInteractionInterface;
import cap.mizzou.rmtrx.app.User_setup.UserCreationInterface;
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
    private String email;
    private String password;
    private String confirmPassword;
    private String lastName;
    private String apiKey;
    private String nameOfResidence;
    private String userId;
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

        if(viewId ==R.id.JoinRadioButton) {
            textToBeChanged.setText("Code of the residence you would like to join");
            radioButtonSelected="Join";
        }
        else if(viewId ==R.id.CreateRadioButton) {
            textToBeChanged.setText("Name of Residence");
            radioButtonSelected="Create";

        }

    }
    public void onClick(View view) {
        if(viewId ==R.id.JoinRadioButton) {
            setJoinResidenceCode(((EditText) findViewById(R.id.code_or_name)).getText().toString());
        } else if(viewId ==R.id.CreateRadioButton) {
            setNameOfResidence(((EditText) findViewById(R.id.code_or_name)).getText().toString());
        }

        setFirstName(((EditText) findViewById(R.id.firstName)).getText().toString());
        setLastName(((EditText) findViewById(R.id.lastName)).getText().toString());
        setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        setPassword(((EditText) findViewById((R.id.password))).getText().toString());
        setConfirmPassword(((EditText) findViewById(R.id.confirmPassword)).getText().toString());

        if (this.validateForm()) {
              sendUserInfoToServerToCreateUser();
        }
    }
    private void sendResidenceInfoToServerToCreateResidence() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        ResidenceInteractionInterface ri = restAdapter.create(ResidenceInteractionInterface.class);

        ri.createResidence(getNameOfResidence(), getUserId(), new Callback<Residence>() {
            @Override
            public void success(Residence residence, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
    }

    private void sendUserInfoToServerToCreateUser() {
        RestAdapter restAdapter =
                new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build();

        UserCreationInterface ri = restAdapter.create(UserCreationInterface.class);

        ri.createUser(getFirstName(),getLastName(),getEmail(),getPassword(), new Callback<CreateUserResponse>() {
            @Override
            public void success(CreateUserResponse userAndKey, Response response) {
                createUserAndLogin(userAndKey,response);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                int x = 1;
            }
        });
    }

    private void createUserAndLogin(CreateUserResponse userAndKey, Response response) {
        user.setFirstName(userAndKey.getUser().getFirstName());
        user.setLastName(userAndKey.getUser().getLastName());
        user.setEmail(userAndKey.getUser().getEmail());
        user.setEmail(userAndKey.getKey().getKey());
        user.setId(userAndKey.getUser().getId());
        user.setLoggedIn(true);
        user.commit();

        if(this.radioButtonSelected.equals("Create")) {
            createResidence();
        }
        else if(this.radioButtonSelected.equals("Join")) {
            joinResidence();
        }
        sendResidenceInfoToServerToCreateResidence();
    }

    public void joinResidence() {

            Intent myIntent = new Intent(this, JoinResidenceActivity.class);
            startActivity(myIntent);
    }

    public void createResidence() {
//        if (this.checkPassword()) {
            Intent createResidence = new Intent(this, CreateResidenceActivity.class);
            createResidence.putExtra("name of residence to be created", getNameOfResidence());
            startActivity(createResidence);
//        }
    }


    public boolean validateForm() {
        boolean result = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        if (this.firstName.equals("")) {
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

        } else if (this.confirmPassword.equals("")) {
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
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String key) {
        this.apiKey = key;
    }
    public String getNameOfResidence() {
        return nameOfResidence;
    }

    public void setNameOfResidence(String nameOfResidence) {
        this.nameOfResidence = nameOfResidence;
    }
    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getJoinResidenceCode() {
        return joinResidenceCode;
    }

    public void setJoinResidenceCode(String joinResidenceCode) {
        this.joinResidenceCode = joinResidenceCode;
    }




}
