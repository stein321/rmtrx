package cap.mizzou.rmtrx.app.Login;

import Models.ResponseObject;
import cap.mizzou.rmtrx.app.ui.HomeActivity;
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
    void login(@Field("email") String username, @Field("password") String password, Callback<ResponseObject> callback);
}
