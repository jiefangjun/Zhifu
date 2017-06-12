package fokia.gq.zhifu;

import java.util.Date;

/**
 * Created by archie on 6/12/17.
 */

public class Income {
    private double money;
    private String note;
    private Date date;

    public Income(double money, String note, Date date){
        this.money = money;
        this.note = note;
        this.date = date;
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
}
