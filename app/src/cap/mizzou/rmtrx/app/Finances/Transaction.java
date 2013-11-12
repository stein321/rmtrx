package cap.mizzou.rmtrx.app.Finances;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 3:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {

    private long id;
    private String userid;
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String id) {
        this.userid = userid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}

