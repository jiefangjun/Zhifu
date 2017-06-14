package fokia.gq.zhifu.model;

import java.lang.String;

/**
 * Created by archie on 6/12/17.
 */

public class Outlay {
    private double money;
    private String note;
    private String date;
    private String type;
    private String address;

    public Outlay(double money, String note, String date, String type, String address) {
        this.money = money;
        this.note = note;
        this.date = date;
        this.type = type;
        this.address = address;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
