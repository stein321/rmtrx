package Models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mam8cc
 * Date: 11/22/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ledger {
    List<TransactionCallback> transactions;

    public List<TransactionCallback> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionCallback> transactions) {
        this.transactions = transactions;
    }
}
