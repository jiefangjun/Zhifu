package fokia.gq.zhifu.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by archie on 6/13/17.
 */

public abstract class BaseDBDao {
    public SQLiteDatabase db;
    public String id;
    public BaseDBDao(SQLiteDatabase db, String id){
        this.db = db;
        this.id = id;
    }
    public abstract void insert(SQLiteDatabase db, String id);
    public abstract void delete(SQLiteDatabase db, String id);
    public abstract void update(SQLiteDatabase db, String id);
    public abstract Cursor query(SQLiteDatabase db, String id);

    //TODO 优化CRUD函数，不需要传入数据库参数
}
