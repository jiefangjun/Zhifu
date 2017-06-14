package fokia.gq.zhifu.model;

import java.lang.String;

/**
 * Created by archie on 6/12/17.
 */

public class Income {
    private double money;
    private String note;
    private String date;
    private String type;
    private String handler;

    public Income(double money, String note, String date, String type, String handler){
        this.money = money;
        this.note = note;
        this.date = date;
        this.type = type;
        this.handler = handler;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String gethandler() {
        return handler;
    }

    public void sethandler(String handler) {
        this.handler = handler;
    }
}
