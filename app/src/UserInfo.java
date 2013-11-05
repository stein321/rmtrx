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
    private String residence_id;
    private String first_name;
    private String last_name;
    private String email;


    public UserInfo(Context context) {
            pref=context.getSharedPreferences("MyPref", 0);
            pref.getString("first_name",first_name);
            pref.getString("last_name",last_name);
            pref.getString("emai",email);
            pref.getString("id",id);
            pref.getString("residence_id",residence_id);
    }

    public void commit() {
        editor=pref.edit();
        editor.putString("first_name",getFirst_name());
        editor.putString("last_name",getLast_name());
        editor.putString("email",getEmail());
        editor.putString("id",getId());
        editor.putString("residence_id",getResidence_id());
        editor.commit();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResidence_id() {
        return residence_id;
    }

    public void setResidence_id(String residence_id) {
        this.residence_id = residence_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
