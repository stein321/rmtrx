package cap.mizzou.rmtrx.app.User_setup;

import Models.UserAndResidenceResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 10/11/13
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserCreationInterface {

    @FormUrlEncoded
    @POST("/account")
    void createUserAndResidence(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("residence_name") String residenceName,
            Callback<UserAndResidenceResponse> userAndResidence
    );

    @FormUrlEncoded
    @POST("/join")
    void createUserAndJoinResidence(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("code") String residenceCode,
            Callback<UserAndResidenceResponse> userAndResidence
    );
}
