import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/22/13
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class is to be called when information about the user is needed or is going to be changed. Once instantiated, it sets the variables
 * to those grabbed from shared values. If
 */
public class CurrentUser {
        private SharedPreferences pref;
        private SharedPreferences.Editor editor;

        private String first_name;
        private String last_name;
        private String id;
        private String residence_name;
        private boolean logged_in_status;

    public CurrentUser(Context context ) {
         pref=context.getSharedPreferences("MyPref", 0);

        setFirst_name(pref.getString("first_name",null));
        setLast_name(pref.getString("last_name",null));
        setId(pref.getString("id",null));
        setResidence_name(pref.getString("residence_name",null));
        setLogged_in_status(pref.getBoolean("logged_in_status",false));

    }

    public void commit() {
        editor.putString("first_name",getFirst_name());
        editor.putString("last_name",getLast_name());
        editor.putString("user_id",getId());
        editor.putString("residence_name", getResidence_name());
        editor.putBoolean("logged_in_status", isLogged_in_status());
        editor.commit();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResidence_name() {
        return residence_name;
    }

    public void setResidence_name(String residence_name) {
        this.residence_name = residence_name;
    }

    public boolean isLogged_in_status() {
        return logged_in_status;
    }

    public void setLogged_in_status(boolean logged_in_status) {
        this.logged_in_status = logged_in_status;
    }



}
