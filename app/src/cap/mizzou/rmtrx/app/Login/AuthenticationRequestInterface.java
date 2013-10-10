package cap.mizzou.rmtrx.app.Login;

import cap.mizzou.rmtrx.app.Login.LoginActivity;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/7/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthenticationRequestInterface {

    @FormUrlEncoded
    @POST("/authenticate")
    void login(@Field("username") String username, @Field("password") String password, Callback<LoginActivity.AuthResponse> printKey);
}
