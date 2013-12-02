package cap.mizzou.rmtrx.app.Finances;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import cap.mizzou.rmtrx.app.DataAccess.Resident;
import cap.mizzou.rmtrx.app.DataAccess.ResidentDataSource;
import cap.mizzou.rmtrx.app.R;

import java.text.DecimalFormat;
import java.util.*;


import cap.mizzou.rmtrx.app.User_setup.UserInfo;
import retrofit.RestAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 4:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class FinanceActivity extends ListActivity {

    private FinanceDb dataSource;
    private String description;
    private Double amount;
    private UserInfo userinfo;
    private double charge;

    private Spinner spinner;
    private int index;
    private TextView t;
    private ResidentDataSource data;
    private financesInterface restInterface;
    List<Resident> listOfAllResidents;
    List<String> residentWithTabList;
    private RestAdapter restAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finances);

        setUserinfo(new UserInfo(this));

        setDataSource(new FinanceDb(this));
        getDataSource().open();
        setData(new ResidentDataSource(this));
        getData().open();
        setRestAdapter(new RestAdapter.Builder().setServer("http://powerful-thicket-5732.herokuapp.com/").build());
        setRestInterface(getRestAdapter().create(financesInterface.class));

        //initialize Spinner
        setSpinner((Spinner) findViewById(R.id.other_roommates));

        setResidentWithTabList(new ArrayList<String>());
        setListOfAllResidents(new ArrayList<Resident>());
        updateSpinner();

    }

    public void updateSpinner() {
            getResidentWithTabList().clear();
//            listOfAllResidents.clear();
            setListOfAllResidents(getData().getAllResidents());

        for(int i=0;i< getListOfAllResidents().size();i++) {
            if(getListOfAllResidents().get(i).getUserID().equals(getUserinfo().getId())) {
                setIndex(i);
            }
        }
        getListOfAllResidents().remove(getIndex());

        for(Resident resident: getListOfAllResidents()) {
            getResidentWithTabList().add(resident.getFirstName() + " $ " + getFormattedAmount(getDataSource().amountOwed(getUserinfo().getId(), resident.getUserID())));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, residentWithTabList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinner().setAdapter(dataAdapter);
    }

    public void addTransaction(View v){

        setSpinner((Spinner)findViewById(R.id.other_roommates));
        if(!setFormInputAndClear()) {
            return;
        }
        //getUserIdFromSpinner
        Resident to= getListOfAllResidents().get(getSpinner().getSelectedItemPosition());
        String toId= to.getUserID();
        getDataSource().createTransaction(getUserinfo().getId(), toId, getDescription(), getAmount());
        updateSpinner();
        createToast(getAmount(),to.getFirstName());
    }

    private void createToast(Double amount, String roommateUserFirstName) {
        CharSequence text = "Transaction of $" + getFormattedAmount(amount) + " was added to " + roommateUserFirstName + "'s Account.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
        toast.show();
    }

    public void addGroupTransaction(View view) {
        if(!setFormInputAndClear()) {
            return;
        }
        int numberOfResidents= getListOfAllResidents().size()+1;
        setCharge(getAmount()/numberOfResidents );


        new AlertDialog.Builder(this)
                .setTitle("Group Transaction")
                .setMessage("Are you sure you want to charge everyone" + " $" + getFormattedAmount(getCharge()) + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for(Resident resident: getListOfAllResidents()) {
                            getDataSource().createTransaction(getUserinfo().getId(), resident.getUserID(), getDescription(), getCharge());
                        }
                        updateSpinner();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private boolean setFormInputAndClear() {
        //grab text from form
        EditText amountText = (EditText) findViewById(R.id.transaction_amount);
        if(amountText.getText().toString().equals("")) {
            return false;
        }
        EditText descriptionText=(EditText)findViewById(R.id.transaction_nature);
        //Converts text to double
        setAmount(Double.parseDouble(amountText.getText().toString()));
        setDescription(descriptionText.getText().toString());
        //clear form
        descriptionText.setText("");
        amountText.setText("");
        return true;
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
    public Double getAmount() {
        return amount;
    }
    public String getFormattedAmount() {
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(getAmount());
    }
    public String getFormattedAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(amount);
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Resident> getListOfAllResidents() {
        return listOfAllResidents;
    }

    public void setListOfAllResidents(List<Resident> listOfAllResidents) {
        this.listOfAllResidents = listOfAllResidents;
    }

    public List<String> getResidentWithTabList() {
        return residentWithTabList;
    }

    public void setResidentWithTabList(List<String> residentWithTabList) {
        this.residentWithTabList = residentWithTabList;
    }
    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public FinanceDb getDataSource() {
        return dataSource;
    }

    public void setDataSource(FinanceDb dataSource) {
        this.dataSource = dataSource;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ResidentDataSource getData() {
        return data;
    }

    public void setData(ResidentDataSource data) {
        this.data = data;
    }
    public void setRestInterface(financesInterface restInterface) {
        this.restInterface = restInterface;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public void setRestAdapter(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }


}



