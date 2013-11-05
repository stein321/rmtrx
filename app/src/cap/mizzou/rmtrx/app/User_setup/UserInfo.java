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
    SharedPreferences.Editor editor;
    private String id;
    private String authKey;
    private String residenceId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean loggedInStatus;

    public UserInfo(Context context) {
            pref=context.getSharedPreferences("MyPref", 0);
            setFirstName(pref.getString("firstName", firstName));
            setLastName(pref.getString("lastName", lastName));
            setEmail(pref.getString("emai",email));
            setId(pref.getString("id",id));
            setResidenceId(pref.getString("residenceId", residenceId));
            setLoggedInStatus(pref.getBoolean("loggedInStatus",false));
}

    public void commit() {
        editor=pref.edit();
        editor.putString("firstName", getFirstName());
        editor.putString("lastName", getLastName());
        editor.putString("email",getEmail());
        editor.putString("id",getId());
        editor.putString("residenceId", getResidenceId());
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


    public boolean isLoggedInStatus() {
        return loggedInStatus;
    }

    public void setLoggedInStatus(boolean loggedInStatus) {
        this.loggedInStatus = loggedInStatus;
    }
    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }




}
