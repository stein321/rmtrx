package cap.mizzou.rmtrx.app.User_setup;

import android.content.Context;
import android.content.SharedPreferences;

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
            setFirstName(pref.getString("firstName", null));
            setLastName(pref.getString("lastName", null));
            setEmail(pref.getString("email", null));
            setId(pref.getString("id", null));
            setResidenceId(pref.getString("residenceId", null));
            setResidenceName(pref.getString("residenceName",null));
            setLoggedIn(pref.getBoolean("loggedIn", false));

}

    public void commit() {
        editor=pref.edit();
        editor.putString("firstName", getFirstName());
        editor.putString("lastName", getLastName());
        editor.putString("email",getEmail());
        editor.putString("id",getId());
        editor.putString("residenceId", getResidenceId());
        editor.putString("residenceName",getResidenceName());
        editor.putBoolean("loggedIn", isLoggedIn());
        editor.commit();
    }

    public void clearInfo() {
        editor.clear().commit();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
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


    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }





}
