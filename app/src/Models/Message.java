package Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/10/13
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    @SerializedName("_id")
    private String id;
    private String message;
    private String senderId;
    private String dateSent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
    public String toString() {
        return message;
    }
}
