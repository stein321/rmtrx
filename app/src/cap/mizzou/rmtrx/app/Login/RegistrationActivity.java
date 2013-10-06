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

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/4/13
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private String first_and_last_name;
    private String email;
    private String password;
    private String confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        getActionBar().setTitle("Register");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void onClick(View view) {
        this.first_and_last_name = ((EditText) findViewById(R.id.firstAndLastName)).getText().toString();
        this.email = ((EditText) findViewById(R.id.email)).getText().toString();
        this.password = ((EditText) findViewById((R.id.password))).getText().toString();
        this.confirm_password = ((EditText) findViewById(R.id.confirm_password)).getText().toString();
        int i = view.getId();

        if (this.validateForm()) {
            this.storeDataInSharedPreference();
            if (i == R.id.createResidence) {
                this.createResidence(view);
            } else if (i == R.id.joinResidence) {
                this.joinResidence(view);
            }
        }
    }

    public void joinResidence(View view) {
        if (this.validateForm()) {
            this.storeDataInSharedPreference();
            Intent myIntent = new Intent(this, JoinResidenceActivity.class);
            myIntent.putExtra(EXTRA_MESSAGE, this.first_and_last_name);
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
        editor.putString("name", first_and_last_name);
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


        if (this.first_and_last_name.equals("")) {
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
}
