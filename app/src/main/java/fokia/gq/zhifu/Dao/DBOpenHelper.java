package fokia.gq.zhifu.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by archie on 6/13/17.
 */

public class DBOpenHelper extends SQLiteOpenHelper{

    public static final String CREATE_TB_PWD = "create table tb_pwd("
            + "password varchar(20) DEFAULT 123)";

    public static final String CREATE_TB_OUTACCOUNT = "create table tb_outaccount("
            + "id integer primary key autoincrement,"
            + "money decimal,"
            + "time varchar(10),"
            + "type varchar(10),"
            + "address varchar(100),"
            + "mark varchar(200))";

    public static final String CREATE_TB_INACCOUNT = "create table tb_inaccount("
            + "id integer primary key autoincrement,"
            + "money decimal,"
            + "time varchar(10),"
            + "type varchar(10),"
            + "handler varchar(100),"
            + "mark varchar(200))";

    public static final String CREATE_NOTE = "create table tb_note("
            + "id integer primary key autoincrement,"
            + "flag varchar(200))";

    public static final String DEFAULT_PASSWORD = "insert into tb_pwd(password) values(123) ";

    private Context mContext;
    public  DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_PWD);
        db.execSQL(CREATE_TB_INACCOUNT);
        db.execSQL(CREATE_TB_OUTACCOUNT);
        db.execSQL(CREATE_NOTE);
        db.execSQL(DEFAULT_PASSWORD);
        Toast.makeText(mContext, "初始化数据成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
