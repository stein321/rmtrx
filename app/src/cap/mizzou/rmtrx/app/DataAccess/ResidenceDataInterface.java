package cap.mizzou.rmtrx.app.DataAccess;

import Models.Residence;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/1/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResidenceDataInterface {

    @GET("/residence/user/{id}")
    void getResidence(@Path("id") String userId, Callback<Residence> callback);

}
