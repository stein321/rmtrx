package cap.mizzou.rmtrx.app.LogOut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import cap.mizzou.rmtrx.app.ui.HomeActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/6/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogOutActivity extends Activity {  //make sure to delete all  TestDbActivity Data

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private UserInfo userInfo;
    private ResidentDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=getApplicationContext();
        userInfo=new UserInfo(context);
        dataSource= new ResidentDataSource(this);
        clearStoredUserInfo();
        goToLoginPage();
    }
    private void clearStoredUserInfo() {
        //TODO:clear local DB
        dataSource.open();
        dataSource.truncate();
        dataSource.close();
        userInfo.clearInfo();
//        userInfo.setLoggedIn(false);
//        userInfo.commit();
    }
    private void goToLoginPage() {
        Intent goToLoginPageIntent = new Intent(this, HomeActivity.class);
        goToLoginPageIntent.putExtra(EXTRA_MESSAGE, "Logged Out");
        startActivity(goToLoginPageIntent);
    }
}
