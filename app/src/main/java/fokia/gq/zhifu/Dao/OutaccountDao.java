package fokia.gq.zhifu.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.model.Income;
import fokia.gq.zhifu.model.Outlay;

/**
 * Created by archie on 6/13/17.
 */

public class OutaccountDao extends BaseDBDao{

    private double money;
    private String note;
    private String date;
    private String type;
    private String address;

    public static List<Outlay> outlays = new ArrayList<>();

    public OutaccountDao(SQLiteDatabase db, String id, double money, String note, String date, String type, String address) {
        super(db, id);
        this.money = money;
        this.note = note;
        this.date = date;
        this.type = type;
        this.address = address;
    }

    @Override
    public void insert(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);
        contentValues.put("time", date);
        contentValues.put("type", type);
        contentValues.put("address", address);
        contentValues.put("mark",note);
        db.insert("tb_outaccount", null, contentValues);
        outlays.add(outlays.size(), new Outlay(money, note, date, type, address));
    }

    @Override
    public void delete(SQLiteDatabase db, String id) {
        db.delete("tb_outaccount", "id=?", new String[]{id});
    }

    public static void staticDelete(SQLiteDatabase db,String id){
        db.delete("tb_outaccount", "time=?", new String[]{id});
    }

    @Override
    public void update(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);
        contentValues.put("time", date);
        contentValues.put("type", type);
        contentValues.put("address", address);
        contentValues.put("mark",note);
        db.update("tb_outaccount", contentValues, "time=?", new String[]{id});
    }

    @Override
    public Cursor query(SQLiteDatabase db, String id) {
        return  db.query("tb_outaccount", null, "where=?", new String[]{id}, null, null, null);
    }

    public static void getOutlays(SQLiteDatabase db){
        Cursor cursor = db.query("tb_outaccount", null, null, null, null, null, "id DESC");
        while (cursor.moveToNext()){
            outlays.add(new Outlay(cursor.getDouble(1), cursor.getString(5), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
    }
}
