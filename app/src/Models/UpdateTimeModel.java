package Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/18/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateTimeModel {
    @SerializedName("time_stamp")
    long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
