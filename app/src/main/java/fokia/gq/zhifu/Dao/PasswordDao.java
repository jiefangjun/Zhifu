package fokia.gq.zhifu.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by archie on 6/13/17.
 */

public class PasswordDao {
    private SQLiteDatabase db;
    private String password;

    public PasswordDao(SQLiteDatabase db, String password){
        this.db = db;
        this.password = password;
    }

    public static void insert(SQLiteDatabase db, String password){
        ContentValues values = new ContentValues();
        values.put("password", password);
        db.insert("tp_pwd", null, values);
    }

    public static void update(SQLiteDatabase db, String password){
        ContentValues values = new ContentValues();
        values.put("password", password);
        db.update("tb_pwd", values, null, null);
        //db.update("tb_pwd", values, "password=?", new String[]{"123"});
    }

    public String query(SQLiteDatabase db){
        Cursor cursor = db.query("tb_pwd", null, null, null, null, null, null);
        cursor.moveToLast();
        return cursor.getString(0);
    }
}
