package cap.mizzou.rmtrx.app.Finances;

import Models.TransactionCallback;
import retrofit.Callback;
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
    @POST("/transaction")
    void sendTransaction(
        @Field("residence_id") String residenceId,
        @Field("from_user") String from,
        @Field("to_user") String to,
        @Field("note") String description,
        @Field("amount") String amount,
        Callback<TransactionCallback> transaction);

}
