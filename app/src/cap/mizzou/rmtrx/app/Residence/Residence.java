package cap.mizzou.rmtrx.app.Residence;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 10/9/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Residence {
    private int numberOfResidences;
    private String address;
    int[] users;
    String residenceId;

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
    }

    private String join_code;

    public Residence (int number,String address, int[] id_numbers) {
        setNumberOfResidences(number);
        setAddress(address);
        setUsers(users);
        setJoin_code();
    }
    public int[] getUsers() {
        return users;
    }

    public void setUsers(int[] users) {
        this.users = users;
    }

    public int getNumberOfResidences() {
        return numberOfResidences;
    }

    public void setNumberOfResidences(int numberOfResidences) {
        this.numberOfResidences = numberOfResidences;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getJoin_code() {
        return join_code;
    }

    public void setJoin_code() {
        this.join_code = generateCode();
    }

    public String generateCode() {
        return "0000";
    }




}
