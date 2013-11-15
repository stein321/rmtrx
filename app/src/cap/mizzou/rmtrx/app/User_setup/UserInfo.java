package cap.mizzou.rmtrx.app.User_setup;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/4/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Map<String,?> keys;


    private String id;
    private String authKey;
    private String residenceId;
    private String residenceName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean loggedIn;

    public UserInfo(Context context) {
            pref=context.getSharedPreferences("MyPref", 0);
            editor=pref.edit();
            setAuthKey(pref.getString("authKey",null));
            setFirstName(pref.getString("firstName", null));
            setLastName(pref.getString("lastName", null));
            setEmail(pref.getString("email", null));
            setId(pref.getString("id", null));
            setResidenceId(pref.getString("residenceId", null));
            setResidenceName(pref.getString("residenceName",null));
            setLoggedIn(pref.getBoolean("loggedIn", false));
            keys = pref.getAll();
    }



    public void clearInfo() {
        editor.clear();
        editor.commit();
    }

    //just do this to see all of the sharedPreferences in the debug log
//    public void dumpPrefValues() {
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            Log.d("map values", entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }
//    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        editor.putString("id",id);
        editor.commit();
    }

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
        editor.putString("residenceId",residenceId);
        editor.commit();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        editor.putString("firstName",firstName);
        editor.commit();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        editor.putString("lastName",lastName);
        editor.commit();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        editor.putString("email",email);
        editor.commit();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        editor.putString("password",password);
        editor.commit();
    }


    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        editor.putBoolean("loggedIn",loggedIn);
        editor.commit();
    }
    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
        editor.putString("authKey",authKey);
        editor.commit();
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
        editor.putString("residenceName",residenceName);
        editor.commit();
    }





}
