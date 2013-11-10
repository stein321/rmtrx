package cap.mizzou.rmtrx.app.Messages;

import Models.ChatLog;
import Models.Residence;
import retrofit.Callback;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/10/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MessagesDataInterface {

    @GET("/residence/{id}/chatlog")
    void getChatLog(@Path("id") String residenceId, Callback<ChatLog> callback);


}
