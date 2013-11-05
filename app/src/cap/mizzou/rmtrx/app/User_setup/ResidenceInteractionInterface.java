package cap.mizzou.rmtrx.app.User_setup;

import Models.Residence;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ResidenceInteractionInterface {
    @FormUrlEncoded
    @POST("/residence")
    void createResidence(
            @Field("name") String nameOfResidence,
            @Field("user_id") String user_id,
            Callback<Residence> residence);
}