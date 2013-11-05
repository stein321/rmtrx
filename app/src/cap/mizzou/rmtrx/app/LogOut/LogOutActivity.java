package cap.mizzou.rmtrx.app.LogOut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.ui.HomeActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/6/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogOutActivity extends Activity {  //make sure to delete all shared preferences and probably all TestDbActivity Data

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);
        userInfo.setLoggedInStatus(false);
        userInfo.commit();
        Intent goToLoginPage = new Intent(this, HomeActivity.class);
        goToLoginPage.putExtra(EXTRA_MESSAGE, "Logged Out");
        startActivity(goToLoginPage);


    }



}
