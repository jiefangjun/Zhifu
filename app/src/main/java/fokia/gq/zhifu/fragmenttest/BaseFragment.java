package fokia.gq.zhifu.fragmenttest;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import fokia.gq.zhifu.Dao.DBOpenHelper;

/**
 * Created by archie on 6/14/17.
 */

public class BaseFragment extends Fragment{
    public DBOpenHelper dbOpenHelper;
    public SQLiteDatabase db;
}
