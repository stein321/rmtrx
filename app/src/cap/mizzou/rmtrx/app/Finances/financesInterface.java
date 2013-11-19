package cap.mizzou.rmtrx.app.Finances;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/18/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface financesInterface {
    @FormUrlEncoded
    @POST("")
    void sendTransaction(
        @Field("from") String from,
        @Field("to") String to,
        @Field("description") String description,
        @Field("amount") String amount
    );
}
