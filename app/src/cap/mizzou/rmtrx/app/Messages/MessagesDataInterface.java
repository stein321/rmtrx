package cap.mizzou.rmtrx.app.Messages;

import Models.ChatLog;
import Models.Message;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/10/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MessagesDataInterface {

    @GET("/residence/{id}/chatlog")
    void getChatLog(@Path("id") String residenceId, Callback<List<Message>> callback);

    @FormUrlEncoded
    @POST("/message")
    void sendMessage(@Field("user_id")String userId,
                     @Field("residence_id")String residenceId,
                     @Field("message")String message,
                     Callback<Message> callback);
}
