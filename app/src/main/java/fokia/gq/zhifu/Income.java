package fokia.gq.zhifu;

import java.util.Date;

/**
 * Created by archie on 6/12/17.
 */

public class Income {
    private double money;
    private String note;
    private Date date;
    private String type;
    private String address;

    public Income(double money, String note, Date date, String type, String address){
        this.money = money;
        this.note = note;
        this.date = date;
        this.type = type;
        this.address = address;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
