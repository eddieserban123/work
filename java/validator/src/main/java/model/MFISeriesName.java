package model;

import java.util.List;

public class MFISeriesName {
    private int id;

    private String aqrId;

    private String email;

    private String creditCardNumber;


    private List<Values> values;

    //default no-args constructor
    public MFISeriesName(){}

    public MFISeriesName(int id, String name, String email, String ccNum){
        this.id=id;
        this.aqrId =name;
        this.email=email;
        this.creditCardNumber=ccNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAqrId() {
        return aqrId;
    }

    public void setAqrId(String aqrId) {
        this.aqrId = aqrId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }


    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

}
