package fokia.gq.zhifu.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.model.Income;

/**
 * Created by archie on 6/13/17.
 */

public class InaccountDao extends BaseDBDao{
    private double money;
    private String date;
    private String type;
    private String handler;
    private String note;
    public static List<Income> incomes = new ArrayList<>();

    public InaccountDao(SQLiteDatabase db,  String id, double money, String date, String type, String handler, String note) {
        super(db, id);
        this.money = money;
        this.date = date;
        this.type = type;
        this.handler = handler;
        this.note = note;
    }

    @Override
    public void insert(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);
        contentValues.put("time", date);
        contentValues.put("type", type);
        contentValues.put("handler", handler);
        contentValues.put("mark",note);
        db.insert("tb_inaccount", null, contentValues);
    }

    @Override
    public void delete(SQLiteDatabase db, String id) {
        db.delete("tb_inaccount", "id=?", new String[]{id});
    }

    @Override
    public void update(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);
        contentValues.put("date", date);
        contentValues.put("type", type);
        contentValues.put("handler", handler);
        contentValues.put("mark",note);
        db.update("tb_account", contentValues, "id=?", new String[]{id});
    }

    @Override
    public Cursor query(SQLiteDatabase db, String id) {
        return  db.query("tb_inaccount", null, "where=?", new String[]{id}, null, null, null);
    }

    public static void getIncomes(SQLiteDatabase db){
        Cursor cursor = db.query("tb_inaccount", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            incomes.add(new Income(cursor.getDouble(1), cursor.getString(5), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
    }



}
