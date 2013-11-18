package cap.mizzou.rmtrx.app.Finances;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 3:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction implements Serializable {

    private long id;
    private String userid;
    private double amount;
    private String nature;
    private String date;
    private String from;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String toString() {
        return userid;
    }
}

