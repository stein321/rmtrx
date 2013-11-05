package cap.mizzou.rmtrx.app.User_setup;

import cap.mizzou.rmtrx.app.Residence.Code;
import cap.mizzou.rmtrx.app.Residence.Residence;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ResidenceCreationInterface {

    @FormUrlEncoded
    @POST("/residence")
    void createResidence(
            @Field("name") String name_of_residence,
            @Field("user_id") String main_user_id,
            Callback<Residence> residence);

    @FormUrlEncoded
    @POST("/code")
    void saveResidenceCode(
            @Field("residence_id")  String residence_id,
            @Field("code")          String code,
            Callback<Code> codeReturned);

    @FormUrlEncoded
    @POST("/join")
    void joinResidenceWithCode(
            @Field("code") String code,
            @Field("user_id") String user_id,
            Callback<Residence> residence);

}
