package Data;

public class DataHelperAPI {
    public String cvc;
    public String holder;
    public String month;
    public String number;
    public String year;

    public DataHelperAPI(String cvc, String holder, String month, String number, String year) {
        this.cvc = cvc;
        this.holder = holder;
        this.month = month;
        this.number = number;
        this.year = year;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
