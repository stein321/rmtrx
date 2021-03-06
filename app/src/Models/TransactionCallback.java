package Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/19/13
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionCallback {

    @SerializedName("_id")
    String _Id;
    String fromUser;
    String toUser;
    String description;
    int amount;
    int dateSubmitted;

    public int getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(int dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public int getAmount() {

        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
